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

package org.egov.infra.microservice.contract;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import groovy.transform.builder.Builder;


public class Boundary {

	private Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getBoundaryNum() {
		return boundaryNum;
	}
	public void setBoundaryNum(Long boundaryNum) {
		this.boundaryNum = boundaryNum;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getCodes() {
		return codes;
	}
	public void setCodes(String codes) {
		this.codes = codes;
	}
	public BoundaryType getBoundaryType() {
		return boundaryType;
	}
	public void setBoundaryType(BoundaryType boundaryType) {
		this.boundaryType = boundaryType;
	}
	public Boundary getParent() {
		return parent;
	}
	public void setParent(Boundary parent) {
		this.parent = parent;
	}
	public Set<Boundary> getChildren() {
		return children;
	}
	public void setChildren(Set<Boundary> children) {
		this.children = children;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public boolean isHistory() {
		return isHistory;
	}
	public void setHistory(boolean isHistory) {
		this.isHistory = isHistory;
	}
	public Long getBndryId() {
		return bndryId;
	}
	public void setBndryId(Long bndryId) {
		this.bndryId = bndryId;
	}
	public String getLocalName() {
		return localName;
	}
	public void setLocalName(String localName) {
		this.localName = localName;
	}
	public Float getLongitude() {
		return longitude;
	}
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	public Float getLatitude() {
		return latitude;
	}
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	public String getMaterializedPath() {
		return materializedPath;
	}
	public void setMaterializedPath(String materializedPath) {
		this.materializedPath = materializedPath;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Long getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Long createdDate) {
		this.createdDate = createdDate;
	}
	public Long getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(Long lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public Long getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Long lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	private String name;
	private Long boundaryNum;
	private String code;
	private String area;
	private String codes;
	private BoundaryType boundaryType;
	private Boundary parent;
	@JsonIgnore
	private Set<Boundary> children = new HashSet<>();
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date fromDate;
	private Date toDate;
	private boolean isHistory;
	private Long bndryId;
	@SafeHtml
	private String localName;
	private Float longitude;
	private Float latitude;
	@Length(max = 32)
	private String materializedPath;
	@Length(max = 256)
	private String tenantId;
	private Long createdBy;
	private Long createdDate;
	private Long lastModifiedBy;
	private Long lastModifiedDate;

}
