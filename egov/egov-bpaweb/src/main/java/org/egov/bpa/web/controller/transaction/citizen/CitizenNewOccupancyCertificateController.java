/*
 * eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 * accountability and the service delivery of the government  organizations.
 *
 *  Copyright (C) <2018>  eGovernments Foundation
 *
 *  The updated version of eGov suite of products as by eGovernments Foundation
 *  is available at http://www.egovernments.org
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see http://www.gnu.org/licenses/ or
 *  http://www.gnu.org/licenses/gpl.html .
 *
 *  In addition to the terms of the GPL license to be adhered to in using this
 *  program, the following additional terms are to be complied with:
 *
 *      1) All versions of this program, verbatim or modified must carry this
 *         Legal Notice.
 *      Further, all user interfaces, including but not limited to citizen facing interfaces,
 *         Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
 *         derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *      For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *      For any further queries on attribution, including queries on brand guidelines,
 *         please contact contact@egovernments.org
 *
 *      2) Any misrepresentation of the origin of the material is prohibited. It
 *         is required that all modified versions of this material be marked in
 *         reasonable ways as different from the original version.
 *
 *      3) This license does not grant any rights to any user of the program
 *         with regards to rights under trademark law for use of the trade names
 *         or trademarks of eGovernments Foundation.
 *
 *  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */

package org.egov.bpa.web.controller.transaction.citizen;

import static org.egov.bpa.utils.BpaConstants.DISCLIMER_MESSAGE_ONSAVE;
import static org.egov.bpa.utils.BpaConstants.WF_LBE_SUBMIT_BUTTON;
import static org.egov.bpa.utils.BpaConstants.WF_NEW_STATE;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.egov.bpa.transaction.entity.ApplicationFloorDetail;
import org.egov.bpa.transaction.entity.BuildingDetail;
import org.egov.bpa.transaction.entity.WorkflowBean;
import org.egov.bpa.transaction.entity.oc.OCBuilding;
import org.egov.bpa.transaction.entity.oc.OCFloor;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.service.collection.GenericBillGeneratorService;
import org.egov.bpa.transaction.service.oc.OccupancyCertificateService;
import org.egov.bpa.web.controller.transaction.BpaGenericApplicationController;
import org.egov.commons.entity.Source;
import org.egov.eis.service.PositionMasterService;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.workflow.matrix.entity.WorkFlowMatrix;
import org.egov.pims.commons.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/application/citizen")
public class CitizenNewOccupancyCertificateController extends BpaGenericApplicationController {

    private static final String ONLINE_PAYMENT_ENABLE = "onlinePaymentEnable";

    private static final String WORK_FLOW_ACTION = "workFlowAction";

    private static final String TRUE = "TRUE";

    private static final String CITIZEN_OR_BUSINESS_USER = "citizenOrBusinessUser";

    private static final String IS_CITIZEN = "isCitizen";

    private static final String OFFICIAL_NOT_EXISTS = "No officials assigned to process this application.";

    private static final String MESSAGE = "message";

    private static final String BPAAPPLICATION_CITIZEN = "citizen_suceess";

    public static final String CITIZEN_OCCUPANCY_CERTIFICATE_NEW = "citizen-occupancy-certificate-new";

    @Autowired
    private GenericBillGeneratorService genericBillGeneratorService;
    @Autowired
    private PositionMasterService positionMasterService;
    @Autowired
    private OccupancyCertificateService occupancyCertificateService;


    @GetMapping("/occupancy-certificate/apply")
    public String newOCForm(final Model model, final HttpServletRequest request) {
        OccupancyCertificate occupancyCertificate = new OccupancyCertificate();
        occupancyCertificate.setApplicationDate(new Date());
        occupancyCertificate.setSource(Source.CITIZENPORTAL);
        occupancyCertificate.setApplicationType("Occupancy Certificate");
        model.addAttribute("mode", "new");
        model.addAttribute("loadingFloorDetailsFromEdcrRequire", true);
        setCityName(model, request);
        getDcrDocumentsUploadMode(model);
        prepareCommonModelAttribute(model, occupancyCertificate.isCitizenAccepted());
        model.addAttribute("occupancyCertificate", occupancyCertificate);
        return CITIZEN_OCCUPANCY_CERTIFICATE_NEW;
    }

