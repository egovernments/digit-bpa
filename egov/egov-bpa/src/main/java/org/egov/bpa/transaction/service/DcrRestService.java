package org.egov.bpa.transaction.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.egov.common.entity.dcr.helper.EdcrApplicationInfo;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@Service
public class DcrRestService {

    private static final String EDCR_REPORT_RESTURL = "%s/edcr/rest/approved-report-ouput/by-edcr-number/{dcrNumber}";

    public String getDcrReport(final String dcrNumber, final HttpServletRequest request) {
        final RestTemplate restTemplate = new RestTemplate();
        
        System.out.println("DcrRestService--------ApplicationThreadLocals.getDomainURL()--"+ApplicationThreadLocals.getDomainURL());
        final String url = String.format(EDCR_REPORT_RESTURL,ApplicationThreadLocals.getDomainURL());
        System.out.println("DcrRestService--------url--"+url);
        return restTemplate.getForObject(url, String.class, dcrNumber);
    }
    
    private static final String EDCR_PLANIFO_RESTURL = "%s/edcr/rest/approved-plan-details/by-edcr-number/{dcrNumber}";

    public  EdcrApplicationInfo getDcrPlanInfo(final String dcrNumber, final HttpServletRequest request) {
        final RestTemplate restTemplate = new RestTemplate();

        final String url = String.format(EDCR_PLANIFO_RESTURL,ApplicationThreadLocals.getDomainURL());

        return restTemplate.getForObject(url, EdcrApplicationInfo.class, dcrNumber);
   }
    
    private static final String EDCR_CONVERTED_PDF_RESTURL = "%s/edcr/rest/converted-pdf/by-edcr-number/{dcrNumber}";

    public String getConvertedPdfs(final String dcrNumber, final HttpServletRequest request) {
        final RestTemplate restTemplate = new RestTemplate();

        final String url = String.format(EDCR_CONVERTED_PDF_RESTURL, ApplicationThreadLocals.getDomainURL());

        return restTemplate.getForObject(url, String.class, dcrNumber);
    }
    
    public String getDcr(final String dcrNumber, final HttpServletRequest request) {
        final RestTemplate restTemplate = new RestTemplate();

        final String url = String.format(EDCR_REPORT_RESTURL,ApplicationThreadLocals.getDomainURL());

        return restTemplate.getForObject(url, String.class, dcrNumber);
    }
    
    private static final String EDCR_CREATED_DATE = "%s/edcr/rest/created-date/of-edcr-number/{dcrNumber}";
    
    
    public Date getDcrCreatedDate(final String dcrNumber, final HttpServletRequest request) {
        final RestTemplate restTemplate = new RestTemplate();

        final String url = String.format(EDCR_CREATED_DATE, ApplicationThreadLocals.getDomainURL());

        return restTemplate.getForObject(url, Date.class, dcrNumber);
    }
    
    private static final String EDCR_PPNO = "%s/edcr/rest/plan-permission-no/by-edcr-number/{dcrNumber}";
    public String getEdcrPlanPermissionNo(@PathVariable final String dcrNumber, final HttpServletRequest request){
    	 final RestTemplate restTemplate = new RestTemplate();

         final String url = String.format(EDCR_PPNO, ApplicationThreadLocals.getDomainURL());

         return restTemplate.getForObject(url, String.class, dcrNumber);
    }
}
