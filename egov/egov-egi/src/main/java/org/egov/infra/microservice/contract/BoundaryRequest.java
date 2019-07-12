package org.egov.infra.microservice.contract;

import org.egov.infra.microservice.models.RequestInfo;

import com.fasterxml.jackson.annotation.JsonProperty;

 
public class BoundaryRequest {

	@JsonProperty("RequestInfo")
	private RequestInfo requestInfo;
	
	@JsonProperty("Boundary")
	private Boundary Boundary;

	public RequestInfo getRequestInfo() {
		return requestInfo;
	}

	public void setRequestInfo(RequestInfo requestInfo) {
		this.requestInfo = requestInfo;
	}

	public Boundary getBoundary() {
		return Boundary;
	}

	public void setBoundary(Boundary boundary) {
		Boundary = boundary;
	}
		

}
