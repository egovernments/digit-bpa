/* eGov suite of products aim to improve the internal efficiency,transparency,
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
package org.egov.bpa.transaction.service.oc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.egov.bpa.autonumber.NocNumberGenerator;
import org.egov.bpa.master.entity.Holiday;
import org.egov.bpa.master.entity.NocConfiguration;
import org.egov.bpa.master.service.HolidayListService;
import org.egov.bpa.master.service.NocConfigurationService;
import org.egov.bpa.transaction.entity.BpaNocApplication;
import org.egov.bpa.transaction.entity.BpaStatus;
import org.egov.bpa.transaction.entity.enums.NocIntegrationInitiationEnum;
import org.egov.bpa.transaction.entity.enums.NocIntegrationTypeEnum;
import org.egov.bpa.transaction.entity.oc.OCNocDocuments;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.entity.oc.OccupancyNocApplication;
import org.egov.bpa.transaction.repository.oc.OccupancyCertificateNocRepository;
import org.egov.bpa.transaction.service.BpaStatusService;
import org.egov.bpa.transaction.service.DcrRestService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.BpaUtils;
import org.egov.common.entity.dcr.helper.EdcrApplicationInfo;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.admin.master.service.UserService;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.persistence.entity.enums.UserType;
import org.egov.infra.utils.ApplicationConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@Transactional(readOnly = true)
public class OccupancyCertificateNocService {
    @Autowired
    private OccupancyCertificateNocRepository ocNocRepository;
    @Autowired
    private BpaStatusService statusService;
    @Autowired
    private BpaUtils bpaUtils;
    @Autowired
    private NocConfigurationService nocConfigurationService;
    @Autowired
    private UserService userService;
    @Autowired
    private NocNumberGenerator nocNumberGenerator;
    @Autowired
    public HolidayListService holidayListService;
    @Autowired
    private DcrRestService drcRestService;

    @Transactional
    public OccupancyNocApplication save(final OccupancyNocApplication ocNoc) {
        return ocNocRepository.save(ocNoc);
    }

    @Transactional
    public List<OccupancyNocApplication> save(final List<OccupancyNocApplication> ocNoc) {
        return ocNocRepository.save(ocNoc);
    }

    public OccupancyNocApplication findByNocApplicationNumber(String appNo) {
        return ocNocRepository.findByNocApplicationNumber(appNo);
    }

    public List<OccupancyNocApplication> findByOCApplicationNumber(String appNo) {
        return ocNocRepository.findByOCApplicationNumber(appNo);
    }

    public List<OccupancyNocApplication> findInitiatedAppByType(final String nocType) {
        return ocNocRepository.findInitiatedAppByType(nocType);
    }

    public OccupancyNocApplication findByApplicationNumberAndType(String appNo, final String nocType) {
        return ocNocRepository.findByApplicationNumberAndType(appNo, nocType);
    }

    public OccupancyNocApplication createNocApplication(OccupancyNocApplication ocNoc, NocConfiguration nocConfig) {
        BpaStatus status = statusService.findByModuleTypeAndCode(BpaConstants.CHECKLIST_TYPE_NOC, BpaConstants.NOC_INITIATED);
        StringBuffer nocCode = new StringBuffer();
        nocCode.append("OC").append(nocConfig.getDepartment());
        ocNoc.getBpaNocApplication().setNocApplicationNumber(nocNumberGenerator.generateNocNumber(nocCode.toString()));
        ocNoc.getBpaNocApplication().setNocType(nocConfig.getDepartment());
        ocNoc.getBpaNocApplication().setStatus(status);
        addSlaEndDate(ocNoc.getBpaNocApplication(), nocConfig);
        return ocNocRepository.save(ocNoc);
    }

    public void initiateNoc(OccupancyCertificate oc) {
        Map<String, String> edcrNocMandatory = getEdcrNocMandatory(oc.geteDcrNumber());
        for (OCNocDocuments nocDocument : oc.getNocDocuments()) {
            OccupancyNocApplication ocNoc = new OccupancyNocApplication();
            BpaNocApplication nocApplication = new BpaNocApplication();

            List<User> nocUser = new ArrayList<>();
            List<User> userList = new ArrayList<>();
            NocConfiguration nocConfig = nocConfigurationService
                    .findByDepartmentAndType(nocDocument.getNocDocument().getServiceChecklist().getChecklist().getCode(),
                            BpaConstants.OC);
            if (nocConfig != null && nocConfig.getApplicationType().trim().equalsIgnoreCase(BpaConstants.OC)
                    && nocConfig.getIntegrationType().equalsIgnoreCase(NocIntegrationTypeEnum.INTERNAL.toString())
                    && nocConfig.getIntegrationInitiation().equalsIgnoreCase(NocIntegrationInitiationEnum.AUTO.toString())
                    && edcrNocMandatory.get(nocConfig.getDepartment()).equalsIgnoreCase("YES")) {
                List<User> nocUsers = new ArrayList<User>(
                        userService.getUsersByTypeAndTenantId(UserType.BUSINESS, ApplicationThreadLocals.getTenantID()));
                userList = nocUsers.stream()
                        .filter(usr -> usr.getRoles().stream()
                                .anyMatch(usrrl -> usrrl.getName()
                                        .equals(BpaConstants.getNocRole().get(nocConfig.getDepartment()))))
                        .collect(Collectors.toList());
                if (userList.isEmpty()) {
                    nocUsers = userService.getUsersByTypeAndTenantId(UserType.BUSINESS, ApplicationConstant.STATE_TENANTID);
                    userList = nocUsers.stream()
                            .filter(usr -> usr.getRoles().stream()
                                    .anyMatch(usrrl -> usrrl.getName()
                                            .equals(BpaConstants.getNocRole().get(nocConfig.getDepartment()))))
                            .collect(Collectors.toList());
                }
                nocUser.add(userList.get(0));
                ocNoc.setOc(oc);
                ocNoc.setBpaNocApplication(nocApplication);
                ocNoc = createNocApplication(ocNoc, nocConfig);
                bpaUtils.createOCNocPortalUserinbox(ocNoc, nocUser, ocNoc.getBpaNocApplication().getStatus().getCode());
            }
        }
    }

    public OccupancyNocApplication createNoc(OccupancyCertificate oc, String nocType) {
        OccupancyNocApplication ocNoc = new OccupancyNocApplication();
        BpaNocApplication nocApplication = new BpaNocApplication();
        List<User> nocUser = new ArrayList<>();
        List<User> userList = new ArrayList<>();
        NocConfiguration nocConfig = nocConfigurationService
                .findByDepartmentAndType(nocType, BpaConstants.OC);
        if (nocConfig.getApplicationType().trim().equalsIgnoreCase(BpaConstants.OC)
                && nocConfig.getIntegrationType().equalsIgnoreCase(NocIntegrationTypeEnum.INTERNAL.toString())
                && nocConfig.getIntegrationInitiation().equalsIgnoreCase(NocIntegrationInitiationEnum.MANUAL.toString())) {
            List<User> nocUsers = new ArrayList<User>(
                    userService.getUsersByTypeAndTenantId(UserType.BUSINESS, ApplicationThreadLocals.getTenantID()));
            userList = nocUsers.stream()
                    .filter(usr -> usr.getRoles().stream()
                            .anyMatch(usrrl -> usrrl.getName().equals(BpaConstants.getNocRole().get(nocConfig.getDepartment()))))
                    .collect(Collectors.toList());
            if (userList.isEmpty()) {
                nocUsers = userService.getUsersByTypeAndTenantId(UserType.BUSINESS, ApplicationConstant.STATE_TENANTID);
                userList = nocUsers.stream()
                        .filter(usr -> usr.getRoles().stream()
                                .anyMatch(usrrl -> usrrl.getName()
                                        .equals(BpaConstants.getNocRole().get(nocConfig.getDepartment()))))
                        .collect(Collectors.toList());
            }
            nocUser.add(userList.get(0));
            ocNoc.setOc(oc);
            ocNoc.setBpaNocApplication(nocApplication);
            ocNoc = createNocApplication(ocNoc, nocConfig);

            ocNoc.getBpaNocApplication().setOwnerUser(nocUser.get(0));

            bpaUtils.createOCNocPortalUserinbox(ocNoc, nocUser, ocNoc.getBpaNocApplication().getStatus().getCode());
        }
        return ocNoc;
    }

    public void addSlaEndDate(BpaNocApplication nocApplication, NocConfiguration nocConfig) {

        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // todays date.
        c.add(Calendar.DATE, Integer.parseInt(nocConfig.getSla().toString()));

        List<Holiday> holiday = holidayListService.findByFromAndToDate(new Date(), c.getTime());
        c.add(Calendar.DATE, holiday.size());

        nocApplication.setSlaEndDate(c.getTime());
    }

    public Map<String, String> getEdcrNocMandatory(final String edcrNumber) {

        EdcrApplicationInfo edcrPlanInfo = drcRestService.getDcrPlanInfo(edcrNumber,
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
        Map<String, String> nocTypeMap = new HashMap<>();
        nocTypeMap.put(BpaConstants.FIREOCNOCTYPE, edcrPlanInfo.getPlan().getPlanInformation().getNocFireDept());
        nocTypeMap.put(BpaConstants.AIRPORTOCNOCTYPE, edcrPlanInfo.getPlan().getPlanInformation().getNocNearAirport());
        nocTypeMap.put(BpaConstants.NMAOCNOCTYPE, edcrPlanInfo.getPlan().getPlanInformation().getNocNearMonument());
        nocTypeMap.put(BpaConstants.ENVOCNOCTYPE, edcrPlanInfo.getPlan().getPlanInformation().getNocStateEnvImpact());
        nocTypeMap.put(BpaConstants.IRROCNOCTYPE, edcrPlanInfo.getPlan().getPlanInformation().getNocIrrigationDept());
        return nocTypeMap;
    }
}