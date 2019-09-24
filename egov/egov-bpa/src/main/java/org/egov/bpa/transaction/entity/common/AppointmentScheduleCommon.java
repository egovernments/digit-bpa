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

package org.egov.bpa.transaction.entity.common;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.egov.bpa.master.entity.AppointmentLocations;
import org.egov.bpa.transaction.entity.enums.AppointmentSchedulePurpose;
import org.egov.infra.persistence.entity.AbstractAuditable;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Table(name = "EGBPA_APPOINTMENT_SCHEDULE_COMMON")
@SequenceGenerator(name = AppointmentScheduleCommon.SEQ_EGBPA_APPOINTMENT_SCHEDULE, sequenceName = AppointmentScheduleCommon.SEQ_EGBPA_APPOINTMENT_SCHEDULE, allocationSize = 1)
public class AppointmentScheduleCommon extends AbstractAuditable {

    public static final String SEQ_EGBPA_APPOINTMENT_SCHEDULE = "SEQ_EGBPA_APPOINTMENT_SCHEDULE_COMMON";
    private static final long serialVersionUID = 8521605863828619973L;

    @Id
    @GeneratedValue(generator = SEQ_EGBPA_APPOINTMENT_SCHEDULE, strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private AppointmentSchedulePurpose purpose;

    @Temporal(value = TemporalType.DATE)
    private Date appointmentDate;

    @SafeHtml
    @Length(min = 1, max = 50)
    private String appointmentTime;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "appointmentLocation")
    private AppointmentLocations appointmentLocation;

    @SafeHtml
    @Length(min = 1, max = 256)
    private String remarks;

    @SafeHtml
    @Length(min = 1, max = 256)
    private String postponementReason;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent")
    private AppointmentScheduleCommon parent;

    private boolean isPostponed;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    public AppointmentSchedulePurpose getPurpose() {
        return purpose;
    }

    public void setPurpose(AppointmentSchedulePurpose purpose) {
        this.purpose = purpose;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(final Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(final String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public AppointmentLocations getAppointmentLocation() {
        return appointmentLocation;
    }

    public void setAppointmentLocation(AppointmentLocations appointmentLocation) {
        this.appointmentLocation = appointmentLocation;
    }

    public String getPostponementReason() {
        return postponementReason;
    }

    public void setPostponementReason(final String postponementReason) {
        this.postponementReason = postponementReason;
    }

    public AppointmentScheduleCommon getParent() {
        return parent;
    }

    public void setParent(final AppointmentScheduleCommon parent) {
        this.parent = parent;
    }

    public boolean isPostponed() {
        return isPostponed;
    }

    public void setPostponed(final boolean isPostponed) {
        this.isPostponed = isPostponed;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(final String remarks) {
        this.remarks = remarks;
    }

}
