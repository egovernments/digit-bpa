package org.egov.bpa.transaction.service;

import org.egov.infra.web.utils.WebUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Service
public class DcrRestService {

    private static final String EDCR_REPORT_RESTURL = "%s/edcr/rest/approved-report-ouput/by-edcr-number/{dcrNumber}";

    public String getDcrReport(final String dcrNumber, final HttpServletRequest request) {
        final RestTemplate restTemplate = new RestTemplate();

        final String url = String.format(EDCR_REPORT_RESTURL, WebUtils.extractRequestDomainURL(request, false));

        return restTemplate.getForObject(url, String.class, dcrNumber);
    }

    private static final String EDCR_CONVERTED_PDF_RESTURL = "%s/edcr/rest/converted-pdf/by-edcr-number/{dcrNumber}";

    public String getConvertedPdfs(final String dcrNumber, final HttpServletRequest request) {
        final RestTemplate restTemplate = new RestTemplate();

        final String url = String.format(EDCR_CONVERTED_PDF_RESTURL, WebUtils.extractRequestDomainURL(request, false));

        return restTemplate.getForObject(url, String.class, dcrNumber);
    }
}
