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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.egov.bpa.master.entity.BpaFee;
import org.egov.bpa.master.entity.BpaFeeCommon;
import org.egov.bpa.master.entity.BpaFeeMapping;
import org.egov.bpa.master.entity.enums.ApplicationType;
import org.egov.bpa.master.entity.enums.FeeApplicationType;
import org.egov.bpa.master.entity.enums.FeeSubType;
import org.egov.bpa.master.repository.BpaFeeCommonRepository;
import org.egov.bpa.utils.BpaConstants;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

@Service
@Transactional(readOnly = true)
public class BpaFeeCommonService {

	@Autowired
	private BpaFeeCommonRepository bpaFeeCommonRepository;
	@PersistenceContext
	private EntityManager entityManager;

	public List<BpaFeeCommon> findAll() {
		return bpaFeeCommonRepository.findAll();
	}

	public Session getCurrentSession() {
		return entityManager.unwrap(Session.class);
	}

	public BpaFeeCommon findById(final Long id) {
		return bpaFeeCommonRepository.findOne(id);
	}

	@Transactional
	public BpaFeeCommon update(final BpaFeeCommon bpaFeeCommon) {
		return bpaFeeCommonRepository.save(bpaFeeCommon);
	}

    public List<FeeSubType> getFeeSubTypes() {
        List<FeeSubType> feeSubTypes = new ArrayList<>();
        for (FeeSubType feeSubType : FeeSubType.values())
        	feeSubTypes.add(feeSubType);
        return feeSubTypes;
    }
    
    public List<FeeApplicationType> getFeeAppTypes() {
        List<FeeApplicationType> appTypes = new ArrayList<>();
        for (FeeApplicationType appType : FeeApplicationType.values())
        	appTypes.add(appType);
        return appTypes;
    }
    
	public void validateFeeList(final BpaFeeMapping bpaFeeMap, final BindingResult errors) {
		List<BpaFeeCommon> bpaFeeCommon = findByName(bpaFeeMap.getBpaFeeCommon().getName());
		if (!bpaFeeCommon.isEmpty())
			errors.rejectValue("bpaFeeCommon.name", "msg.fee.name.exists");

		List<BpaFeeCommon> bpaFeeList = findByCode(bpaFeeMap.getBpaFeeCommon().getName());
		if (!bpaFeeList.isEmpty())
			errors.rejectValue("bpaFeeCommon.code", "msg.fee.code.exists");

		Map<String, BpaFeeMapping> feeMap = new HashMap<>();
		for (BpaFeeMapping feeMp : bpaFeeMap.getBpaFeeMapTemp()) {
			String key = feeMp.getApplicationType().toString() + "-" + feeMp.getFeeSubType().toString() + "-"
					+ feeMp.getServiceType().getDescription();
			if (feeMap.get(key) != null) {
				errors.rejectValue("applicationType", "msg.fee.combination.exists");

			} else {
				feeMap.put(key, feeMp);
			}

		}
	}
	
	@SuppressWarnings("unchecked")
	public List<BpaFeeMapping> getOCSanctionFeeForListOfServices(Long serviceType) {
		final Criteria feeCrit = getCurrentSession().createCriteria(BpaFeeMapping.class, "bpafeeMap")
				.createAlias("bpafeeMap.bpaFeeCommon", "bpaFeeObj")
				.createAlias("bpafeeMap.serviceType", "servicetypeObj");
		feeCrit.add(Restrictions.eq("servicetypeObj.id", serviceType));
		feeCrit.add(Restrictions.eq("bpafeeMap.applicationType", FeeApplicationType.OCCUPANCY_CERTIFICATE));
		feeCrit.add(Restrictions.eq("bpafeeMap.feeSubType",FeeSubType.SANCTION_FEE));
		feeCrit.add(Restrictions.ilike("bpaFeeObj.name", BpaConstants.OC_FEE));
		return feeCrit.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<BpaFeeMapping> getOCFeeForListOfServices(Long serviceType) {
		final Criteria feeCrit = getCurrentSession().createCriteria(BpaFeeMapping.class, "bpafeeMap")
				.createAlias("bpafeeMap.bpaFeeCommon", "bpaFeeObj")
				.createAlias("bpafeeMap.serviceType", "servicetypeObj");
		feeCrit.add(Restrictions.eq("servicetypeObj.id", serviceType));
		feeCrit.add(Restrictions.eq("bpafeeMap.applicationType", FeeApplicationType.OCCUPANCY_CERTIFICATE));
		feeCrit.add(Restrictions.eq("bpafeeMap.feeSubType",FeeSubType.SANCTION_FEE));
		return feeCrit.list();
	}

	public List<BpaFeeCommon> findByName(final String name) {
		return bpaFeeCommonRepository.findByName(name.toUpperCase());
	}

	public List<BpaFeeCommon> findByCode(final String code) {
		return bpaFeeCommonRepository.findByCode(code.toUpperCase());
	}

}