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
package org.egov.bpa.web.controller.ajax;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;
import org.egov.bpa.master.entity.BpaScheme;
import org.egov.bpa.master.entity.BpaSchemeLandUsage;
import org.egov.bpa.master.entity.CheckListDetail;
import org.egov.bpa.master.entity.PostalAddress;
import org.egov.bpa.master.entity.RegistrarOfficeVillage;
import org.egov.bpa.master.entity.SlotMapping;
import org.egov.bpa.master.entity.StakeHolder;
import org.egov.bpa.master.entity.enums.SlotMappingApplType;
import org.egov.bpa.master.service.BpaSchemeService;
import org.egov.bpa.master.service.CheckListDetailService;
import org.egov.bpa.master.service.PostalAddressService;
import org.egov.bpa.master.service.RegistrarOfficeVillageService;
import org.egov.bpa.master.service.SlotMappingService;
import org.egov.bpa.master.service.StakeHolderService;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.enums.StakeHolderType;
import org.egov.bpa.transaction.service.ApplicationBpaService;
import org.egov.bpa.transaction.service.BpaApplicationValidationService;
import org.egov.bpa.transaction.service.WorkflowHistoryService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.common.entity.Occupancy;
import org.egov.common.entity.SubOccupancy;
import org.egov.commons.service.OccupancyService;
import org.egov.eis.entity.Assignment;
import org.egov.eis.entity.AssignmentAdaptor;
import org.egov.eis.service.AssignmentService;
import org.egov.eis.service.DesignationService;
import org.egov.infra.admin.master.entity.Boundary;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.admin.master.service.BoundaryService;
import org.egov.infra.admin.master.service.CrossHierarchyService;
import org.egov.infra.admin.master.service.UserService;
import org.egov.infra.persistence.entity.enums.UserType;
import org.egov.infra.workflow.matrix.service.CustomizedWorkFlowService;
import org.egov.pims.commons.Designation;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BpaAjaxController {

    private static final String BLOCK_NAME = "blockName";

    private static final String BLOCK_ID = "blockId";

    @Autowired
    private DesignationService designationService;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private CustomizedWorkFlowService customizedWorkFlowService;

    @Autowired
    private ApplicationBpaService applicationBpaService;
    @Autowired
    private StakeHolderService stakeHolderService;
    @Autowired
    private OccupancyService occupancyService;
    @Autowired
    private UserService userService;
    @Autowired
    private PostalAddressService postalAddressService;
    @Autowired
    private CrossHierarchyService crossHierarchyService;
    @Autowired
    private BpaSchemeService bpaSchemeService;
    @Autowired
    private RegistrarOfficeVillageService registrarOfficeService;
    @Autowired
    private BoundaryService boundaryService;
    @Autowired
    private SlotMappingService slotMappingService;
    @Autowired
    private WorkflowHistoryService workflowHistoryService;
    @Autowired
    private BpaApplicationValidationService bpaApplicationValidationService;

    @Autowired
    protected CheckListDetailService checkListDetailService;


    @RequestMapping(value = "/ajax/getAdmissionFees", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public BigDecimal isConnectionPresentForProperty(@RequestParam final Long[] serviceTypeIds) {
        if (serviceTypeIds.length > 0) {
            return applicationBpaService.getTotalFeeAmountByPassingServiceTypeAndAmenities(Arrays.asList(serviceTypeIds));
        } else {
            return BigDecimal.ZERO;
        }
    }

    @RequestMapping(value = "/bpaajaxWorkFlow-getDesignationsByObjectTypeAndDesignation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Designation> getDesignationsByObjectTypeAndDesignation(
            @ModelAttribute("designations") @RequestParam final String departmentRule,
            @RequestParam final String currentState, @RequestParam final String type,
            @RequestParam final BigDecimal amountRule, @RequestParam final String additionalRule,
            @RequestParam final String pendingAction, @RequestParam final Long approvalDepartment) {
        List<Designation> designationList = designationService
                .getDesignationsByNames(customizedWorkFlowService.getNextDesignations(type,
                        departmentRule, amountRule, additionalRule, currentState,
                        pendingAction, new Date()));
        if (designationList.isEmpty())
            designationList = designationService.getAllDesignationByDepartment(approvalDepartment, new Date());
        return designationList;
    }

    @RequestMapping(value = "/ajax-designationsByDepartment", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Designation> getDesignationsByDepartmentId(
            @ModelAttribute("designations") @RequestParam final Long approvalDepartment) {
        List<Designation> designations = new ArrayList<>();
        if (approvalDepartment != null && approvalDepartment != 0 && approvalDepartment != -1)
            designations = designationService.getAllDesignationByDepartment(approvalDepartment, new Date());
        designations.forEach(designation -> designation.toString());
        return designations;
    }

    @RequestMapping(value = "/bpaajaxWorkFlow-positionsByDepartmentAndDesignationAndBoundary", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String getPositionByDepartmentAndDesignationAndBoundary(@RequestParam final Long approvalDepartment,
            @RequestParam final Long approvalDesignation, @RequestParam final Long boundaryId,
            final HttpServletResponse response) {
        if (approvalDepartment != null && approvalDepartment != 0 && approvalDepartment != -1
                && approvalDesignation != null && approvalDesignation != 0 && approvalDesignation != -1) {
            List<Assignment> assignmentList = assignmentService
                    .findAssignmentByDepartmentDesignationAndBoundary(approvalDepartment, approvalDesignation, boundaryId);
            final Gson jsonCreator = new GsonBuilder().registerTypeAdapter(Assignment.class, new AssignmentAdaptor())
                    .create();
            return jsonCreator.toJson(assignmentList, new TypeToken<Collection<Assignment>>() {
            }.getType());
        }
        return "[]";
    }

    @RequestMapping(value = "/ajax/stakeholdersbytype", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<StakeHolder> getStakeHolderByType(@RequestParam final String name,
            @RequestParam final StakeHolderType stakeHolderType) {
        return stakeHolderService.getStakeHolderListByType(stakeHolderType, name);
    }

    @RequestMapping(value = "/application/getoccupancydetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Occupancy> getOccupancyDetails() {
        return occupancyService.findAll();
    }

    @RequestMapping(value = "/getApplicantDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> getApplicantDetailsForMobileNumber(@RequestParam final String mobileNumber) {
        Map<String, String> user = new HashMap<>();
        List<User> userList = userService.getUserByMobileNumberAndType(mobileNumber, UserType.CITIZEN);
        if (!userList.isEmpty()) {
            User dbUser = userList.get(0);
            user.put("name", dbUser.getName());
            user.put("address", dbUser.getAddress().get(0).getStreetRoadLine());
            user.put("emailId", dbUser.getEmailId());
            user.put("gender", dbUser.getGender().name());
            user.put("id", dbUser.getId().toString());

        }
        return user;
    }

    @RequestMapping(value = "/getApplicantDetailsForEmailId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public User getApplicantDetailsForEmailId(@RequestParam final String emailId) {
        return userService.getUserByUsername(emailId);
    }

    @RequestMapping(value = "/ajax/postaladdress", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<PostalAddress> getPostalAddress(@RequestParam final String pincode) {
        return postalAddressService.getPostalAddressList(pincode);
    }

    @RequestMapping(value = "/ajax/getpostaladdressbyid", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public PostalAddress getPostalAddressObj(@RequestParam final Long id) {
        return postalAddressService.findById(id);
    }

    @RequestMapping(value = {
            "/boundary/ajaxBoundary-localityByWard" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void localityByWard(@RequestParam Long wardId, HttpServletResponse response) throws IOException {

        final List<Boundary> blocks = crossHierarchyService
                .findChildBoundariesByParentBoundaryIdParentBoundaryTypeAndChildBoundaryType(BpaConstants.WARD,
                        BpaConstants.REVENUE_HIERARCHY_TYPE,
                        BpaConstants.LOCALITY_BNDRY_TYPE,
                        wardId);
        final List<JSONObject> jsonObjects = new ArrayList<>();
        for (final Boundary block : blocks) {
            final JSONObject jsonObj = new JSONObject();
            jsonObj.put("localityId", block.getId());
            jsonObj.put("localityName", block.getName());
            jsonObjects.add(jsonObj);
        }
        IOUtils.write(jsonObjects.toString(), response.getWriter());
    }

    @RequestMapping(value = {
            "/boundary/ajaxBoundary-electionwardbyrevenueward" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void electionWardByRevenueWard(@RequestParam Long wardId, HttpServletResponse response) throws IOException {

        final List<Boundary> blocks = crossHierarchyService
                .findChildBoundariesByParentBoundaryIdParentBoundaryTypeAndChildBoundaryType(BpaConstants.WARD,
                        BpaConstants.REVENUE_HIERARCHY_TYPE,
                        BpaConstants.WARD,
                        wardId);
        sortBoundaryByBndryNumberAsc(blocks);
        final List<JSONObject> jsonObjects = new ArrayList<>();
        for (final Boundary block : blocks) {
            final JSONObject jsonObj = new JSONObject();
            jsonObj.put("electionwardId", block.getId());
            jsonObj.put("electionwardName", block.getName());
            jsonObjects.add(jsonObj);
        }
        IOUtils.write(jsonObjects.toString(), response.getWriter());
    }

    @RequestMapping(value = {
            "/ajax/getlandusagebyscheme" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void landUsageByScheme(@RequestParam Long schemeId, HttpServletResponse response) throws IOException {

        if (schemeId != null) {
            final BpaScheme scheme = bpaSchemeService
                    .findById(schemeId);

            final List<JSONObject> jsonObjects = new ArrayList<>();
            if (scheme != null) {
                for (final BpaSchemeLandUsage landUsage : scheme.getSchemeLandUsage()) {
                    final JSONObject jsonObj = new JSONObject();
                    jsonObj.put("usageId", landUsage.getId());
                    jsonObj.put("usageDesc", landUsage.getDescription());
                    jsonObjects.add(jsonObj);
                }
            }
            IOUtils.write(jsonObjects.toString(), response.getWriter());
        }
    }

    @RequestMapping(value = { "/ajax/registraroffice" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public void registrarOfficeVillageMapping(@RequestParam Long villageId, HttpServletResponse response) throws IOException {

        if (villageId != null) {
            final List<RegistrarOfficeVillage> registrarOfficeList = registrarOfficeService
                    .getRegistrarOfficeByVillage(villageId);

            final List<JSONObject> jsonObjects = new ArrayList<>();
            if (!registrarOfficeList.isEmpty()) {
                for (final RegistrarOfficeVillage office : registrarOfficeList) {
                    final JSONObject jsonObj = new JSONObject();
                    jsonObj.put("registrarVillageId", office.getId());
                    jsonObj.put("registrarOfficeName", office.getRegistrarOffice().getName());
                    jsonObjects.add(jsonObj);
                }
            }
            IOUtils.write(jsonObjects.toString(), response.getWriter());
        }
    }

    @RequestMapping(value = { "/boundary/ajaxBoundary-blockByWard" }, method = RequestMethod.GET)
    public void blockByWard(@RequestParam Long wardId, HttpServletResponse response) throws IOException {
        List<Boundary> revenueWards = boundaryService.getActiveChildBoundariesByBoundaryId(wardId);
        sortBoundaryByBndryNumberAsc(revenueWards);
        final List<JsonObject> jsonObjects = new ArrayList<>();
        revenueWards.stream().forEach(block -> {
            final JsonObject jsonObj = new JsonObject();
            jsonObj.addProperty(BLOCK_ID, block.getId());
            jsonObj.addProperty(BLOCK_NAME, block.getName());
            jsonObjects.add(jsonObj);
        });
        IOUtils.write(jsonObjects.toString(), response.getWriter());
    }

    private void sortBoundaryByBndryNumberAsc(List<Boundary> boundaries) {
        boundaries.sort(Comparator.comparing(Boundary::getBoundaryNum));
    }
    
    @RequestMapping(value = "/ajax/getOneDayPermitSlotByBoundary", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Boolean getOneDayPermitSlotByBoundary(@RequestParam Long zoneId, @RequestParam Long electionWardId) {
    	SlotMapping slotMapping = new SlotMapping();
    	Boundary zone = boundaryService.getBoundaryById(zoneId);
    	Boundary electionWard = boundaryService.getBoundaryById(electionWardId);
    	slotMapping.setZone(zone);
    	slotMapping.setElectionWard(electionWard);
    	slotMapping.setApplType(SlotMappingApplType.ONE_DAY_PERMIT);
        List<SlotMapping> slotMappings = slotMappingService.searchSlotMapping(slotMapping);
        if(!slotMappings.isEmpty() && slotMappings.size()>0)
        	return true;
        else
        	return false;
    }

    @RequestMapping(value = "/validate/edcr-usedinbpa", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> validateEdcrIsUsedInBpaApplication(@RequestParam final String eDcrNumber) {
        return workflowHistoryService.checkIsEdcrUsedInBpaApplication(eDcrNumber);
    }

    @RequestMapping(value = "/validate/emailandmobile", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Boolean validateEmailAndMobileNumber(@RequestParam final String inputType, @RequestParam final String inputValue) {
        if ("email".equalsIgnoreCase(inputType) && stakeHolderService.validateStakeHolderIsRejected("",inputValue,"","", "") == null) {
            return userService.getUserByEmailId(inputValue) == null ? false : true;
        } else if ("mobile".equalsIgnoreCase(inputType) && stakeHolderService.validateStakeHolderIsRejected(inputValue,"","","", "") == null) {
            return userService.getUserByMobileNumberAndType(inputValue, UserType.BUSINESS).isEmpty() ? false : true;
        } else if ("aadhaar".equalsIgnoreCase(inputType) && stakeHolderService.validateStakeHolderIsRejected("","",inputValue,"", "") == null) {
            return userService.getUserByAadhaarNumber(inputValue) == null ? false : true;
        } else if ("pan".equalsIgnoreCase(inputType) && stakeHolderService.validateStakeHolderIsRejected("","","",inputValue, "") == null) {
            return userService.getUserByPan(inputValue) == null ? false : true;
        } else if ("licenseNo".equalsIgnoreCase(inputType) && stakeHolderService.validateStakeHolderIsRejected("","","", "", inputValue) == null) {
            return stakeHolderService.findByLicenseNumber(inputValue) == null ? false : true;
        }
        return false;
    }

    @RequestMapping(value = "/application/edcr-require", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Boolean checkEDCRIsRequire(@RequestParam final String serviceType, @RequestParam final String occupancy) {
        return bpaApplicationValidationService.isEdcrInetgrationRequired(serviceType, occupancy);
    }

    @RequestMapping(value = "/occupancy/sub-usages", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<SubOccupancy> getSubUsagesByOccupancy(@RequestParam String occupancy) {
        return occupancyService.findSubUsagesByOccupancy(occupancy);
    }

    @RequestMapping(value = "/application/findby-permit-number", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void getApplicationDetailsByPlanPermissionNumber(@RequestParam final String permitNumber, final HttpServletResponse response) throws IOException {
        BpaApplication application = applicationBpaService.findByPermitNumber(permitNumber);
        final JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("id", application.getId());
        jsonObj.addProperty("occupancy", application.getOccupancy().getDescription());
        jsonObj.addProperty("serviceTypeId",application.getServiceType().getId());
        jsonObj.addProperty("serviceTypeDesc",application.getServiceType().getDescription());
        jsonObj.addProperty("applicationType", application.getIsOneDayPermitApplication() ? "YES" : "NO");
        jsonObj.addProperty("dcrNumber", application.geteDcrNumber());
        jsonObj.addProperty("applicantName", application.getOwner().getName());
        jsonObj.addProperty("applicationNumber", application.getApplicationNumber());
        jsonObj.addProperty("planPermissionNumber", application.getPlanPermissionNumber());
        IOUtils.write(jsonObj.toString(), response.getWriter());
    }

    @RequestMapping(value = "/application/getdocumentlistbyservicetype", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<CheckListDetail> getDocumentsByServiceType(@RequestParam final Long serviceType, @RequestParam final String checklistType) {
        return checkListDetailService.findActiveCheckListByServiceType(serviceType, checklistType);
    }
    
    @RequestMapping(value = "/validate/edcr-expiry", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> validateEdcrExpiry(@RequestParam final String eDcrNumber) {
        return applicationBpaService.checkEdcrExpiry(eDcrNumber);
    }
}
