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

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.egov.bpa.master.entity.ChecklistServiceTypeMapping;
import org.egov.bpa.master.repository.ChecklistServiceTypeMappingRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

@Service
@Transactional(readOnly = true)
public class ChecklistServicetypeMappingService {

    @PersistenceContext
    private EntityManager entityManager;

    public Session getCurrentSession() {
        return entityManager.unwrap(Session.class);
    }

    @Autowired
    private ChecklistServiceTypeMappingRepository mappingRepository;

    @Transactional
    public void save(List<ChecklistServiceTypeMapping> mappings) {
        mappingRepository.save(mappings);
    }

    public List<org.egov.bpa.master.entity.ChecklistServiceTypeMapping> searchChecklistServicetypeMapping(
            ChecklistServiceTypeMapping checklistServiceTypeMapping) {

        if (checklistServiceTypeMapping.getChecklistType() != null
                && checklistServiceTypeMapping.getServiceType() != null) {
            return mappingRepository.findByServiceTypeAndChecklistType(
                    checklistServiceTypeMapping.getServiceType().getDescription(),
                    checklistServiceTypeMapping.getChecklistType().getDescription());
        }
        if (checklistServiceTypeMapping.getChecklistType() != null)
            return mappingRepository.findByChecklist(checklistServiceTypeMapping.getChecklistType().getDescription());
        else if (checklistServiceTypeMapping.getServiceType() != null)
            return mappingRepository.findByServiceType(checklistServiceTypeMapping.getServiceType());

        return Collections.emptyList();
    }

    public List<ChecklistServiceTypeMapping> findByActiveChecklistAndServiceType(String serviceType, String checklistType) {
        return mappingRepository.findByActiveChecklistAndServiceType(checklistType, serviceType);
    }

    public List<ChecklistServiceTypeMapping> findByActiveByServiceTypeAndChecklist(Long serviceType, String checklistType) {
        return mappingRepository.findByActiveByServiceTypeAndChecklist(serviceType, checklistType);
    }

    public boolean validateChecklistServiceTypeAlreadyExist(String serviceType,String checklistType) {
        List<ChecklistServiceTypeMapping> list = mappingRepository.findByServiceTypeAndChecklistType(
        		serviceType,checklistType);
        return !list.isEmpty();
    }

    public ChecklistServiceTypeMapping load(Long id) {
        return mappingRepository.getOne(id);
    }
    
    public List<ChecklistServiceTypeMapping> findByChecklistTypeCode(String checklistTypeCode) {
        return mappingRepository.findByChecklistTypeCode(checklistTypeCode);
    }

}