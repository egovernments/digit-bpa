package org.egov.infra.microservice.contract;

import java.util.ArrayList;
import java.util.List;

 
public class MdmsBoundary {
	private String code;
	private String name;
	private String label;
	private String latitude;
	private String longitude;
	private String area;
	private Long boundaryNum;
	private List<MdmsBoundary> children = new ArrayList<MdmsBoundary>();
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public Long getBoundaryNum() {
		return boundaryNum;
	}
	public void setBoundaryNum(Long boundaryNum) {
		this.boundaryNum = boundaryNum;
	}
	public List<MdmsBoundary> getChildren() {
		return children;
	}
	public void setChildren(List<MdmsBoundary> children) {
		this.children = children;
	}
}
