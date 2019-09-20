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
package org.egov.bpa.transaction.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.egov.bpa.autonumber.InspectionNumberGenerator;
import org.egov.bpa.config.properties.BpaApplicationSettings;
import org.egov.bpa.master.entity.ChecklistServiceTypeMapping;
import org.egov.bpa.master.service.ChecklistServicetypeMappingService;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.InspectionNotice;
import org.egov.bpa.transaction.entity.PermitInspection;
import org.egov.bpa.transaction.entity.common.DocketCommon;
import org.egov.bpa.transaction.entity.common.DocketDetailCommon;
import org.egov.bpa.transaction.entity.common.InspectionCommon;
import org.egov.bpa.transaction.entity.common.InspectionFilesCommon;
import org.egov.bpa.transaction.entity.common.PlanScrutinyChecklistCommon;
import org.egov.bpa.transaction.entity.enums.ScrutinyChecklistType;
import org.egov.bpa.transaction.repository.InspectionRepository;
import org.egov.bpa.transaction.service.oc.PlanScrutinyChecklistCommonService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.bpa.utils.BpaUtils;
import org.egov.commons.service.CheckListTypeService;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.admin.master.service.UserService;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.filestore.service.FileStoreService;
import org.egov.infra.utils.autonumber.AutonumberServiceBeanResolver;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;

@Service
@Transactional(readOnly = true)
public class InspectionService {

    private static final Logger LOGGER = Logger.getLogger(InspectionService.class);

    public static final List<String> CHECKLIST_TYPES = new ArrayList<String>() {
        private static final long serialVersionUID = -1817740030848421168L;

        {
            add(BpaConstants.INSPECTIONLOCATION);
            add(BpaConstants.INSPECTIONMEASUREMENT);
            add(BpaConstants.INSPECTIONACCESS);
            add(BpaConstants.INSPECTIONSURROUNDING);
            add(BpaConstants.INSPECTIONTYPEOFLAND);
            add(BpaConstants.INSPECTIONPROPOSEDSTAGEWORK);
            add(BpaConstants.INSPECTIONWORKCOMPLETEDPERPLAN);
            add(BpaConstants.INSPECTIONHGTBUILDABUTROAD);
            add(BpaConstants.INSPECTIONAREALOC);
            add(BpaConstants.INSPECTIONLENGTHOFCOMPOUNDWALL);
            add(BpaConstants.INSPECTIONNUMBEROFWELLS);
            add(BpaConstants.INSPECTIONERECTIONOFTOWER);
            add(BpaConstants.INSPECTIONSHUTTER);
            add(BpaConstants.INSPECTIONROOFCONVERSION);

        }
    };

    @Autowired
    private ChecklistServicetypeMappingService checklistServicetypeMappingService;
    @Autowired
    private AutonumberServiceBeanResolver beanResolver;
    @Autowired
    private UserService userService;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private InspectionRepository inspectionRepository;
    @Autowired
    private FileStoreService fileStoreService;
    @Autowired
    private ApplicationBpaService applicationBpaService;
    @Autowired
    private PlanScrutinyChecklistCommonService planScrutinyChecklistService;
    @Autowired
    private InspectionNoticeService inspectionNoticeService;
    @Autowired
    private CheckListTypeService checkListTypeService;
    @Autowired
    private BpaApplicationSettings bpaApplicationSettings;
    @Autowired
    private BpaUtils bpaUtils;

    public PermitInspection findById(Long id) {
        return inspectionRepository.getOne(id);
    }

    public Session getCurrentSession() {
        return entityManager.unwrap(Session.class);
    }

    @Transactional
    public PermitInspection save(final PermitInspection permitInspn, final BpaApplication application) {
        InspectionNotice notice;
        notice = inspectionNoticeService.findByRefNumberAndInspectionNumber(
                permitInspn.getApplication().getApplicationNumber(), permitInspn.getInspection().getInspectionNumber());
        if (notice != null) {
            inspectionNoticeService.delete(notice);
        }
        User currentUser = null;
        if (permitInspn.getId() == null) {
            if (ApplicationThreadLocals.getUserId() != null)
                currentUser = userService.getUserById(ApplicationThreadLocals.getUserId());

            permitInspn.getInspection().setInspectedBy(currentUser);

            permitInspn.getInspection().setInspectionNumber(generateInspectionNumber());
        }
        if (permitInspn.getInspection().getInspectionDate() == null)
            permitInspn.getInspection().setInspectionDate(new Date());
        for (DocketCommon d : permitInspn.getInspection().getDocket()) {
            d.setInspection(permitInspn.getInspection());
            for (DocketDetailCommon ddc : d.getDocketDetail()) {
                ddc.setDocket(d);
            }
        }
        buildInspectionFiles(permitInspn.getInspection());
        buildPlanScrutinyChecklistItems(permitInspn);
        permitInspn.setApplication(application);
        buildDocketDetails(permitInspn.getInspection().getDocket());
        return inspectionRepository.save(permitInspn);
    }

