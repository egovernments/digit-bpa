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
package org.egov.bpa.master.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.egov.bpa.master.entity.SlotMapping;
import org.egov.bpa.master.entity.enums.SlotMappingApplType;
import org.egov.bpa.master.entity.enums.ApplicationType;
import org.egov.bpa.master.repository.SlotMappingRepository;
import org.egov.infra.admin.master.entity.Boundary;
import org.egov.infra.security.utils.SecurityUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SlotMappingService {

	@Autowired
	private SlotMappingRepository noOfApplicationsRepository;

	@Autowired
	private SecurityUtils securityUtils;

	@PersistenceContext
	private EntityManager entityManager;

	public Session getCurrentSession() {
		return entityManager.unwrap(Session.class);
	}

	public void createSlotMapping(SlotMapping slotMapping) {
		if(slotMapping.getApplType().toString().equals(ApplicationType.ONE_DAY_PERMIT.toString())){
		slotMapping.setDay(slotMapping.getDays().getHolidayTypeVal());
		}
		slotMapping.setCreatedBy(securityUtils.getCurrentUser());
		slotMapping.setCreatedDate(new Date());
		noOfApplicationsRepository.save(slotMapping);
	}

	public void updateSlotMapping(SlotMapping slotMapping) {
		SlotMapping slotMap = noOfApplicationsRepository.findOne(slotMapping.getId());
		slotMap.setApplType(slotMapping.getApplType());
		if(slotMapping.getApplType().toString().equals(ApplicationType.ONE_DAY_PERMIT.toString()))
			slotMap.setDay(slotMapping.getDays().getHolidayTypeVal());
		slotMap.setMaxSlotsAllowed(slotMapping.getMaxSlotsAllowed());
		slotMap.setMaxRescheduledSlotsAllowed(slotMapping.getMaxRescheduledSlotsAllowed());
		slotMap.setZone(slotMapping.getZone());
		slotMap.setRevenueWard(slotMapping.getRevenueWard());
		slotMap.setElectionWard(slotMapping.getElectionWard());
		slotMap.setLastModifiedBy(securityUtils.getCurrentUser());
		slotMap.setLastModifiedDate(new Date());
		noOfApplicationsRepository.save(slotMap);
	}

	@SuppressWarnings("unchecked")
	public List<SlotMapping> searchSlotMapping(SlotMapping slotMapping) {
		Criteria criteria = buildSearchCriteria(slotMapping);
		return criteria.list();
	}

	private Criteria buildSearchCriteria(SlotMapping slotMapping) {
		Criteria criteria = getCurrentSession().createCriteria(SlotMapping.class, "slotMapping");
		if (slotMapping.getZone() != null) {
			criteria.add(Restrictions.eq("slotMapping.zone", slotMapping.getZone()));
		}
		if (slotMapping.getApplType() != null) {
			criteria.add(Restrictions.eq("slotMapping.applType", slotMapping.getApplType()));
		}
		if (slotMapping.getElectionWard() != null) {
			criteria.add(Restrictions.eq("slotMapping.electionWard", slotMapping.getElectionWard()));
		}
		if (slotMapping.getRevenueWard() != null) {
			criteria.add(Restrictions.eq("slotMapping.revenueWard", slotMapping.getRevenueWard()));
		}
		if (slotMapping.getDays() != null) {
			criteria.add(Restrictions.eq("slotMapping.day", slotMapping.getDays().getHolidayTypeVal()));
		}
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria;
	}

	public SlotMapping findById(Long id) {
	 return	noOfApplicationsRepository.findOne(id);		
	}

	public List<SlotMapping> findByZoneAndAppType(Boundary zone, SlotMappingApplType applType) {
		return noOfApplicationsRepository.findByApplTypeAndZone(applType,zone);
	}

    public List<Boundary> slotfindZoneByApplType(SlotMappingApplType applType) {
        return noOfApplicationsRepository.findZoneByApplType(applType);

    }

    public List<SlotMapping> slotMappingForOneDayPermit(SlotMappingApplType applType) {
        return noOfApplicationsRepository.findSlotMappingForOneDayPermit(applType);

    }

    public List<SlotMapping> findByZoneElectionWardAndAppType(Boundary zone,
            Boundary electionWard, SlotMappingApplType applType) {
        return noOfApplicationsRepository.findByZoneElectionWardAndAppType(zone, electionWard,
                applType);

    }

}
