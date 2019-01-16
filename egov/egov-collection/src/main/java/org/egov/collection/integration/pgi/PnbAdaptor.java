package org.egov.collection.integration.pgi;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.egov.collection.config.properties.CollectionApplicationProperties;
import org.egov.collection.constants.CollectionConstants;
import org.egov.collection.entity.OnlinePayment;
import org.egov.collection.entity.ReceiptHeader;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.exception.ApplicationException;
import org.egov.infra.exception.ApplicationRuntimeException;
import org.egov.infstr.models.ServiceDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.awl.merchanttoolkit.dto.ReqMsgDTO;
import com.awl.merchanttoolkit.dto.ResMsgDTO;
import com.awl.merchanttoolkit.transaction.AWLMEAPI;

public class PnbAdaptor implements PaymentGatewayAdaptor {

	private static final Logger LOGGER = Logger.getLogger(PnbAdaptor.class);
	private static final BigDecimal PAISE_RUPEE_CONVERTER = BigDecimal.valueOf(100);

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private CollectionApplicationProperties collectionApplicationProperties;

	@Override
	public PaymentRequest createPaymentRequest(ServiceDetails paymentServiceDetails, ReceiptHeader receiptHeader) {
		LOGGER.debug("inside createPaymentRequest");
		final DefaultPaymentRequest paymentRequest = new DefaultPaymentRequest();
		final BigDecimal amount = receiptHeader.getTotalAmount();
		final float rupees = Float.parseFloat(amount.toString());
		final Integer rupee = (int) rupees;
		final Float exponent = rupees - (float) rupee;
		final Integer paise = (int) (rupee * PAISE_RUPEE_CONVERTER.intValue()
				+ exponent * PAISE_RUPEE_CONVERTER.intValue());

		ReqMsgDTO pnbReqMsgDTO = new ReqMsgDTO();
		pnbReqMsgDTO.setMid(collectionApplicationProperties.pnbMid());
		pnbReqMsgDTO.setOrderId(receiptHeader.getId().toString());
		pnbReqMsgDTO.setTrnAmt(paise.toString());// in paise

		pnbReqMsgDTO.setTrnCurrency(collectionApplicationProperties.pnbTransactionCurrency());
		pnbReqMsgDTO.setTrnRemarks(receiptHeader.getService().getName());
		pnbReqMsgDTO.setMeTransReqType(collectionApplicationProperties.pnbTransactionRequestType());
		pnbReqMsgDTO.setEnckey(collectionApplicationProperties.pnbEncryptionKey());
		final StringBuilder returnUrl = new StringBuilder();
		returnUrl.append(paymentServiceDetails.getCallBackurl()).append("?paymentServiceId=")
				.append(paymentServiceDetails.getId());
		pnbReqMsgDTO.setResponseUrl(returnUrl.toString());
		pnbReqMsgDTO.setAddField1(ApplicationThreadLocals.getCityCode());
		pnbReqMsgDTO.setAddField2(receiptHeader.getConsumerCode());
		AWLMEAPI objAWLMEAPI = new AWLMEAPI();
		try {
			pnbReqMsgDTO = objAWLMEAPI.generateTrnReqMsg(pnbReqMsgDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String merchantRequest = null;
		if (pnbReqMsgDTO.getStatusDesc().equals(CollectionConstants.PNB_TRANSACTION_STATUS_DESC)) {
			merchantRequest = pnbReqMsgDTO.getReqMsg();
		}
		paymentRequest.setParameter(CollectionConstants.PNB_MERCHANT_REQUEST, merchantRequest);
		paymentRequest.setParameter(CollectionConstants.PNB_MID, collectionApplicationProperties.pnbMid());
		paymentRequest.setParameter(CollectionConstants.ONLINEPAYMENT_INVOKE_URL,
				paymentServiceDetails.getServiceUrl());
		LOGGER.info("====== paymentRequest =========");
		LOGGER.info(" MERCHANT REQUEST : "
				+ paymentRequest.requestParameters.get(CollectionConstants.PNB_MERCHANT_REQUEST));
		LOGGER.info(" MID : " + paymentRequest.requestParameters.get(CollectionConstants.PNB_MID));
		LOGGER.info(" PAYMENT GATEWAY URL : "
				+ paymentRequest.requestParameters.get(CollectionConstants.ONLINEPAYMENT_INVOKE_URL));
		return paymentRequest;
	}

	/**
	 * This method parses the given response string into a Punjab national bank
	 * payment response object.
	 *
	 * @param a
	 *            <code>String</code> representation of the response.
	 * @return an instance of <code></code> containing the response information
	 */
	@Override
	public PaymentResponse parsePaymentResponse(final String response) {
		LOGGER.info("Response message from PNB Payment gateway: " + response);
		final PaymentResponse pnbResponse = new DefaultPaymentResponse();
		String merchantResponse = "";
		try {
			if (response != null && !response.isEmpty()) {
				String[] splitData = response.split(",");
				if (splitData[1] != "")
					merchantResponse = splitData[1].split("=")[1];
			}
			AWLMEAPI objAWLMEAPI = new AWLMEAPI();
			ResMsgDTO objResMsgDTO = objAWLMEAPI.parseTrnResMsg(merchantResponse,
					collectionApplicationProperties.pnbEncryptionKey());
			// Punjab national bank Payment Gateway returns Response Code 'S'
			// for successful
			// transactions, so converted it to 0300
			// as that is being followed as a standard in other payment
			// gateways.
			pnbResponse.setAuthStatus(objResMsgDTO.getStatusCode().equals("S")
					? CollectionConstants.PGI_AUTHORISATION_CODE_SUCCESS : objResMsgDTO.getStatusCode());
			pnbResponse.setErrorDescription(objResMsgDTO.getStatusDesc());
			pnbResponse.setAdditionalInfo6(objResMsgDTO.getAddField2().replace("-", "").replace("/", ""));
			pnbResponse.setReceiptId(objResMsgDTO.getOrderId());

                        LOGGER.info("Response message from PNB Payment gateway: Auth Status: " + pnbResponse.getAuthStatus());
                        LOGGER.info("Response message from PNB Payment gateway: Error Description: " + pnbResponse.getErrorDescription());
                        LOGGER.info("Response message from PNB Payment gateway: AdditionalInfo6: " + pnbResponse.getAdditionalInfo6());
                        LOGGER.info("Response message from PNB Payment gateway: ReceiptId: " + pnbResponse.getReceiptId());

			// Success
			if (pnbResponse.getAuthStatus().equals(CollectionConstants.PGI_AUTHORISATION_CODE_SUCCESS)) {
				pnbResponse.setTxnAmount(new BigDecimal(objResMsgDTO.getTrnAmt()).divide(PAISE_RUPEE_CONVERTER));
				pnbResponse.setTxnReferenceNo(objResMsgDTO.getPgMeTrnRefNo());
				pnbResponse.setTxnDate(getTransactionDate(objResMsgDTO.getTrnReqDate()));
			}
		} catch (final Exception exp) {
			LOGGER.error(exp);
			throw new ApplicationRuntimeException("Exception during prepare payment response" + exp.getMessage());
		}

		return pnbResponse;
	}

	private Date getTransactionDate(final String transDate) throws ApplicationException {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		try {
			return sdf.parse(transDate);
		} catch (final ParseException e) {
			LOGGER.error("Error occured in parsing the transaction date [" + transDate + "]", e);
			throw new ApplicationException(".transactiondate.parse.error", e);
		}
	}

	@Transactional
	public PaymentResponse createOfflinePaymentRequest(final OnlinePayment onlinePayment) {
		LOGGER.debug("Inside createOfflinePaymentRequest");
		final PaymentResponse pnbResponse = new DefaultPaymentResponse();
		ResMsgDTO pnbResMsgDTO = new ResMsgDTO();
		AWLMEAPI objAWLMEAPI = new AWLMEAPI();
		try {
			pnbResMsgDTO = objAWLMEAPI.getTransactionStatus(collectionApplicationProperties.pnbMid(),
					onlinePayment.getReceiptHeader().getId().toString(), onlinePayment.getTransactionNumber(),
					collectionApplicationProperties.pnbEncryptionKey(),System.getProperty("pnb.payment.integration.prop"));
			pnbResponse.setAuthStatus(pnbResMsgDTO.getStatusCode().equals("S")
					? CollectionConstants.PGI_AUTHORISATION_CODE_SUCCESS : pnbResMsgDTO.getStatusCode());
			pnbResponse.setErrorDescription(pnbResMsgDTO.getStatusDesc());
			pnbResponse.setAdditionalInfo6(pnbResMsgDTO.getAddField2().replace("-", "").replace("/", ""));
			if(pnbResMsgDTO.getOrderId().equalsIgnoreCase("NA"))
				pnbResponse.setReceiptId(onlinePayment.getReceiptHeader().getId().toString());
			else	
				pnbResponse.setReceiptId(pnbResMsgDTO.getOrderId());

			// Success
			if (pnbResponse.getAuthStatus().equals(CollectionConstants.PGI_AUTHORISATION_CODE_SUCCESS)) {
				pnbResponse.setTxnAmount(new BigDecimal(pnbResMsgDTO.getTrnAmt()).divide(PAISE_RUPEE_CONVERTER));
				pnbResponse.setTxnReferenceNo(pnbResMsgDTO.getPgMeTrnRefNo());
				pnbResponse.setTxnDate(getTransactionDate(pnbResMsgDTO.getTrnReqDate()));
			}
			LOGGER.debug(
					"receiptid=" + pnbResponse.getReceiptId() + "consumercode=" + pnbResponse.getAdditionalInfo6());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.error(e);
		}
		return pnbResponse;
	}
}
