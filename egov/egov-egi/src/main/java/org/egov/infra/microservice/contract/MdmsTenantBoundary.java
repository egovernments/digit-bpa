package org.egov.infra.microservice.contract;

import java.util.ArrayList;
import java.util.List;

 
public class MdmsTenantBoundary {

	private HierarchyType hierarchyType;
	private List<MdmsBoundary> boundary = new ArrayList<MdmsBoundary>();
	private String tenantId;
	public HierarchyType getHierarchyType() {
		return hierarchyType;
	}
	public void setHierarchyType(HierarchyType hierarchyType) {
		this.hierarchyType = hierarchyType;
	}
	public List<MdmsBoundary> getBoundary() {
		return boundary;
	}
	public void setBoundary(List<MdmsBoundary> boundary) {
		this.boundary = boundary;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
}

