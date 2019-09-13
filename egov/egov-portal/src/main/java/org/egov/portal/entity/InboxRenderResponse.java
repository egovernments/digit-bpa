package org.egov.portal.entity;

import java.util.List;

public class InboxRenderResponse {

    private Long totalServices;
    private Long underScrutiny;
    private Long completedServices;
    private List<PortalInboxHelper> portalInboxHelper;

    public Long getTotalServices() {
        return totalServices;
    }

    public void setTotalServices(Long totalServices) {
        this.totalServices = totalServices;
    }

    public Long getUnderScrutiny() {
        return underScrutiny;
    }

    public void setUnderScrutiny(Long underScrutiny) {
        this.underScrutiny = underScrutiny;
    }

    public Long getCompletedServices() {
        return completedServices;
    }

    public void setCompletedServices(Long completedServices) {
        this.completedServices = completedServices;
    }

    public List<PortalInboxHelper> getPortalInboxHelper() {
        return portalInboxHelper;
    }

    public void setPortalInboxHelper(List<PortalInboxHelper> portalInboxHelper) {
        this.portalInboxHelper = portalInboxHelper;
    }

}