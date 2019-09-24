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

public class SlotDetailsHelper extends DataTableSearchRequest {
    @SafeHtml
    private String applicationType;
    private Long slotId;
    private Long slotDetailsId;
    @SafeHtml
    private String appointmentTime;
    private Date appointmentDate;
    private Long zoneId;
    @SafeHtml
    private String zone;
    private Long revenueWardId;
    @SafeHtml
    private String revenueWardWard;
    private Long electionWardId;
    @SafeHtml
    private String electionWard;
    private Integer byNoOfDays;
    private Integer maxScheduledSlots;
    private Integer maxRescheduledSlots;
    private Integer utilizedScheduledSlots;
    private Integer utilizedRescheduledSlots;

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public Long getSlotId() {
        return slotId;
    }

    public void setSlotId(Long slotId) {
        this.slotId = slotId;
    }

    public Long getSlotDetailsId() {
        return slotDetailsId;
    }

    public void setSlotDetailsId(Long slotDetailsId) {
        this.slotDetailsId = slotDetailsId;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public Long getRevenueWardId() {
        return revenueWardId;
    }

    public void setRevenueWardId(Long revenueWardId) {
        this.revenueWardId = revenueWardId;
    }

    public String getRevenueWardWard() {
        return revenueWardWard;
    }

    public void setRevenueWardWard(String revenueWardWard) {
        this.revenueWardWard = revenueWardWard;
    }

    public Long getElectionWardId() {
        return electionWardId;
    }

    public void setElectionWardId(Long electionWardId) {
        this.electionWardId = electionWardId;
    }

    public String getElectionWard() {
        return electionWard;
    }

    public void setElectionWard(String electionWard) {
        this.electionWard = electionWard;
    }

    public Integer getByNoOfDays() {
        return byNoOfDays;
    }

    public void setByNoOfDays(Integer byNoOfDays) {
        this.byNoOfDays = byNoOfDays;
    }

    public Integer getMaxScheduledSlots() {
        return maxScheduledSlots;
    }

    public void setMaxScheduledSlots(Integer maxScheduledSlots) {
        this.maxScheduledSlots = maxScheduledSlots;
    }

    public Integer getMaxRescheduledSlots() {
        return maxRescheduledSlots;
    }

    public void setMaxRescheduledSlots(Integer maxRescheduledSlots) {
        this.maxRescheduledSlots = maxRescheduledSlots;
    }

    public Integer getUtilizedScheduledSlots() {
        return utilizedScheduledSlots;
    }

    public void setUtilizedScheduledSlots(Integer utilizedScheduledSlots) {
        this.utilizedScheduledSlots = utilizedScheduledSlots;
    }

    public Integer getUtilizedRescheduledSlots() {
        return utilizedRescheduledSlots;
    }

    public void setUtilizedRescheduledSlots(Integer utilizedRescheduledSlots) {
        this.utilizedRescheduledSlots = utilizedRescheduledSlots;
    }
}