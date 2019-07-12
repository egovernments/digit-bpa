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

import java.util.List;
import java.util.Set;

import org.egov.infra.admin.master.entity.BoundaryType;
import org.egov.infra.admin.master.entity.HierarchyType;
import org.egov.infra.microservice.contract.SaveRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
public class BoundaryTypeRepository extends SaveRepository  {

  public  BoundaryType findByName(String name) {
		return null;
	}

  public  BoundaryType findByNameContainingIgnoreCase(String name) {
		return null;
	}

    @Query("select bt from BoundaryType bt where bt.hierarchyType.name=:hierarchyName and bt.hierarchy=:hierarchyLevel")
    public BoundaryType findByHierarchyTypeNameAndLevel(@Param("hierarchyName") String name,
            @Param("hierarchyLevel") Long hierarchyLevel) {
		return null;
	}

    public @Query("select bt from BoundaryType bt where bt.hierarchyType.id=:hierarchyId")
    List<BoundaryType> findByHierarchyTypeId(@Param("hierarchyId") Long id) {
		return null;
	}

     @Query("select bt from BoundaryType bt where bt.parent.id=:parentId")
     public BoundaryType findByParent(@Param("parentId") Long parentId) {
		return null;
	}

     @Query("select bt from BoundaryType bt where bt.id = :id and bt.hierarchyType.id = :hierarchyId")
     public BoundaryType findByIdAndHierarchy(@Param("id") Long id, @Param("hierarchyId") Long hierarchyId) {
		return null;
	}

     public BoundaryType findByNameAndHierarchyType(String name, HierarchyType hierarchyType) {
		return null;
	}

    @Query("select bt from BoundaryType bt where bt.name = :boundaryTypeName and bt.hierarchyType.name = :hierarchyTypeName")
    public BoundaryType findByNameAndHierarchyTypeName(@Param("boundaryTypeName") String name,
            @Param("hierarchyTypeName") String hierarchyTypeName) {
    	
		return null;
	}

    @Query("select bt from BoundaryType bt where bt.hierarchyType.name=:name")
    public List<BoundaryType> findByHierarchyTypeName(@Param("name") String hierarchyName) {
		return null;
	}

    @Query("select bt from BoundaryType bt where bt.hierarchyType.code in :names and bt.name like 'W%'")
    public List<BoundaryType> findByHierarchyTypeNames(@Param("names") final Set<String> names) {
		return null;
	}

	public BoundaryType findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void save(BoundaryType boundaryType) {
		// TODO Auto-generated method stub
		
	}
}