    private void setCityName(final Model model, final HttpServletRequest request) {
        if (request.getSession().getAttribute("cityname") != null)
            model.addAttribute("cityName", request.getSession().getAttribute("cityname"));
    }

    @PostMapping("/occupancy-certificate/submit")
    public String submitOCDetails(@Valid @ModelAttribute final OccupancyCertificate occupancyCertificate,
            final HttpServletRequest request, final Model model,
            final BindingResult errors) {
        if (errors.hasErrors()) {
            return CITIZEN_OCCUPANCY_CERTIFICATE_NEW;
        }
        occupancyCertificateService.validateProposedAndExistingBuildings(occupancyCertificate);
		String result = validateOcWithBpaApplication(model, occupancyCertificate);
		if (!result.isEmpty())
			return result;
        WorkflowBean wfBean = new WorkflowBean();
        Long userPosition = null;
        String workFlowAction = request.getParameter(WORK_FLOW_ACTION);
        Boolean isCitizen = request.getParameter(IS_CITIZEN) != null
                && request.getParameter(IS_CITIZEN)
                        .equalsIgnoreCase(TRUE) ? Boolean.TRUE : Boolean.FALSE;
        Boolean citizenOrBusinessUser = request.getParameter(CITIZEN_OR_BUSINESS_USER) != null
                && request.getParameter(CITIZEN_OR_BUSINESS_USER)
                        .equalsIgnoreCase(TRUE) ? Boolean.TRUE : Boolean.FALSE;
        Boolean onlinePaymentEnable = request.getParameter(ONLINE_PAYMENT_ENABLE) != null
                && request.getParameter(ONLINE_PAYMENT_ENABLE)
                        .equalsIgnoreCase(TRUE) ? Boolean.TRUE : Boolean.FALSE;
        final WorkFlowMatrix wfMatrix = bpaUtils.getWfMatrixByCurrentState(occupancyCertificate.getStateType(), WF_NEW_STATE);
        if (wfMatrix != null)
            userPosition = bpaUtils.getUserPositionIdByZone(wfMatrix.getNextDesignation(),
                    occupancyCertificate.getParent().getSiteDetail().get(0) != null
                            && occupancyCertificate.getParent().getSiteDetail().get(0).getElectionBoundary() != null
                                    ? occupancyCertificate.getParent().getSiteDetail().get(0).getElectionBoundary().getId()
                                    : null);
		if (citizenOrBusinessUser && workFlowAction != null && workFlowAction.equals(WF_LBE_SUBMIT_BUTTON)
				&& (userPosition == 0 || userPosition == null)) {
			model.addAttribute("noJAORSAMessage", OFFICIAL_NOT_EXISTS);
			return CITIZEN_OCCUPANCY_CERTIFICATE_NEW;
		}

        wfBean.setWorkFlowAction(request.getParameter(WORK_FLOW_ACTION));
        OccupancyCertificate ocResponse = occupancyCertificateService.saveOrUpdate(occupancyCertificate, wfBean);
        if (citizenOrBusinessUser) {
            if (isCitizen)
                bpaUtils.createPortalUserinbox(ocResponse, Arrays.asList(ocResponse.getParent().getOwner().getUser(),
                        ocResponse.getParent().getStakeHolder().get(0).getStakeHolder()), workFlowAction);
            else
				bpaUtils.createPortalUserinbox(ocResponse,
						Arrays.asList(ocResponse.getParent().getOwner().getUser(), securityUtils.getCurrentUser()),
                        workFlowAction);
        }
		if (workFlowAction != null && workFlowAction.equals(WF_LBE_SUBMIT_BUTTON)
                && !bpaUtils.logedInuserIsCitizen()) {
            Position pos = positionMasterService.getPositionById(ocResponse.getCurrentState().getOwnerPosition().getId());
            User wfUser = workflowHistoryService.getUserPositionByPassingPosition(pos.getId());
			String message = messageSource.getMessage("msg.portal.forward.registration",
					new String[] {
							wfUser == null ? ""
									: wfUser.getUsername().concat("~").concat(getDesinationNameByPosition(pos)),
							ocResponse.getApplicationNumber() },
					LocaleContextHolder.getLocale());

            message = message.concat(DISCLIMER_MESSAGE_ONSAVE);
            model.addAttribute(MESSAGE, message);
        } else {
            model.addAttribute(MESSAGE,
                    "Successfully saved with ApplicationNumber " + ocResponse.getApplicationNumber() + ".");
        }
        
      /* Applicant applicant= occupancyCertificate.getParent().getOwner();
			bpaSmsAndEmailService.buildSmsAndEmailForOCNewAppln(occupancyCertificate,
					applicant.getName(), applicant.getUser().getMobileNumber(),
					applicant.getUser().getEmailId(), applicant.getUser().getUsername(),
					applicant.getUser().getPassword(), isCitizen);*/
			
        if (workFlowAction != null
                && workFlowAction
                        .equals(WF_LBE_SUBMIT_BUTTON)
                && onlinePaymentEnable && bpaUtils.checkAnyTaxIsPendingToCollect(occupancyCertificate.getDemand())) {
            return genericBillGeneratorService
                    .generateBillAndRedirectToCollection(occupancyCertificate, model);
        }
        return BPAAPPLICATION_CITIZEN;
    }

