package org.egov.collection.integration.pgi;

import static org.egov.collection.constants.CollectionConstants.SINGLE_DELIMITER;
import static org.egov.collection.constants.CollectionConstants.SIX_DELIMITER;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.Security;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.egov.collection.config.properties.CollectionApplicationProperties;
import org.egov.collection.constants.CollectionConstants;
import org.egov.collection.entity.OnlinePayment;
import org.egov.collection.entity.ReceiptHeader;
import org.egov.collection.service.ReceiptHeaderService;
import org.egov.collection.utils.CollectionsUtil;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.exception.ApplicationException;
import org.egov.infra.exception.ApplicationRuntimeException;
import org.egov.infra.utils.StringUtils;
import org.egov.infstr.models.ServiceDetails;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The PaymentRequestAdaptor class frames the payment request object and parses the payment response for the payment gateway
 * service HDFC
 */

public class HdfcAdaptor implements PaymentGatewayAdaptor {

    private static final Logger LOGGER = Logger.getLogger(HdfcAdaptor.class);

    private static final String FAILURE = "failure";

    private static final String STATUS = "status";
    private static final String ADDITIONAL_CHARGES = "aditionalCharges";
    private static final String UDF5 = "udf5";
    private static final String UDF4 = "udf4";
    private static final String UDF3 = "udf3";
    private static final String UDF2 = "udf2";
    private static final String UDF1 = "udf1";
    private static final String EMAIL = "email";
    private static final String FIRST_NAME = "firstname";
    private static final String PRODUCT_INFO = "productinfo";
    private static final String AMOUNT = "amount";
    private static final String TNX_ID = "txnid";
    private static final String HASH = "hash";
    private static final String KEY = "key";
    private static final String ERROR = "error";
    private static final String MIH_PAYID = "mihpayid";
    private static final String SALT = "salt";
    private static final String ADDED_ON = "addedon";
    private static final String SUCCESS = "success";
    private static final String DROP_CATEGORY = "drop_category";
    private static final String CITY_CODE = "city_code";

    @Autowired
    private CollectionApplicationProperties collectionApplicationProperties;

    @Autowired
    private ReceiptHeaderService receiptHeaderService;

    @Autowired
    private CollectionsUtil collectionsUtil;