    private void buildPlanScrutinyChecklistItems(final PermitInspection permitInspn) {
        InspectionCommon inspection = permitInspn.getInspection();
        if (!inspection.getPlanScrutinyChecklistForRuleTemp().isEmpty()) {
            planScrutinyChecklistService.delete(inspection.getPlanScrutinyChecklistForRule());
            inspection.getPlanScrutinyChecklistForRule().clear();
            inspection.setPlanScrutinyChecklistForRule(inspection.getPlanScrutinyChecklistForRuleTemp());
            inspection.getPlanScrutinyChecklistForRule()
                    .forEach(planScrutiny -> planScrutiny.setInspection(inspection));
        }
        if (!inspection.getPlanScrutinyChecklistForDrawingTemp().isEmpty()) {
            planScrutinyChecklistService.delete(inspection.getPlanScrutinyChecklistForDrawing());
            inspection.getPlanScrutinyChecklistForDrawing().clear();
            inspection.setPlanScrutinyChecklistForDrawing(inspection.getPlanScrutinyChecklistForDrawingTemp());
            inspection.getPlanScrutinyChecklistForDrawing()
                    .forEach(planScrutiny -> planScrutiny.setInspection(inspection));
        }
    }

    private void buildInspectionFiles(InspectionCommon inspection) {
        if (!inspection.getInspectionSupportDocs().isEmpty())
            for (InspectionFilesCommon filesCommon : inspection.getInspectionSupportDocs()) {
                filesCommon.setInspection(inspection);
                buildInspectionFiles(filesCommon);
            }
    }

    private void buildInspectionFiles(final InspectionFilesCommon inspectionFiles) {
        if (inspectionFiles.getFiles() != null && inspectionFiles.getFiles().length > 0) {
            Set<FileStoreMapper> existingFiles = new HashSet<>();
            existingFiles.addAll(inspectionFiles.getImages());
            existingFiles.addAll(applicationBpaService.addToFileStore(inspectionFiles.getFiles()));
            inspectionFiles.setImages(existingFiles);
        }
    }

    public List<PermitInspection> findByIdOrderByIdAsc(final Long id) {
        return inspectionRepository.findByIdOrderByIdAsc(id);
    }

    public List<PermitInspection> findByBpaApplicationOrderByIdAsc(final BpaApplication application) {
        return inspectionRepository.findByApplicationOrderByIdDesc(application);
    }

    private String generateInspectionNumber() {
        final InspectionNumberGenerator inspectionNUmber = beanResolver
                .getAutoNumberServiceFor(InspectionNumberGenerator.class);
        return inspectionNUmber.generateInspectionNumber("INSP");
    }

    public void buildDocketDetails(final List<DocketCommon> dockets) {
        for (DocketCommon docket : dockets) {
            for (final DocketDetailCommon dd : docket.getDocketDetail()) {
                final ChecklistServiceTypeMapping checkdet = checklistServicetypeMappingService
                        .load(dd.getServiceChecklist().getId());
                dd.setServiceChecklist(checkdet);
                dd.setDocket(docket);
            }
        }

    }

    public List<DocketCommon> buildDocDetFromUI(final PermitInspection inspection) {
        List<DocketCommon> docket = new ArrayList<>();
        DocketCommon docObject = new DocketCommon();
        final List<DocketDetailCommon> docketDetailList = buildDocketDetail(inspection.getInspection());
        docObject.setDocketDetail(docketDetailList);
        docket.add(docObject);
        return docket;
    }

    public List<DocketDetailCommon> buildDocketDetail(final InspectionCommon inspection) {
        final List<DocketDetailCommon> docketDetailList = new ArrayList<>();
        docketDetailList.addAll(inspection.getDocketDetailLocList());
        docketDetailList.addAll(inspection.getDocketDetailMeasurementList());
        docketDetailList.addAll(inspection.getDocketDetailAccessList());
        docketDetailList.addAll(inspection.getDocketDetailSurroundingPlotList());
        docketDetailList.addAll(inspection.getDocketDetailLandTypeList());
        docketDetailList.addAll(inspection.getDocketDetailProposedWorkList());
        docketDetailList.addAll(inspection.getDocketDetailWorkAsPerPlanList());
        docketDetailList.addAll(inspection.getDocketDetailHgtAbuttRoadList());
        docketDetailList.addAll(inspection.getDocketDetailAreaLoc());
        docketDetailList.addAll(inspection.getDocketDetailLengthOfCompWall());
        docketDetailList.addAll(inspection.getDocketDetailNumberOfWell());
        docketDetailList.addAll(inspection.getDocketDetailErectionTower());
        docketDetailList.addAll(inspection.getDocketDetailShutter());
        docketDetailList.addAll(inspection.getDocketDetailRoofConversion());
        return docketDetailList;
    }

