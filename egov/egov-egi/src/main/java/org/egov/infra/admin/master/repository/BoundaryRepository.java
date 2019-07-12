/*
 *    eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) 2017  eGovernments Foundation
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
 *            Further, all user interfaces, including but not limited to citizen facing interfaces,
 *            Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
 *            derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *            For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *            For any further queries on attribution, including queries on brand guidelines,
 *            please contact contact@egovernments.org
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
 *
 */

package org.egov.infra.admin.master.repository;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.hibernate.jpa.QueryHints.HINT_CACHEABLE;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.QueryHint;

import org.apache.log4j.Logger;
import org.egov.infra.admin.master.entity.Boundary;
import org.egov.infra.admin.master.entity.BoundaryType;
import org.egov.infra.admin.master.entity.HierarchyType;
import org.egov.infra.microservice.contract.BoundaryMdmsResponse;
import org.egov.infra.microservice.contract.BoundaryRequest;
import org.egov.infra.microservice.contract.MdmsBoundary;
import org.egov.infra.microservice.contract.MdmsTenantBoundary;
import org.egov.infra.microservice.contract.RequestInfoWrapper;
import org.egov.infra.microservice.models.RequestInfo;
import org.egov.infra.microservice.models.UserInfo;
import org.egov.infra.microservice.utils.MicroserviceUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BoundaryRepository {
	private static final Logger LOGGER = Logger.getLogger(MicroserviceUtils.class);

	@QueryHints({ @QueryHint(name = HINT_CACHEABLE, value = "true") })
	public Boundary findByName(String name) {
		return null;
	}

	public List<Boundary> findByNameContainingIgnoreCase(String name) {
		return null;
	}

	public List<Boundary> findByBoundaryTypeId(Long boundaryType) {
		return null;
	}

	public Page<Boundary> findByBoundaryTypeId(Long boundaryType, Pageable pageable) {
		return null;
	}

	public Boundary findByBoundaryTypeAndBoundaryNum(BoundaryType boundaryType, Long boundaryNum) {
		return null;
	}

	// Not found api in RM
	@Query("select b from Boundary b where b.active=true AND b.boundaryType.id =:boundaryTypeId order by b.name")
	public List<Boundary> findActiveBoundariesByBoundaryTypeId(@Param("boundaryTypeId") Long boundaryTypeId) {
		List<MdmsTenantBoundary> boundaryList = MicroserviceUtils.getBoundaryById(null, null);
		List<Boundary> bs = new ArrayList<>();

		for (MdmsTenantBoundary b : boundaryList) {
			for (MdmsBoundary mb : b.getBoundary()) {
				Boundary bb = new Boundary();
				bb.setName(mb.getName());
				bb.setCode(mb.getCode());
				bb.setBoundaryNum(mb.getBoundaryNum());
				bs.add(bb);

			}
		}

		return bs;
	}

	@Query("select b from Boundary b where b.active=true AND b.boundaryType.hierarchyType = :hierarchyType "
			+ "AND b.boundaryType.hierarchy = :hierarchyLevel AND ((b.toDate IS NULL AND b.fromDate <= :asOnDate) "
			+ "OR (b.toDate IS NOT NULL AND b.fromDate <= :asOnDate AND b.toDate >= :asOnDate)) order by b.name")
	public List<Boundary> findActiveBoundariesByHierarchyTypeAndLevelAndAsOnDate(
			@Param("hierarchyType") HierarchyType hierarchyType, @Param("hierarchyLevel") Long hierarchyLevel,
			@Param("asOnDate") Date asOnDate) {
		return null;
	}

	@Query("select b from Boundary b where b.active=true AND b.parent is not null AND b.parent.id = :parentBoundaryId "
			+ "AND ((b.toDate IS NULL AND b.fromDate <= :asOnDate) OR (b.toDate IS NOT NULL AND b.fromDate <= :asOnDate "
			+ "AND b.toDate >= :asOnDate)) order by b.name")
	public List<Boundary> findActiveChildBoundariesByBoundaryIdAndAsOnDate(
			@Param("parentBoundaryId") Long parentBoundaryId, @Param("asOnDate") Date asOnDate) {
		return null;
	}

	@Query("from Boundary BND where BND.active=true AND BND.materializedPath like (select B.materializedPath "
			+ "from Boundary B where B.id=:parentId)||'%'")
	public List<Boundary> findActiveChildrenWithParent(@Param("parentId") String parentId) {
		return null;
	}

	@Query("from Boundary BND where BND.active=true AND BND.materializedPath in :mpath ")
	public List<Boundary> findActiveBoundariesForMpath(@Param("mpath") final Set<String> mpath) {
		return null;
	}

	@Query("select b from Boundary b where b.parent is not null AND b.parent.id = :parentBoundaryId "
			+ "AND ((b.toDate IS NULL AND b.fromDate <= :asOnDate) OR (b.toDate IS NOT NULL AND b.fromDate <= :asOnDate "
			+ "AND b.toDate >= :asOnDate)) order by b.name")
	public List<Boundary> findChildBoundariesByBoundaryIdAndAsOnDate(@Param("parentBoundaryId") Long parentBoundaryId,
			@Param("asOnDate") Date asOnDate) {
		return null;
	}

	@Query("select b from Boundary b where b.active=true AND b.boundaryNum = :boundaryNum "
			+ "AND b.boundaryType.name = :boundaryType AND upper(b.boundaryType.hierarchyType.code) = :hierarchyTypeCode "
			+ "AND ((b.toDate IS NULL AND b.fromDate <= :asOnDate) OR (b.toDate IS NOT NULL AND b.fromDate <= :asOnDate "
			+ "AND b.toDate >= :asOnDate))")
	public Boundary findActiveBoundaryByBndryNumAndTypeAndHierarchyTypeCodeAndAsOnDate(
			@Param("boundaryNum") Long boundaryNum, @Param("boundaryType") String boundaryType,
			@Param("hierarchyTypeCode") String hierarchyTypeCode, @Param("asOnDate") Date asOnDate) {
		return null;
	}

	@Query("select b from Boundary b where b.active=true AND upper(b.boundaryType.name) = upper(:boundaryTypeName) "
			+ "AND upper(b.boundaryType.hierarchyType.name) = upper(:hierarchyTypeName) order by b.name")
	public List<Boundary> findActiveBoundariesByBndryTypeNameAndHierarchyTypeName(
			@Param("boundaryTypeName") String boundaryTypeName, @Param("hierarchyTypeName") String hierarchyTypeName) {

		List<MdmsTenantBoundary> boundaryList = MicroserviceUtils.getBoundaryById(boundaryTypeName, hierarchyTypeName);
		List<Boundary> bs = new ArrayList<>();

		for (MdmsTenantBoundary b : boundaryList) {
			for (MdmsBoundary mb : b.getBoundary()) {
				Boundary bb = new Boundary();
				bb.setName(mb.getName());
				bb.setCode(mb.getCode());
				bb.setBoundaryNum(mb.getBoundaryNum());
				// bb.setParent();
				bs.add(bb);

			}
		}

		return bs;

	}

	@Query("select b from Boundary b where upper(b.boundaryType.name) = UPPER(:boundaryTypeName) "
			+ "AND upper(b.boundaryType.hierarchyType.name) = UPPER(:hierarchyTypeName) order by b.id")
	public List<Boundary> findBoundariesByBndryTypeNameAndHierarchyTypeName(
			@Param("boundaryTypeName") String boundaryTypeName, @Param("hierarchyTypeName") String hierarchyTypeName) {
		return null;
	}

	@Query("select b from Boundary b where upper(b.boundaryType.name) = UPPER(:boundaryTypeName) "
			+ "AND upper(b.boundaryType.hierarchyType.name) = UPPER(:hierarchyTypeName) order by b.id")
	public Boundary findBoundaryByBndryTypeNameAndHierarchyTypeName(@Param("boundaryTypeName") String boundaryTypeName,
			@Param("hierarchyTypeName") String hierarchyTypeName) {
		return null;
	}

	@Query("select b from Boundary b where b.active=true and UPPER(b.name) like UPPER(:boundaryName) "
			+ "and b.boundaryType.id=:boundaryTypeId order by b.boundaryNum asc")
	public List<Boundary> findByNameAndBoundaryTypeOrderByBoundaryNumAsc(@Param("boundaryName") String boundaryName,
			@Param("boundaryTypeId") Long boundaryTypeId) {
		return null;
	}

	@Query("select b from Boundary b where b.boundaryType.name=:boundaryType and b.boundaryType.hierarchyType.name=:hierarchyType "
			+ "and b.boundaryType.hierarchy=:hierarchyLevel")
	public Boundary findByBoundaryTypeNameAndHierarchyTypeNameAndLevel(@Param("boundaryType") String boundaryType,
			@Param("hierarchyType") String hierarchyType, @Param("hierarchyLevel") Long hierarchyLevel) {
		return null;
	}

	@Query("select b from Boundary b where b.active=true AND upper(b.boundaryType.name) = upper(:boundaryTypeName) "
			+ "AND upper(b.boundaryType.hierarchyType.name) = upper(:hierarchyTypeName) AND UPPER(b.name) "
			+ "like UPPER(:name)||'%' order by b.id")
	public List<Boundary> findActiveBoundariesByNameAndBndryTypeNameAndHierarchyTypeName(
			@Param("boundaryTypeName") String boundaryTypeName, @Param("hierarchyTypeName") String hierarchyTypeName,
			@Param("name") String name) {
		return null;
	}

	@Query("from Boundary BND where BND.active=true AND BND.parent.id=:parentId)")
	public List<Boundary> findActiveImmediateChildrenWithOutParent(@Param("parentId") String parentCode) {

		LOGGER.info("Calling get Boundary........parentId." + parentCode);
		StringBuffer url = new StringBuffer();
		url.append("/egov-location/location/v11/boundarys/_search?tenantId=pb.amritsar&codes=" + parentCode);

		// hierarchyTypeCode="+"ADMIN"+"&boundaryType="+"Locality"+
		List<Boundary> bs = new ArrayList<>();
		String host = "https://egov-micro-qa.egovernments.org";
		String authToken = "0d305edf-5416-4996-bace-e6faccf77b2b";
		List<MdmsTenantBoundary> boundarys = new ArrayList<>();
		if (isNotBlank(url)) {
			final RestTemplate restTemplate = new RestTemplate();
			BoundaryMdmsResponse tresp;
			try {

				UserInfo userInfo = new UserInfo();
				userInfo.setTenantId("pb.amritsar");
				final RequestInfo requestInfo = new RequestInfo();
				requestInfo.setApiId("apiId");
				requestInfo.setVer("ver");
				requestInfo.setTs(new Date());
				requestInfo.setUserInfo(userInfo);
				requestInfo.setAuthToken(authToken);

				RequestInfoWrapper requestInfo1 = new RequestInfoWrapper();
				final BoundaryRequest brequest = new BoundaryRequest();

				requestInfo1.setRequestInfo(requestInfo);

				tresp = restTemplate.postForObject(host + url.toString(), requestInfo1, BoundaryMdmsResponse.class);
				boundarys = tresp.getBoundarys();

				for (MdmsTenantBoundary b : boundarys) {
					for (MdmsBoundary mb : b.getBoundary())
					{
						for (MdmsBoundary c : mb.getChildren())
						{
						LOGGER.info("code ---"+c.getCode());
						if(c.getCode().equalsIgnoreCase(parentCode))
						{
						for (MdmsBoundary mbb : c.getChildren()) {
							Boundary bb = new Boundary();
							bb.setName(mbb.getName());
							bb.setCode(mbb.getCode());
							bb.setBoundaryNum(mbb.getBoundaryNum());
							// bb.setParent();
							bs.add(bb);
						}
						}
						}
					}
				}

			} catch (final Exception e) {
				final String errMsg = "Exception while getting boundary  from microservice ";
				// throw new ApplicationRuntimeException(errMsg, e);
				LOGGER.fatal(errMsg, e);
			}
		}
		LOGGER.info("Got Boundary........." + boundarys.size());
		return bs;
	}

	@Query("from Boundary BND where BND.parent is null")
	public List<Boundary> findAllParents() {
		return null;
	}

	public List<Boundary> findByBoundaryTypeOrderByBoundaryNumAsc(BoundaryType boundaryType) {
		return null;
	}

	public Boundary findById(Long id) {
		Boundary b=new Boundary();
		b.setId(id);
		return b;
	}

	public Boundary findByCode(String code) {
		Boundary b=new Boundary();
		b.setCode(code);
		b.setName(code);
		return b;
	}
}