    /**
     * This method invokes APIs to frame request object for the payment service passed as parameter
     *
     * @param serviceDetails
     * @param receiptHeader
     * @return
     */
    public PaymentRequest createPaymentRequest(ServiceDetails paymentServiceDetails, ReceiptHeader receiptHeader) {
        DefaultPaymentRequest paymentRequest = new DefaultPaymentRequest();
        paymentRequest.setParameter(CollectionConstants.HDFC_KEY, collectionApplicationProperties.hdfcKey());
        String cityCode = ApplicationThreadLocals.getCityCode();

        paymentRequest.setParameter(CollectionConstants.HDFC_TXNID, cityCode
                + CollectionConstants.SEPARATOR_HYPHEN + receiptHeader.getId().toString()
                + CollectionConstants.SEPARATOR_HYPHEN + receiptHeader.getService().getCode());

        paymentRequest
                .setParameter(CollectionConstants.HDFC_AMOUNT, receiptHeader.getTotalAmount().setScale(0, BigDecimal.ROUND_UP));

        paymentRequest.setParameter(CollectionConstants.HDFC_PRODUCT_INFO,
                (receiptHeader.getConsumerCode().replace("-", "").replace("/", "")));

        paymentRequest.setParameter(CollectionConstants.HDFC_FIRSTNAME, buildPayeeName(receiptHeader.getPayeeName()));
        paymentRequest.setParameter(CollectionConstants.HDFC_EMAIL,
                !StringUtils.isBlank(receiptHeader.getPayeeEmail()) ? receiptHeader.getPayeeEmail()
                        : collectionApplicationProperties.hdfcTransactionEmail());
        paymentRequest.setParameter(CollectionConstants.HDFC_PHONE, collectionApplicationProperties.hdfcTransactionPhone());
        StringBuilder returnUrl = new StringBuilder();
        returnUrl.append(paymentServiceDetails.getCallBackurl()).append("?paymentServiceId=")
                .append(paymentServiceDetails.getId());
        paymentRequest.setParameter(CollectionConstants.CITRUS_RETURNURL, returnUrl);
        paymentRequest.setParameter(CollectionConstants.HDFC_SURL, returnUrl.toString());// If transaction is successful.
        paymentRequest.setParameter(CollectionConstants.HDFC_FURL, returnUrl.toString());// If transaction is fail.
        paymentRequest.setParameter(CollectionConstants.HDFC_CURL, returnUrl.toString());// If transaction is cancelled by
                                                                                         // customer on PAYU page.

        // Product Info
        paymentRequest.setParameter(UDF1, (receiptHeader.getConsumerCode().replace("-", "").replace("/", "")));
        // Email ID
        paymentRequest.setParameter(UDF2, !StringUtils.isBlank(receiptHeader.getPayeeEmail()) ? receiptHeader.getPayeeEmail()
                : collectionApplicationProperties.hdfcTransactionEmail());
        // Phone
        paymentRequest.setParameter(UDF3, collectionApplicationProperties.hdfcTransactionPhone());
        // Address
        paymentRequest.setParameter(UDF4, buildAddress(ApplicationThreadLocals.getCityName()));
        // Txn ID
        paymentRequest.setParameter(UDF5, receiptHeader.getId().toString());

        // calculate hash
        Map<String, String> requestmap = new HashMap<String, String>(paymentRequest.getRequestParameters());

        /*
         * sha512(key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|u df5||||||SALT)
         */
        StringBuffer hashData = new StringBuffer(collectionApplicationProperties.hdfcKey());

        hashData.append(SINGLE_DELIMITER).append(requestmap.get(CollectionConstants.HDFC_TXNID));

        hashData.append(SINGLE_DELIMITER).append(String.valueOf(requestmap.get(CollectionConstants.HDFC_AMOUNT)));

        hashData.append(SINGLE_DELIMITER).append(requestmap.get(CollectionConstants.HDFC_PRODUCT_INFO));

        hashData.append(SINGLE_DELIMITER).append(requestmap.get(CollectionConstants.HDFC_FIRSTNAME));

        hashData.append(SINGLE_DELIMITER).append(requestmap.get(CollectionConstants.HDFC_EMAIL));

        hashData.append(SINGLE_DELIMITER).append(requestmap.get(UDF1));

        hashData.append(SINGLE_DELIMITER).append(requestmap.get(UDF2));

        hashData.append(SINGLE_DELIMITER).append(requestmap.get(UDF3));

        hashData.append(SINGLE_DELIMITER).append(requestmap.get(UDF4));

        hashData.append(SINGLE_DELIMITER).append(requestmap.get(UDF5));

        hashData.append(SIX_DELIMITER).append(collectionApplicationProperties.hdfcSalt());

        LOGGER.info(CITY_CODE + " = " + cityCode + " | " + KEY + " = " + collectionApplicationProperties.hdfcKey() + " | "
                + CollectionConstants.HDFC_TXNID + " = " + requestmap.get(CollectionConstants.HDFC_TXNID)
                + " | " + CollectionConstants.HDFC_PRODUCT_INFO + " = " + requestmap.get(CollectionConstants.HDFC_PRODUCT_INFO)
                + " | " + CollectionConstants.HDFC_FIRSTNAME + " = " + requestmap.get(CollectionConstants.HDFC_FIRSTNAME)
                + " | " + CollectionConstants.HDFC_EMAIL + " = " + requestmap.get(CollectionConstants.HDFC_EMAIL)
                + " | " + UDF1 + " = " + requestmap.get(UDF1) + " | " + UDF2 + " = " + requestmap.get(UDF2) + " | "
                + UDF3 + " = " + requestmap.get(UDF3) + " | " + UDF4 + " = " + requestmap.get(UDF4) + " | "
                + UDF5 + " = " + requestmap.get(UDF5) + " | " + SALT + " = " + collectionApplicationProperties.hdfcSalt());

        String hashValue = "";
        LOGGER.info("Hashing data before encryption = " + hashData.toString());

        if (hashData.length() > 0) {
            hashValue = generateSecureHash(hashData.toString());
        }
        paymentRequest.setParameter(CollectionConstants.HDFC_HASH, hashValue);
        paymentRequest.setParameter(CollectionConstants.HDFC_PG,
                collectionApplicationProperties.hdfcPg());
        paymentRequest.setParameter(DROP_CATEGORY, "NB");
        paymentRequest.setParameter(CollectionConstants.ONLINEPAYMENT_INVOKE_URL, paymentServiceDetails.getServiceUrl());
        LOGGER.info("HDFC payment request : " + paymentRequest.getRequestParameters());
        return paymentRequest;
    }

