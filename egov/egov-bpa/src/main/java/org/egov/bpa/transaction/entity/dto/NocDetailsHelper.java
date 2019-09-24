/*
 * eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 * accountability and the service delivery of the government  organizations.
 *
 *  Copyright (C) <2018>  eGovernments Foundation
 *
 *  The updated version of eGov suite of products as by eGovernments Foundation
 *  is available at http://www.egovernments.org
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see http://www.gnu.org/licenses/ or
 *  http://www.gnu.org/licenses/gpl.html .
 *
 *  In addition to the terms of the GPL license to be adhered to in using this
 *  program, the following additional terms are to be complied with:
 *
 *      1) All versions of this program, verbatim or modified must carry this
 *         Legal Notice.
 *      Further, all user interfaces, including but not limited to citizen facing interfaces,
 *         Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
 *         derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *      For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *      For any further queries on attribution, including queries on brand guidelines,
 *         please contact contact@egovernments.org
 *
 *      2) Any misrepresentation of the origin of the material is prohibited. It
 *         is required that all modified versions of this material be marked in
 *         reasonable ways as different from the original version.
 *
 *      3) This license does not grant any rights to any user of the program
 *         with regards to rights under trademark law for use of the trade names
 *         or trademarks of eGovernments Foundation.
 *
 *  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */

package org.egov.bpa.transaction.entity.dto;

import java.util.Date;

import org.egov.infra.web.support.search.DataTableSearchRequest;
import org.hibernate.validator.constraints.SafeHtml;

public class NocDetailsHelper extends DataTableSearchRequest {
    private Long nocStatusId;
    private Date nocApplicationDate;
    @SafeHtml
    private String nocApplicationNumber;
    @SafeHtml
    private String permitApplicationNo;
    @SafeHtml
    private String thirdPartyApplicationNo;
    @SafeHtml
    private String nocDepartmentName;
    @SafeHtml
    private String nocStatusName;
    private Date statusUpdatedDate;
    @SafeHtml
    private String remarks;

    public Long getNocStatusId() {
        return nocStatusId;
    }

    public void setNocStatusId(Long nocStatusId) {
        this.nocStatusId = nocStatusId;
    }

    public Date getNocApplicationDate() {
        return nocApplicationDate;
    }

    public void setNocApplicationDate(Date nocApplicationDate) {
        this.nocApplicationDate = nocApplicationDate;
    }

    public String getNocApplicationNumber() {
        return nocApplicationNumber;
    }

    public void setNocApplicationNumber(String nocApplicationNumber) {
        this.nocApplicationNumber = nocApplicationNumber;
    }

    public String getPermitApplicationNo() {
        return permitApplicationNo;
    }

    public void setPermitApplicationNo(String permitApplicationNo) {
        this.permitApplicationNo = permitApplicationNo;
    }

    public String getThirdPartyApplicationNo() {
        return thirdPartyApplicationNo;
    }

    public void setThirdPartyApplicationNo(String thirdPartyApplicationNo) {
        this.thirdPartyApplicationNo = thirdPartyApplicationNo;
    }

    public String getNocDepartmentName() {
        return nocDepartmentName;
    }

    public void setNocDepartmentName(String nocDepartmentName) {
        this.nocDepartmentName = nocDepartmentName;
    }

    public String getNocStatusName() {
        return nocStatusName;
    }

    public void setNocStatusName(String nocStatusName) {
        this.nocStatusName = nocStatusName;
    }

    public Date getStatusUpdatedDate() {
        return statusUpdatedDate;
    }

    public void setStatusUpdatedDate(Date statusUpdatedDate) {
        this.statusUpdatedDate = statusUpdatedDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}