    public void buildDocketDetailList(PermitInspection inspection, Long serviceTypeId) {

        InspectionCommon inspectionCommon = inspection.getInspection();
        List<ChecklistServiceTypeMapping> mappingList;
        List<DocketDetailCommon> commonList;
        DocketCommon docket;
        for (String type : CHECKLIST_TYPES) {
            mappingList = checklistServicetypeMappingService.findByActiveByServiceTypeAndChecklist(serviceTypeId, type);
            commonList = new ArrayList<>();
            for (final ChecklistServiceTypeMapping checkDet : mappingList) {
                commonList.add(createDocketDetailObject(checkDet));
            }

            docket = new DocketCommon();
            if (mappingList != null && !mappingList.isEmpty())
                docket.setChecklistType(checkListTypeService.findByCode(type));
            docket.setDocketDetail(commonList);
            inspectionCommon.getDocket().add(docket);

        }

    }

    private DocketDetailCommon createDocketDetailObject(final ChecklistServiceTypeMapping checkDet) {
        final DocketDetailCommon docdet = new DocketDetailCommon();
        docdet.setServiceChecklist(checkDet);
        return docdet;
    }

    public void setDocketDetList(final List<DocketDetailCommon> docketTempList,
            final ChecklistServiceTypeMapping checkDet) {
        final DocketDetailCommon docdet = createDocketDetailObject(checkDet);
        docketTempList.add(docdet);
    }

    public void buildDocketDetailForModifyAndViewList(final PermitInspection permitInspn, final Model model) {
        if (permitInspn != null && !permitInspn.getInspection().getDocket().isEmpty()) {
            InspectionCommon inspection = permitInspn.getInspection();
            for (final DocketDetailCommon docketDet : inspection.getDocket().get(0).getDocketDetail()) {
                String checklisType = docketDet.getServiceChecklist().getChecklist().getChecklistType().getCode();
                if (checklisType.equals(BpaConstants.INSPECTIONLOCATION))
                    inspection.getDocketDetailLocList().add(docketDet);
                if (checklisType.equals(BpaConstants.INSPECTIONMEASUREMENT))
                    inspection.getDocketDetailMeasurementList().add(docketDet);
                if (checklisType.equals(BpaConstants.INSPECTIONACCESS))
                    inspection.getDocketDetailAccessList().add(docketDet);
                if (checklisType.equals(BpaConstants.INSPECTIONSURROUNDING))
                    inspection.getDocketDetailSurroundingPlotList().add(docketDet);
                if (checklisType.equals(BpaConstants.INSPECTIONTYPEOFLAND))
                    inspection.getDocketDetailLandTypeList().add(docketDet);
                if (checklisType.equals(BpaConstants.INSPECTIONPROPOSEDSTAGEWORK))
                    inspection.getDocketDetailProposedWorkList().add(docketDet);
                if (checklisType.equals(BpaConstants.INSPECTIONWORKCOMPLETEDPERPLAN))
                    inspection.getDocketDetailWorkAsPerPlanList().add(docketDet);
                if (checklisType.equals(BpaConstants.INSPECTIONHGTBUILDABUTROAD))
                    inspection.getDocketDetailHgtAbuttRoadList().add(docketDet);
                if (checklisType.equals(BpaConstants.INSPECTIONAREALOC))
                    inspection.getDocketDetailAreaLoc().add(docketDet);
                if (checklisType.equals(BpaConstants.INSPECTIONLENGTHOFCOMPOUNDWALL))
                    inspection.getDocketDetailLengthOfCompWall().add(docketDet);
                if (checklisType.equals(BpaConstants.INSPECTIONNUMBEROFWELLS))
                    inspection.getDocketDetailNumberOfWell().add(docketDet);
                if (checklisType.equals(BpaConstants.INSPECTIONSHUTTER))
                    inspection.getDocketDetailShutter().add(docketDet);
                if (checklisType.equals(BpaConstants.INSPECTIONERECTIONOFTOWER))
                    inspection.getDocketDetailErectionTower().add(docketDet);
                if (checklisType.equals(BpaConstants.INSPECTIONROOFCONVERSION))
                    inspection.getDocketDetailRoofConversion().add(docketDet);
            }
            model.addAttribute("docketDetailLocList", inspection.getDocketDetailLocList());
            model.addAttribute("docketDetailMeasurementList", inspection.getDocketDetailMeasurementList());
            model.addAttribute("docketDetailAccessList", inspection.getDocketDetailAccessList());
            model.addAttribute("docketDetailSurroundingPlotList", inspection.getDocketDetailSurroundingPlotList());
            model.addAttribute("docketDetailLandTypeList", inspection.getDocketDetailLandTypeList());
            model.addAttribute("docketDetailProposedWorkList", inspection.getDocketDetailProposedWorkList());
            model.addAttribute("docketDetailWorkAsPerPlanList", inspection.getDocketDetailWorkAsPerPlanList());
            model.addAttribute("docketDetailHgtAbuttRoadList", inspection.getDocketDetailHgtAbuttRoadList());
            model.addAttribute("docketDetailAreaLoc", inspection.getDocketDetailAreaLoc());
            model.addAttribute("docketDetailLengthOfCompWall", inspection.getDocketDetailLengthOfCompWall());
            model.addAttribute("docketDetailNumberOfWell", inspection.getDocketDetailNumberOfWell());
            model.addAttribute("docketDetailErectionTower", inspection.getDocketDetailErectionTower());
            model.addAttribute("docketDetailShutter", inspection.getDocketDetailShutter());
            model.addAttribute("docketDetailRoofConversion", inspection.getDocketDetailRoofConversion());
        }
    }

