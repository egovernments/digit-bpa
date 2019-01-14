/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2017>  eGovernments Foundation
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

import org.egov.bpa.master.entity.StakeHolder;
import org.egov.eis.entity.Assignment;
import org.egov.eis.service.AssignmentService;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.utils.StringUtils;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional(readOnly = true)
public class StakeHolderAuditService {

	private static final String USER = "user";
	private static final String DATE = "date";
	private static final String STATUS = "status";
	private static final String UPDATED_BY = "updatedBy";
	private static final String COMMENTS = "comments";
	private static final String DEPARTMENT = "department";

	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private AssignmentService assignmentService;

	private List<StakeHolder> getRevisionOfStakeholder(Long stakeholderId) {
		return AuditReaderFactory.get(entityManager)
								 .createQuery()
								 .forRevisionsOfEntity(StakeHolder.class, true, true)
								 .add(AuditEntity.id().eq(stakeholderId))
								 .getResultList();
	}


	public List<Map<String, Object>> getStakeholderUpdateHistory(final Long id) {
		final List<Map<String, Object>> historyTable = new ArrayList<>();
		List<StakeHolder> historyList = getRevisionOfStakeholder(id);
		historyList.forEach(stakeHolderRev -> {
			final Map<String, Object> historyMap = new ConcurrentHashMap<>(0);
			if (stakeHolderRev.getLastUpdatedDate() != null || stakeHolderRev.getLastUpdatedUser() != null) {
				historyMap.put(DATE, stakeHolderRev.getLastUpdatedDate());
				historyMap.put(COMMENTS, StringUtils.isBlank(stakeHolderRev.getComments()) ? "N/A" : stakeHolderRev.getComments());
				historyMap.put(UPDATED_BY, stakeHolderRev.getLastUpdatedUser().getName());
				historyMap.put(STATUS, stakeHolderRev.getStatus().getStakeHolderStatusVal());
				setEmployeeDetails(stakeHolderRev.getLastUpdatedUser(), historyMap);
				historyTable.add(historyMap);
			}
		});
		historyTable.sort(Comparator.comparing(history -> String.valueOf(history.get(DATE))));
		return historyTable;
	}

	private void setEmployeeDetails(User userObject, Map<String, Object> historyMap) {
		Assignment assignment = assignmentService.getPrimaryAssignmentForUser(userObject.getId());
		if (null == assignment) {
			historyMap.put(USER, userObject.getUsername() + "::" + userObject.getName());
			historyMap.put(DEPARTMENT, "N/A");
		} else {
			historyMap
					.put(USER, assignment.getEmployee().getUsername() == null ? "N/A"
							: assignment.getEmployee().getUsername() + "::" + assignment.getEmployee().getName());
			historyMap.put(DEPARTMENT, assignment.getDepartment() == null ? "N/A" : assignment.getDepartment().getName());
		}
	}
}
