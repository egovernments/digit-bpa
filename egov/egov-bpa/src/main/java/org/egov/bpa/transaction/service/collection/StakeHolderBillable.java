package org.egov.bpa.transaction.service.collection;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.egov.bpa.master.entity.StakeHolder;
import org.egov.bpa.utils.BpaConstants;
import org.egov.collection.constants.CollectionConstants;
import org.egov.demand.dao.EgBillDao;
import org.egov.demand.interfaces.Billable;
import org.egov.demand.model.AbstractBillable;
import org.egov.demand.model.EgBillType;
import org.egov.demand.model.EgDemand;
import org.egov.infra.admin.master.entity.AppConfigValues;
import org.egov.infra.admin.master.entity.Module;
import org.egov.infra.admin.master.service.AppConfigValueService;
import org.egov.infra.admin.master.service.ModuleService;
import org.egov.infra.persistence.entity.Address;
import org.egov.infra.persistence.entity.enums.AddressType;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StakeHolderBillable extends AbstractBillable implements Billable {

	private static final String DISPLAY_MESSAGE = "Registration Fee Collection";
	private StakeHolder stakeHolder;
	private Long userId;
	private Boolean isCallbackForApportion = Boolean.FALSE;
	private EgBillType billType;
	private String referenceNumber;
	private String transanctionReferenceNumber;

	@Autowired
	private AppConfigValueService appConfigValueService;

	@Autowired
	private EgBillDao egBillDAO;
	@Autowired
	private ModuleService moduleService;

	@Autowired
	private BpaDemandService bpaDemandService;

	@Override
	public String getBillPayee() {
		return stakeHolder.getName();
	}

	@Override
	public String getBillAddress() {
		List<Address> address = stakeHolder.getAddress();
		String permanentAddress = null;
		for (Address address2 : address) {
			if(address2.getType().equals(AddressType.PERMANENT)){
				permanentAddress = address2.toString();	
				break;
		}
		}
		return stakeHolder.getName() == null ? "Not Mentioned" : permanentAddress;
	}

	@Override
	public EgDemand getCurrentDemand() {
		return stakeHolder.getDemand();
	}

	@Override
	public String getEmailId() {
		return stakeHolder.getEmailId();
	}

	@Override
	public List<EgDemand> getAllDemands() {
		final List<EgDemand> demandList = new ArrayList<>();
		demandList.add(stakeHolder.getDemand());
		return demandList;
	}

	@Override
	public EgBillType getBillType() {
		return billType == null ? billType = egBillDAO.getBillTypeByCode("AUTO") : billType;
	}

	public void setBillType(final EgBillType billType) {
		this.billType = billType;
	}

	@Override
	public Date getBillLastDueDate() {
		return new DateTime().plusMonths(1).toDate();
	}

	@Override
	public Long getBoundaryNum() {
		/*return stakeHolder.getSiteDetail().get(0) != null
			   && stakeHolder.getSiteDetail().get(0).getAdminBoundary() == null ? 0l : stakeHolder.getSiteDetail().get(0)
																								  .getAdminBoundary().getBoundaryNum();*/
		return 0l;
	}

	@Override
	public String getBoundaryType() {
		return "Ward";
	}

	@Override
	public String getDepartmentCode() {
		List<AppConfigValues> appConfigValueList = appConfigValueService.getConfigValuesByModuleAndKey(
				BpaConstants.APPLICATION_MODULE_TYPE, BpaConstants.BPA_DEPARTMENT_CODE);
		return appConfigValueList.isEmpty() ? "" : appConfigValueList.get(0).getValue();
	}

	@Override
	public BigDecimal getFunctionaryCode() {
		List<AppConfigValues> appConfigValueList = appConfigValueService.getConfigValuesByModuleAndKey(
				BpaConstants.APPLICATION_MODULE_TYPE, BpaConstants.BPA_DEFAULT_FUNCTIONARY_CODE);
		return appConfigValueList.isEmpty() ? BigDecimal.ZERO : new BigDecimal(appConfigValueList.get(0).getValue());
	}

	@Override
	public String getFundCode() {
		List<AppConfigValues> appConfigValueList = appConfigValueService.getConfigValuesByModuleAndKey(
				BpaConstants.APPLICATION_MODULE_TYPE, BpaConstants.BPA_DEFAULT_FUND_CODE);
		return appConfigValueList.isEmpty() ? "" : appConfigValueList.get(0).getValue();
	}

	@Override
	public String getFundSourceCode() {
		List<AppConfigValues> appConfigValueList = appConfigValueService.getConfigValuesByModuleAndKey(
				BpaConstants.APPLICATION_MODULE_TYPE, BpaConstants.BPA_DEFAULT_FUND_SRC_CODE);
		return appConfigValueList.isEmpty() ? "" : appConfigValueList.get(0).getValue();
	}

	@Override
	public Date getIssueDate() {
		return new Date();
	}

	@Override
	public Date getLastDate() {
		return getBillLastDueDate();
	}

	@Override
	public Module getModule() {
		return moduleService.getModuleByName(BpaConstants.EGMODULE_NAME);
	}

	@Override
	public Boolean getOverrideAccountHeadsAllowed() {
		return false;
	}

	@Override
	public Boolean getPartPaymentAllowed() {
		return false;
	}

	@Override
	public String getServiceCode() {
		return "BPASH";
	}

	@Override
	public BigDecimal getTotalAmount() {
		final EgDemand currentDemand = getCurrentDemand();
		final List<Object> instVsAmt = bpaDemandService.getDmdCollAmtInstallmentWiseForRegFee(currentDemand);
		BigDecimal balance = BigDecimal.ZERO;
		for (final Object object : instVsAmt) {
			final Object[] ddObject = (Object[]) object;
			final BigDecimal dmdAmt = new BigDecimal((Double) ddObject[2]);
			BigDecimal collAmt = BigDecimal.ZERO;
			if (ddObject[2] != null)
				collAmt = new BigDecimal((Double) ddObject[3]);
			balance = balance.add(dmdAmt.subtract(collAmt));
		}
		return balance;
	}

	@Override
	public Long getUserId() {
		return userId;
	}

	public void setUserId(final Long userId) {
		this.userId = userId;
	}

	@Override
	public String getDescription() {
		return "Acknowledgement Number: " + getStakeHolder().getCode();
	}

	@Override
	public String getDisplayMessage() {
		return DISPLAY_MESSAGE;
	}

	@Override
	public String getCollModesNotAllowed() {
		return CollectionConstants.INSTRUMENTTYPE_BANK;
	}

	@Override
	public String getConsumerId() {
		return stakeHolder.getCode();
	}

	@Override
	public String getConsumerType() {
		//return stakeHolder.getServiceType().getCode();
		return "";
	}

	@Override
	public Boolean isCallbackForApportion() {
		return isCallbackForApportion;
	}

	@Override
	public void setCallbackForApportion(final Boolean b) {
		isCallbackForApportion = b;
	}

	public StakeHolder getStakeHolder() {
		return stakeHolder;
	}

	public void setStakeHolder(StakeHolder stakeHolder) {
		this.stakeHolder = stakeHolder;
	}

	@Override
	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(final String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	@Override
	public String getTransanctionReferenceNumber() {
		return transanctionReferenceNumber;
	}

	public void setTransanctionReferenceNumber(final String transanctionReferenceNumber) {
		this.transanctionReferenceNumber = transanctionReferenceNumber;
	}
}