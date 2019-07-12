package org.egov.infra.microservice.contract;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

 
public class BoundaryMdmsResponse {

	@JsonProperty("ResponseInfo")
	private ResponseInfo responseInfo = null;
	@JsonProperty("TenantBoundary")
	private List<MdmsTenantBoundary> boundarys = new ArrayList<MdmsTenantBoundary>();
	public ResponseInfo getResponseInfo() {
		return responseInfo;
	}
	public void setResponseInfo(ResponseInfo responseInfo) {
		this.responseInfo = responseInfo;
	}
	public List<MdmsTenantBoundary> getBoundarys() {
		return boundarys;
	}
	public void setBoundarys(List<MdmsTenantBoundary> boundarys) {
		this.boundarys = boundarys;
	}

}