    public void prepareImagesForView(final PermitInspection permitInspn) {
        if (!permitInspn.getInspection().getInspectionSupportDocs().isEmpty())
            permitInspn.getInspection().getInspectionSupportDocs().forEach(docketFile -> {
                if (docketFile != null) {
                    Map<Long, String> imageMap = new HashMap<>();
                    docketFile.getImages().forEach(imageFilestore -> {
                        final File file = fileStoreService.fetch(imageFilestore.getFileStoreId(),
                                BpaConstants.FILESTORE_MODULECODE);
                        if (file != null) {
                            try {
                                imageMap.put(imageFilestore.getId(),
                                        Base64.getEncoder().encodeToString(FileCopyUtils.copyToByteArray(file)));
                            } catch (final IOException e) {
                                LOGGER.error("Error while preparing the images for view", e);
                            }
                        }
                    });
                    docketFile.setEncodedImages(imageMap);
                }
            });
    }

    public List<ChecklistServiceTypeMapping> buildPlanScrutiny(Long serviceTypeId) {
        return checklistServicetypeMappingService.findByActiveByServiceTypeAndChecklist(serviceTypeId, "PLANSCRUTINY");
    }

    public List<ChecklistServiceTypeMapping> buildPlanScrutinyDrawing(Long serviceTypeId) {
        return checklistServicetypeMappingService.findByActiveByServiceTypeAndChecklist(serviceTypeId,
                "PLANSCRUTINYDRAWING");
    }

    public List<PlanScrutinyChecklistCommon> getPlanScrutinyForRuleValidation(PermitInspection permitInspection) {
        return planScrutinyChecklistService.findByInspectionAndScrutinyChecklistType(permitInspection.getInspection(),
                ScrutinyChecklistType.RULE_VALIDATION);
    }

    public List<PlanScrutinyChecklistCommon> getPlanScrutinyForDrawingDetails(PermitInspection permitInspection) {
        return planScrutinyChecklistService.findByInspectionAndScrutinyChecklistType(permitInspection.getInspection(),
                ScrutinyChecklistType.DRAWING_DETAILS);
    }

    public void buildPlanScrutinyChecklistDetails(PermitInspection permitInspn) {
        permitInspn.getInspection().getPlanScrutinyChecklistForRule().clear();
        permitInspn.getInspection().setPlanScrutinyChecklistForRule(
                planScrutinyChecklistService.findByInspectionAndScrutinyChecklistType(permitInspn.getInspection(),
                        ScrutinyChecklistType.RULE_VALIDATION));
        permitInspn.getInspection().getPlanScrutinyChecklistForDrawing().clear();
        permitInspn.getInspection().setPlanScrutinyChecklistForDrawing(
                planScrutinyChecklistService.findByInspectionAndScrutinyChecklistType(permitInspn.getInspection(),
                        ScrutinyChecklistType.DRAWING_DETAILS));
    }

    public void validatePermitInspectionDocs(final PermitInspection inspection, final BindingResult errors) {
        List<String> tsDocAllowedExtenstions = new ArrayList<>(
                Arrays.asList(bpaApplicationSettings.getValue("bpa.oc.inspection.docs.allowed.extenstions").split(",")));

        List<String> tsDocMimeTypes = new ArrayList<>(
                Arrays.asList(bpaApplicationSettings.getValue("bpa.oc.inspection.docs.allowed.mime.types").split(",")));

        Integer i = 0;
        for (InspectionFilesCommon document : inspection.getInspection().getInspectionSupportDocs()) {
            bpaUtils.validateFiles(errors, tsDocAllowedExtenstions, tsDocMimeTypes, document.getFiles(),
                    "inspection.inspectionSupportDocs[" + i + "].files",
                    bpaApplicationSettings.getValue("bpa.oc.inspection.docs.max.size"));
            i++;
        }

    }

}
