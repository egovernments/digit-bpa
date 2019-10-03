/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2015>  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.bpa.transaction.entity;

import static org.egov.infra.validation.constants.ValidationErrorCode.INVALID_ADDRESS;
import static org.egov.infra.validation.constants.ValidationErrorCode.INVALID_EMAIL;
import static org.egov.infra.validation.constants.ValidationErrorCode.INVALID_NUMERIC;
import static org.egov.infra.validation.constants.ValidationErrorCode.INVALID_PERSON_NAME;
import static org.egov.infra.validation.constants.ValidationRegex.ADDRESS;
import static org.egov.infra.validation.constants.ValidationRegex.EMAIL;
import static org.egov.infra.validation.constants.ValidationRegex.NUMERIC;
import static org.egov.infra.validation.constants.ValidationRegex.PERSON_NAME;

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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.egov.bpa.transaction.entity.enums.GenderTitle;
import org.egov.infra.persistence.entity.AbstractAuditable;
import org.egov.infra.persistence.entity.enums.Gender;
import org.egov.portal.entity.Citizen;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Table(name = "EGBPA_APPLICANT")
@SequenceGenerator(name = Applicant.SEQ_APPLICANT, sequenceName = Applicant.SEQ_APPLICANT, allocationSize = 1)
public class Applicant extends AbstractAuditable {

    public static final String SEQ_APPLICANT = "SEQ_EGBPA_Applicant";
    private static final long serialVersionUID = 3078684328383202788L;

    @Id
    @GeneratedValue(generator = SEQ_APPLICANT, strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    private GenderTitle title;

    @Length(min = 1, max = 128)
    @Pattern(regexp = ADDRESS, message = INVALID_ADDRESS)
    private String fatherorHusbandName;

    private Date dateofBirth;

    @SafeHtml
    @Length(min = 1, max = 128)
    @Pattern(regexp = ADDRESS, message = INVALID_ADDRESS)
    private String district;

    @SafeHtml
    @Length(min = 1, max = 128)
    @Pattern(regexp = ADDRESS, message = INVALID_ADDRESS)
    private String taluk;

    @SafeHtml
    @Length(min = 1, max = 128)
    @Pattern(regexp = ADDRESS, message = INVALID_ADDRESS)
    private String area;

    @SafeHtml
    @Length(min = 1, max = 128)
    @Pattern(regexp = ADDRESS, message = INVALID_ADDRESS)
    private String city;

    @SafeHtml
    @Length(min = 1, max = 128)
    @Pattern(regexp = ADDRESS, message = INVALID_ADDRESS)
    private String state;

    @SafeHtml
    @Length(min = 1, max = 128)
    @Pattern(regexp = NUMERIC, message = INVALID_NUMERIC)
    private String pinCode;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private Citizen user;

    @NotNull
    @SafeHtml
    @Length(min = 2, max = 100)
    @Pattern(regexp = PERSON_NAME, message = INVALID_PERSON_NAME)
    private String name;

    @SafeHtml
    @Length(min = 1, max = 1024)
    @Pattern(regexp = ADDRESS, message = INVALID_ADDRESS)
    private String address;

    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    @Email(regexp = EMAIL, message = INVALID_EMAIL)
    @SafeHtml
    @Length(max = 128)
    private String emailId;

    @SafeHtml
    @Length(max = 20)
    @Pattern(regexp = NUMERIC, message = INVALID_NUMERIC)
    private String aadhaarNumber;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    public GenderTitle getTitle() {
        return title;
    }

    public void setTitle(final GenderTitle title) {
        this.title = title;
    }

    public Date getDateofBirth() {
        return dateofBirth;
    }

    public void setDateofBirth(final Date dateofBirth) {
        this.dateofBirth = dateofBirth;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(final String district) {
        this.district = district;
    }

    public String getTaluk() {
        return taluk;
    }

    public void setTaluk(final String taluk) {
        this.taluk = taluk;
    }

    public String getArea() {
        return area;
    }

    public void setArea(final String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(final String state) {
        this.state = state;
    }

    public String getFatherorHusbandName() {
        return fatherorHusbandName;
    }

    public void setFatherorHusbandName(final String fatherorHusbandName) {
        this.fatherorHusbandName = fatherorHusbandName;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(final String pinCode) {
        this.pinCode = pinCode;
    }

    public Citizen getUser() {
        return user;
    }

    public void setUser(Citizen user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }

    public String showAadhaarNumber() {
        return StringUtils.isBlank(aadhaarNumber) ? aadhaarNumber
                : aadhaarNumber.replaceAll("\\d(?=(?:\\D*\\d){4})", "*");
    }

    public String showMobileNumber() {
        return user == null || StringUtils.isBlank(user.getMobileNumber()) ? ""
                : user.getMobileNumber().replaceAll("\\d(?=(?:\\D*\\d){4})", "*");
    }

}
