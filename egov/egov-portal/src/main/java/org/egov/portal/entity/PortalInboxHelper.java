package org.egov.portal.entity;

import java.util.Date;

public class PortalInboxHelper {

    private String applicantName;
    private String serviceRequestNo;
    private Date serviceRequestDate;
    private String serviceGroup;
    private String serviceName;
    private String link;
    private String status;
    private String pendingAction;
    private String tenantId;
    private boolean resolved;

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getServiceRequestNo() {
        return serviceRequestNo;
    }

    public void setServiceRequestNo(String serviceRequestNo) {
        this.serviceRequestNo = serviceRequestNo;
    }

    public Date getServiceRequestDate() {
        return serviceRequestDate;
    }

    public void setServiceRequestDate(Date serviceRequestDate) {
        this.serviceRequestDate = serviceRequestDate;
    }

    public String getServiceGroup() {
        return serviceGroup;
    }

    public void setServiceGroup(String serviceGroup) {
        this.serviceGroup = serviceGroup;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPendingAction() {
        return pendingAction;
    }

    public void setPendingAction(String pendingAction) {
        this.pendingAction = pendingAction;
    }

    public boolean getResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

}