    /**
     * 
     * @param model
     * @param occupancyCertificate
     * @return
     */
	private String validateOcWithBpaApplication(final Model model, final OccupancyCertificate occupancyCertificate) {
		List<OCBuilding> ocBuildings = occupancyCertificate.getBuildings();
		List<BuildingDetail> bpaBuildingDetail = occupancyCertificate.getParent().getBuildingDetail();
		// 1.check number of blocks,floors same or not
		if (ocBuildings.size() > bpaBuildingDetail.size()) {
			model.addAttribute("OcComparisonValidation",
					"For Occupancy Certificate submitted plan buildings more than permit application buildings.Not eligible to apply occupancy certificate. ");
			return CITIZEN_OCCUPANCY_CERTIFICATE_NEW;
		}
		for (OCBuilding oc : ocBuildings) {
			boolean buildingExist = false;
			for (BuildingDetail bpa : bpaBuildingDetail) {
				if (oc.getBuildingNumber() == bpa.getNumber())
					buildingExist = true;
			}
			if (!buildingExist) {
				model.addAttribute("OcComparisonValidation",
						"For Occupancy Certificate submitted plan building " + oc.getBuildingNumber()
								+ "details and permit application building "
								+ "details are not matching.Not eligible to apply occupancy certificate.");
				return CITIZEN_OCCUPANCY_CERTIFICATE_NEW;
			}
		}
		// 2.check the floor count same or not
		for (OCBuilding oc : ocBuildings) {
			int size = oc.getFloorDetails().size();
			for (BuildingDetail bpa : bpaBuildingDetail) {
				if (oc.getBuildingNumber() == bpa.getNumber()) {
					if (size > bpa.getApplicationFloorDetails().size()) {
						model.addAttribute("OcComparisonValidation",
								"For Occupancy Certificate submitted plan building " + oc.getBuildingNumber()
										+ ", floor count " + size + " and permitted application building "
										+ oc.getBuildingNumber() + ",floor count  "
										+ bpa.getApplicationFloorDetails().size()
										+ " not matching. Not eligible to apply occupancy certificate.");
						return CITIZEN_OCCUPANCY_CERTIFICATE_NEW;
					}
				}
			}
		}
		// 3. check floor wise floor area same or not
		BigDecimal hundred = new BigDecimal(100);
		BigDecimal percent = new BigDecimal(bpaUtils.getAppConfigForOcAllowDeviation());
		for (OCBuilding oc : ocBuildings) {
			for (BuildingDetail bpa : bpaBuildingDetail) {
				if (oc.getBuildingNumber() == bpa.getNumber()) {
					for (OCFloor ocFloor : oc.getFloorDetails()) {
						int floorNumber = ocFloor.getFloorNumber();
						for (ApplicationFloorDetail bpaFloor : bpa.getApplicationFloorDetails()) {
							if (floorNumber == bpaFloor.getFloorNumber()) {
								BigDecimal allowDeviation = bpaFloor.getFloorArea().multiply(percent).divide(hundred);
								BigDecimal total = bpaFloor.getFloorArea().add(allowDeviation);
								if (ocFloor.getFloorArea().compareTo(total) > 0) {
									model.addAttribute("OcComparisonValidation",
											"For Occupancy Certificate submitted plan building floor area constructed more than permitted building floor area for Block - "
													+ oc.getBuildingNumber() + "for floor - " + floorNumber
													+ ".Not eligible to apply occupancy certificate since it's crossing 5% deviation .");
									return CITIZEN_OCCUPANCY_CERTIFICATE_NEW;
								}
							}
						}
					}
				}
			}
		} 
		// check floor wise occupancy
		for (OCBuilding oc : ocBuildings) {
			for (BuildingDetail bpa : bpaBuildingDetail) {
				if (oc.getBuildingNumber() == bpa.getNumber()) {
					for (OCFloor ocFloor : oc.getFloorDetails()) {
						int floorNumber = ocFloor.getFloorNumber();
						for (ApplicationFloorDetail bpaFloor : bpa.getApplicationFloorDetails()) {
							if (floorNumber == bpaFloor.getFloorNumber()) {
								if (!ocFloor.getOccupancy().getCode().equals(bpaFloor.getOccupancy().getCode())) {
									model.addAttribute("OcComparisonValidation",
											"For Occupancy Certificate submitted plan building floor occupancy and permitted building floor occupancy "
													+ " not matching for Block - " + oc.getBuildingNumber()
													+ " and floor - " + floorNumber
													+ ".Not eligible to apply occupancy certificate.");
									return CITIZEN_OCCUPANCY_CERTIFICATE_NEW;
								}
							}
						}
					}
				}
			}
		}
		// 4.check block wise floor area
		BigDecimal limitSqurMtrs = new BigDecimal(40);
		for (OCBuilding oc : ocBuildings) {
			for (BuildingDetail bpa : bpaBuildingDetail) {
				if (oc.getBuildingNumber() == bpa.getNumber()) {
					BigDecimal totalOcFloor = getOcTotalFloorArea(oc.getFloorDetails());
					BigDecimal totalBpaFloor = getBpaTotalFloorArea(bpa.getApplicationFloorDetails());
					BigDecimal allowDeviation = totalBpaFloor.multiply(percent).divide(hundred);
					BigDecimal totalBpaWithAllowDeviation = totalBpaFloor.add(allowDeviation);
					if (totalBpaWithAllowDeviation.compareTo(totalOcFloor) < 0) {
						model.addAttribute("OcComparisonValidation",
								"For Occupancy Certificate submitted plan building total floor area constructed more than permitted building total floor area for Block "
										+ oc.getBuildingNumber()
										+ ".Not eligible to apply occupancy certificate since it's crossing 5% deviation .");
						return CITIZEN_OCCUPANCY_CERTIFICATE_NEW;
					}
					if (totalOcFloor.subtract(totalBpaFloor).compareTo(limitSqurMtrs) > 0) {
						model.addAttribute("OcComparisonValidation",
								"For Occupancy Certificate submitted plan building total floor area constructed more than permitted building total floor area for Block "
										+ oc.getBuildingNumber()
										+ ".Not eligible to apply occupancy certificate since it's crossing limit (40 sqmtrs) .");
						return CITIZEN_OCCUPANCY_CERTIFICATE_NEW;
					}
				}
			}
		}
		return "";
	}
	
	private BigDecimal getOcTotalFloorArea(List<OCFloor> floorList){
		BigDecimal totalFloorArea = BigDecimal.ZERO;
		for(OCFloor floorDetail : floorList)
			totalFloorArea = totalFloorArea.add(floorDetail.getFloorArea());
		return totalFloorArea;
	}
	
	private BigDecimal getBpaTotalFloorArea(List<ApplicationFloorDetail> floorList){
		BigDecimal totalFloorArea = BigDecimal.ZERO;
		for(ApplicationFloorDetail floorDetail : floorList)
			totalFloorArea = totalFloorArea.add(floorDetail.getFloorArea());
		return totalFloorArea;
	}
}