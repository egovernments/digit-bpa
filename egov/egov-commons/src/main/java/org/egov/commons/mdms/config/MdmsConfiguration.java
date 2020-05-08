package org.egov.commons.mdms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MdmsConfiguration {

    @Value("${mdms.host}")
    private String mdmsHost;

    @Value("${mdms.searchurl}")
    private String mdmsSearchUrl;

    public String getMdmsHost() {
        return mdmsHost;
    }

    public void setMdmsHost(String mdmsHost) {
        this.mdmsHost = mdmsHost;
    }

    public String getMdmsSearchUrl() {
        return mdmsSearchUrl;
    }

    public void setMdmsSearchUrl(String mdmsSearchUrl) {
        this.mdmsSearchUrl = mdmsSearchUrl;
    }

}