    private String buildPayeeName(String payeeName) {
        if (StringUtils.isNotBlank(payeeName)) {
            String[] split = payeeName.split(",");
            if (split.length > 0) {
                String splitZero = split[0];
                if (splitZero.length() > 20) {
                    return splitZero.substring(0, 19).trim().replaceAll("[^A-Za-z0-9]", " ");
                } else
                    return splitZero.trim().replaceAll("[^A-Za-z0-9]", " ");
            }
        }
        return StringUtils.EMPTY;
    }

    private String buildAddress(String address) {
        if (StringUtils.isNotBlank(address)) {
            return address.replace(",", " ").replaceAll("(\r\n|\n)", " ").replaceAll("[^A-Za-z0-9]", " ").trim();
        }
        return StringUtils.EMPTY;
    }

    /**
     * This method generates secure hash using SHA-512 hashing algorithm
     *
     * @param input
     * @return Encrypted message
     */
    private String generateSecureHash(String input) {
        String result = input;
        byte[] mdbytes = null;
        if (input != null) {
            Security.addProvider(new BouncyCastleProvider());
            Digest messageDigest = new SHA512Digest();
            byte[] retValue = new byte[messageDigest.getDigestSize()];
            byte[] byteData = result.toString().getBytes();
            messageDigest.update(byteData, 0, result.toString().length());
            messageDigest.doFinal(retValue, 0);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < retValue.length; i++) {
                String hex = Integer.toHexString(0xFF & retValue[i]);
                if (hex.length() == 1) {
                    sb.append("0");
                }
                sb.append(hex);
            }
            result = sb.toString();
        }
        return result;
    }

    @Override
    public PaymentResponse parsePaymentResponse(String response) {
        PaymentResponse hdfcResponse = new DefaultPaymentResponse();

        try {
            // TODO Auto-generated method stub
            LOGGER.info("Response message from HdfcAdaptor Payment gateway: " + response);
            String[] keyValueStr = response.replace("{", "").replace("}", "").split(",");
            Map<String, String> responseMap = new HashMap<String, String>(0);
            for (String pair : keyValueStr) {
                String[] entry = pair.split("=");
                responseMap.put(entry[0].trim(), entry[1].trim());
            }

            // HDFC Payment Gateway returns f_code Ok for successful
            // transactions, so converted it to 0300
            // as that is being followed as a standard in other payment gateways.

            Date transactionDate = formatDate(responseMap.get(ADDED_ON));

            String txnId = responseMap.get(TNX_ID);
            String convertedTnxId = collectionsUtil.getTransactionId(txnId, CollectionConstants.SEPARATOR_HYPHEN);

            String udf1 = responseMap.get(UDF1); // consumer code

            ReceiptHeader onlinePaymentReceiptHeader = receiptHeaderService.findByNamedQuery(
                    CollectionConstants.QUERY_PENDING_RECEIPT_BY_ID_AND_CONSUMERCODE, Long.valueOf(convertedTnxId),
                    udf1);

            if (verifyResponse(responseMap, transactionDate, onlinePaymentReceiptHeader)) {
                hdfcResponse.setAuthStatus(
                        responseMap.get(STATUS).equalsIgnoreCase(SUCCESS) ? CollectionConstants.PGI_AUTHORISATION_CODE_SUCCESS
                                : responseMap.get(STATUS));
                if (hdfcResponse.getAuthStatus().equalsIgnoreCase(CollectionConstants.PGI_AUTHORISATION_CODE_SUCCESS)) {
                    BigDecimal amount = new BigDecimal(responseMap.get(AMOUNT)).setScale(2, BigDecimal.ROUND_UP);
                    BigDecimal receiptHeaderAmount = onlinePaymentReceiptHeader.getTotalAmount().setScale(2, BigDecimal.ROUND_UP);
                    if (receiptHeaderAmount.compareTo(amount) == 0) {
                        hdfcResponse.setTxnAmount(amount);
                        hdfcResponse.setTxnReferenceNo(responseMap.get(MIH_PAYID));
                        hdfcResponse.setTxnDate(transactionDate);
                    } else
                        hdfcResponse.setAuthStatus(FAILURE);
                }
            } else {
                hdfcResponse.setAuthStatus(FAILURE);
            }

            hdfcResponse.setErrorDescription(responseMap.get(ERROR));
            hdfcResponse.setAdditionalInfo6(responseMap.get(PRODUCT_INFO));
            hdfcResponse.setReceiptId(convertedTnxId);

        } catch (ApplicationException e) {
            LOGGER.error(e);
            throw new ApplicationRuntimeException("Exception during prepare payment response", e);
        }

        return hdfcResponse;

    }

    private boolean verifyResponse(Map<String, String> responseMap, Date transactionDate,
            ReceiptHeader onlinePaymentReceiptHeader) throws ApplicationException {

        try {
            // We use only hash, tnxid, status and additional charges from response other parameters we fetch from database
            String txnId = responseMap.get(TNX_ID);
            String status = responseMap.get(STATUS);
            String responseHash = responseMap.get(HASH);
            String udf1 = responseMap.get(UDF1); // consumer code
            String additionalCharges = responseMap.get(ADDITIONAL_CHARGES);

            if (StringUtils.isNotBlank(txnId) && StringUtils.isNotBlank(status)
                    && StringUtils.isNotBlank(udf1)) {

                /*
                 * Transaction is verified using txnid , udf1 from the response and fetching the receipt header object and using
                 * receipt header object data to build reverse hash data and verify the hash in response and reverse hash are
                 * same.
                 */

                if (onlinePaymentReceiptHeader != null) {

                    String udf5 = onlinePaymentReceiptHeader.getId().toString();
                    String udf4 = buildAddress(ApplicationThreadLocals.getCityName());
                    String udf3 = collectionApplicationProperties.hdfcTransactionPhone();
                    String udf2 = !StringUtils.isBlank(onlinePaymentReceiptHeader.getPayeeEmail())
                            ? onlinePaymentReceiptHeader.getPayeeEmail()
                            : collectionApplicationProperties.hdfcTransactionEmail();
                    // String email = !StringUtils.isBlank(onlinePaymentReceiptHeader.getPayeeEmail()) ?
                    // onlinePaymentReceiptHeader.getPayeeEmail() : collectionApplicationProperties.hdfcTransactionEmail();
                    String firstName = buildPayeeName(onlinePaymentReceiptHeader.getPayeeName());
                    String productInfo = onlinePaymentReceiptHeader.getConsumerCode().replace("-", "").replace("/", "");
                    String amount = onlinePaymentReceiptHeader.getTotalAmount().setScale(2, BigDecimal.ROUND_UP).toString();
                    String hashValue = "";

                    LOGGER.info("Response Parameters : " + STATUS + " = " + status + " | " + ADDITIONAL_CHARGES + " = "
                            + additionalCharges
                            + " | " + UDF5 + " = " + udf5 + " | " + UDF4 + " = " + udf4 + " | udf4 from response = "
                            + responseMap.get("udf4") + " | " + UDF3 + " = " + udf3 + " | " + UDF2 + " = " + udf2
                            + " | " + UDF1 + " = " + udf1 + " | " + EMAIL + " = " + udf2 + " | " + FIRST_NAME + " = " + firstName
                            + " | " + PRODUCT_INFO + " = " + productInfo
                            + " | " + AMOUNT + " = " + amount + " | " + TNX_ID + " = " + txnId + " | " + HASH + " = "
                            + responseHash);

                    if (StringUtils.isBlank(additionalCharges)) {

                        /* sha512(SALT|status||||||udf5|udf4|udf3|udf2|udf1|email|firstname|productinfo|amount|txnid|key) */
                        StringBuffer hashData = new StringBuffer(collectionApplicationProperties.hdfcSalt());
                        hashData.append(SINGLE_DELIMITER).append(status);
                        hashData.append(SIX_DELIMITER);
                        hashData.append(udf5);
                        hashData.append(SINGLE_DELIMITER).append(udf4);
                        hashData.append(SINGLE_DELIMITER).append(udf3);
                        hashData.append(SINGLE_DELIMITER).append(udf2);
                        hashData.append(SINGLE_DELIMITER).append(udf1);
                        hashData.append(SINGLE_DELIMITER).append(udf2);
                        hashData.append(SINGLE_DELIMITER).append(firstName);
                        hashData.append(SINGLE_DELIMITER).append(productInfo);
                        hashData.append(SINGLE_DELIMITER).append(amount);
                        hashData.append(SINGLE_DELIMITER).append(txnId);
                        hashData.append(SINGLE_DELIMITER).append(collectionApplicationProperties.hdfcKey());

                        LOGGER.info("Hashing data from response = " + hashData.toString());

                        if (hashData.length() > 0) {
                            hashValue = generateSecureHash(hashData.toString());
                            LOGGER.info("Reverse hash generated = " + hashValue + " for tnxid = " + txnId);
                        }

                    } else {
                        /*
                         * sha512(additionalCharges|SALT|status||||||udf5|udf4|udf3|udf2|udf1|email|firstname|productinfo|amount|
                         * txnid|key)
                         */
                        StringBuffer hashData = new StringBuffer(additionalCharges);
                        hashData.append(SINGLE_DELIMITER).append(collectionApplicationProperties.hdfcSalt());
                        hashData.append(SINGLE_DELIMITER).append(status);
                        hashData.append(SIX_DELIMITER);
                        hashData.append(SINGLE_DELIMITER).append(udf5);
                        hashData.append(SINGLE_DELIMITER).append(udf4);
                        hashData.append(SINGLE_DELIMITER).append(udf3);
                        hashData.append(SINGLE_DELIMITER).append(udf2);
                        hashData.append(SINGLE_DELIMITER).append(udf1);
                        hashData.append(SINGLE_DELIMITER).append(udf2);
                        hashData.append(SINGLE_DELIMITER).append(firstName);
                        hashData.append(SINGLE_DELIMITER).append(productInfo);
                        hashData.append(SINGLE_DELIMITER).append(amount);
                        hashData.append(SINGLE_DELIMITER).append(txnId);
                        hashData.append(SINGLE_DELIMITER).append(collectionApplicationProperties.hdfcKey());

                        LOGGER.info("Hashing data from response" + hashData.toString());

                        if (hashData.length() > 0) {
                            hashValue = generateSecureHash(hashData.toString());
                            LOGGER.info("Reverse hash generated = " + hashValue + " for tnxid = " + txnId);
                        }

                    }

                    // Verifying the hash from response == reverse hash and transaction date >= receipt date.
                    boolean value = transactionDate
                            .compareTo(formatDate(onlinePaymentReceiptHeader.getReceiptDate().toString())) >= 0
                            && StringUtils.isNotBlank(responseHash) && responseHash.equalsIgnoreCase(hashValue);

                    return value;
                }

            }

        } catch (ApplicationException e) {
            throw new ApplicationException("Exception during verify payment response", e);
        }

        return false;
    }

    public PaymentResponse createOfflinePaymentRequest(OnlinePayment onlinePayment) {
        LOGGER.debug("Inside createOfflinePaymentRequest");

        PaymentResponse hdfcResponse = new DefaultPaymentResponse();
        try {
            HttpPost httpPost = new HttpPost(collectionApplicationProperties.hdfcReconsileUrl());
            httpPost.setEntity(prepareEntityData(onlinePayment));
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse execute = httpClient.execute(httpPost);
            HttpEntity entity = execute.getEntity();
            InputStream content = entity.getContent();

            Map<String, String> responseMap = prepareResponseMap(content);
            hdfcResponse
                    .setAuthStatus(responseMap.get(STATUS).equals(SUCCESS) ? CollectionConstants.PGI_AUTHORISATION_CODE_SUCCESS
                            : responseMap.get(STATUS));
            if (CollectionConstants.PGI_AUTHORISATION_CODE_SUCCESS.equals(hdfcResponse.getAuthStatus())) {
                hdfcResponse
                        .setAdditionalInfo6(onlinePayment.getReceiptHeader().getConsumerCode().replace("-", "").replace("/", ""));
                hdfcResponse.setErrorDescription(responseMap.get("error_Message"));
                String txnId = responseMap.get(TNX_ID);
                String convertedTnxId = collectionsUtil.getTransactionId(txnId, CollectionConstants.SEPARATOR_HYPHEN);
                hdfcResponse.setReceiptId(convertedTnxId);
                hdfcResponse.setTxnAmount(new BigDecimal(responseMap.get("amt")));
                hdfcResponse.setTxnReferenceNo(responseMap.get(MIH_PAYID));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date transactionDate = null;
                try {
                    transactionDate = sdf.parse(responseMap.get(ADDED_ON));
                    hdfcResponse.setTxnDate(transactionDate);
                } catch (ParseException e) {
                    LOGGER.error("Error occured in parsing the transaction date [" + responseMap.get("txnDateTime") + "]", e);
                    // throw new EGOVRuntimeException(".transactiondate.parse.error", e);
                }
            } else {
                hdfcResponse
                        .setAdditionalInfo6(onlinePayment.getReceiptHeader().getConsumerCode().replace("-", "").replace("/", ""));
                hdfcResponse.setReceiptId(onlinePayment.getReceiptHeader().getId().toString());
                hdfcResponse.setErrorDescription("Transaction not found");
            }
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        LOGGER.debug("end createOfflinePaymentRequest");
        return hdfcResponse;
    }

    private Map<String, String> prepareResponseMap(InputStream content) {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(content));
        final StringBuilder data = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null)
                data.append(line);
            reader.close();
        } catch (final IOException e) {
            LOGGER.error("Error Reading InsputStrem from HDFC Bank Response" + e);
        }

        String response = data.toString();
        LOGGER.info("HDFC  Reconcilation response: " + response);

        String[] keyValueStr = response.replace("{", "").replace("}", "").replace("\"", "").split(",");
        Map<String, String> responseMap = new HashMap<String, String>(0);
        for (String pair : keyValueStr) {
            if (pair.contains(MIH_PAYID)) {
                String[] stringArray = pair.split(":");
                responseMap.put(stringArray[2].trim(), stringArray[3].trim());
            } else if (pair.contains(ADDED_ON)) {
                String[] stringArray = pair.split(":");
                responseMap.put(stringArray[0].trim(), (stringArray[1].split(" "))[0].trim());
            } else {
                String[] entry = pair.split(":");
                if (entry.length == 2) {
                    responseMap.put(entry[0].trim(), entry[1].trim());
                }
            }
            System.out.println("responseMap = " + responseMap);
        }
        return responseMap;
    }

    private UrlEncodedFormEntity prepareEntityData(OnlinePayment onlinePayment) {
        List<NameValuePair> formData = new ArrayList<>();
        String cityCode = ApplicationThreadLocals.getCityCode();
        formData.add(new BasicNameValuePair(CollectionConstants.HDFC_KEY, collectionApplicationProperties.hdfcKey()));
        formData.add(new BasicNameValuePair(CollectionConstants.HDFC_COMMAND,
                collectionApplicationProperties.hdfcReconsileCommand()));
        // Calculate hash value.
        StringBuffer hashData = new StringBuffer(collectionApplicationProperties.hdfcKey());
        hashData.append(SINGLE_DELIMITER);
        hashData.append(collectionApplicationProperties.hdfcReconsileCommand());
        hashData.append(SINGLE_DELIMITER);
        hashData.append(cityCode + CollectionConstants.SEPARATOR_HYPHEN
                + onlinePayment.getReceiptHeader().getId().toString() + CollectionConstants.SEPARATOR_HYPHEN
                + onlinePayment.getReceiptHeader().getService().getCode());
        hashData.append(SINGLE_DELIMITER);
        hashData.append(collectionApplicationProperties.hdfcSalt());
        String hashValue = "";
        LOGGER.info("Reconcile - Hashing data before encryption" + hashData.toString());
        if (hashData.length() > 0) {
            hashValue = generateSecureHash(hashData.toString());
        }
        LOGGER.info("Reconcile - Hashing data before encryption" + hashData.toString());
        formData.add(new BasicNameValuePair(CollectionConstants.HDFC_HASH, hashValue));
        formData.add(new BasicNameValuePair(CollectionConstants.HDFC_VARIABLE,
                cityCode + CollectionConstants.SEPARATOR_HYPHEN + onlinePayment.getReceiptHeader().getId().toString()
                        + CollectionConstants.SEPARATOR_HYPHEN
                        + onlinePayment.getReceiptHeader().getService().getCode()));
        LOGGER.debug("HDFC  Reconcilation request: " + formData);

        UrlEncodedFormEntity urlEncodedFormEntity = null;
        try {
            urlEncodedFormEntity = new UrlEncodedFormEntity(formData);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return urlEncodedFormEntity;
    }

    private Date formatDate(String date) throws ApplicationException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date formattedDate = null;
        try {
            formattedDate = sdf.parse(date);
        } catch (ParseException e) {
            LOGGER.error("Error occured in parsing the transaction date [" + date + "]", e);
            throw new ApplicationException(".transactiondate.parse.error", e);
        }
        return formattedDate;
    }
}