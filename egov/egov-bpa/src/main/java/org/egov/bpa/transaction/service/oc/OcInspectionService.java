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
package org.egov.bpa.transaction.service.oc;

import static org.egov.bpa.utils.OcConstants.OC_INSPECTION;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.egov.bpa.autonumber.InspectionNumberGenerator;
import org.egov.bpa.master.entity.ChecklistServiceTypeMapping;
import org.egov.bpa.master.service.ChecklistServicetypeMappingService;
import org.egov.bpa.transaction.entity.common.DocketCommon;
import org.egov.bpa.transaction.entity.common.DocketDetailCommon;
import org.egov.bpa.transaction.entity.common.InspectionCommon;
import org.egov.bpa.transaction.entity.common.InspectionFilesCommon;
import org.egov.bpa.transaction.entity.common.PlanScrutinyChecklistCommon;
import org.egov.bpa.transaction.entity.enums.ScrutinyChecklistType;
import org.egov.bpa.transaction.entity.oc.OCInspection;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.repository.oc.OCInspectionRepository;
import org.egov.bpa.transaction.service.ApplicationBpaService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.filestore.service.FileStoreService;
import org.egov.infra.security.utils.SecurityUtils;
import org.egov.infra.utils.autonumber.AutonumberServiceBeanResolver;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;

@Service
@Transactional(readOnly = true)
public class OcInspectionService {

    private static final Logger LOGGER = Logger.getLogger(OcInspectionService.class);

    @Autowired
    private ChecklistServicetypeMappingService checklistServicetypeMappingService;
    @Autowired
    private AutonumberServiceBeanResolver beanResolver;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private OCInspectionRepository inspectionRepository;
    @Autowired
    private FileStoreService fileStoreService;
    @Autowired
    private SecurityUtils securityUtils;
    @Autowired
    private ApplicationBpaService applicationBpaService;
    @Autowired
    private PlanScrutinyChecklistCommonService planScrutinyChecklistService;

    public Session getCurrentSession() {
        return entityManager.unwrap(Session.class);
    }

    @Transactional
    public OCInspection save(final OCInspection ocInspection) {
        InspectionCommon inspection = ocInspection.getInspection();
        if (inspection.getId() == null) {
            inspection.setInspectionNumber(generateInspectionNumber());
            inspection.setInspectedBy(securityUtils.getCurrentUser());
        }
        if (inspection.getInspectionDate() == null)
            inspection.setInspectionDate(new Date());
        buildInspectionFiles(inspection);
        buildPlanScrutinyChecklistItems(ocInspection);
        inspection.getDocket().get(0).setInspection(inspection);
        buildDocketDetails(ocInspection.getInspection().getDocket().get(0));
        return inspectionRepository.save(ocInspection);
    }

    private void buildPlanScrutinyChecklistItems(final OCInspection inspection) {
		if (!inspection.getInspection().getPlanScrutinyChecklistForRuleTemp().isEmpty()) {
			planScrutinyChecklistService.delete(inspection.getInspection().getPlanScrutinyChecklistForRule());
			inspection.getInspection().getPlanScrutinyChecklistForRule().clear();
			inspection.getInspection()
					.setPlanScrutinyChecklistForRule(inspection.getInspection().getPlanScrutinyChecklistForRuleTemp());
			inspection.getInspection().getPlanScrutinyChecklistForRule()
					.forEach(planScrutiny -> planScrutiny.setInspection(inspection.getInspection()));
		}
		if (!inspection.getInspection().getPlanScrutinyChecklistForDrawingTemp().isEmpty()) {
			planScrutinyChecklistService.delete(inspection.getInspection().getPlanScrutinyChecklistForDrawing());
			inspection.getInspection().getPlanScrutinyChecklistForDrawing().clear();
			inspection.getInspection().setPlanScrutinyChecklistForDrawing(
					inspection.getInspection().getPlanScrutinyChecklistForDrawingTemp());
			inspection.getInspection().getPlanScrutinyChecklistForDrawing()
            .forEach(planScrutiny -> planScrutiny.setInspection(inspection.getInspection()));
		}
    }

    public OCInspection findById(Long id) {
        return inspectionRepository.findOne(id);
    }

    public OCInspection findByOcApplicationNoAndInspectionNo(String applicationNo, String inspectionNo) {
        return inspectionRepository.findByOc_ApplicationNumberAndInspection_InspectionNumber(applicationNo, inspectionNo);
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

    public List<OCInspection> findByIdOrderByIdAsc(final Long id) {
        return inspectionRepository.findByIdOrderByIdAsc(id);
    }

    public List<OCInspection> findByOcOrderByIdAsc(final OccupancyCertificate oc) {
        return inspectionRepository.findByOcOrderByIdDesc(oc);
    }

    private String generateInspectionNumber() {
        final InspectionNumberGenerator inspectionNUmber = beanResolver
                .getAutoNumberServiceFor(InspectionNumberGenerator.class);
        return inspectionNUmber.generateInspectionNumber("INSP");
    }

    public DocketCommon buildDocketDetails(final DocketCommon docket) {
        for (final DocketDetailCommon dd : docket.getDocketDetail()) {
            final ChecklistServiceTypeMapping chkListDtl = checklistServicetypeMappingService
                    .load(dd.getServiceChecklist().getId());
            dd.setServiceChecklist(chkListDtl);
            dd.setDocket(docket);
        }
        return docket;
    }

    public List<DocketCommon> buildDocDetFromUI(final OCInspection ocInspection) {
        List<DocketCommon> docket = new ArrayList<>();
        DocketCommon docObject = new DocketCommon();
        docObject.setInspection(ocInspection.getInspection());
        final List<DocketDetailCommon> docketDetailList = buildDocketDetail(ocInspection.getInspection());
        docObject.setDocketDetail(docketDetailList);
        docket.add(docObject);
        return docket;
    }

    public List<DocketDetailCommon> buildDocketDetail(final InspectionCommon inspection) {
        final List<DocketDetailCommon> docketDetailList = new ArrayList<>();
        docketDetailList.addAll(inspection.getDocketDetailLocList());
        return docketDetailList;
    }

    public void buildDocketDetailList(InspectionCommon inspection, final Long serviceTypeId) {
        List<ChecklistServiceTypeMapping> inspectionCheckList = checklistServicetypeMappingService
                .findByActiveByServiceTypeAndChecklist(serviceTypeId, OC_INSPECTION);
        List<DocketDetailCommon> docketTempLocList = inspectionCheckList.stream()
                .map(serviceChecklist -> new DocketDetailCommon(serviceChecklist)).collect(Collectors.toList());
        inspection.setDocketDetailLocList(docketTempLocList);
    }

    public void buildDocketDetailForModifyAndViewList(final InspectionCommon inspection, final Model model) {
        if (inspection != null && !inspection.getDocket().isEmpty())
            for (final DocketDetailCommon docketDet : inspection.getDocket().get(0).getDocketDetail()) {
                String checkListType = docketDet.getServiceChecklist().getChecklist().getChecklistType().getCode();
                if (OC_INSPECTION.equals(checkListType))
                    inspection.getDocketDetailLocList().add(docketDet);
            }
        model.addAttribute("docketDetailLocList", inspection.getDocketDetailLocList());
        model.addAttribute("docketDetailMeasurementList", inspection.getDocketDetailMeasurementList());

    }

    public void prepareImagesForView(final OCInspection ocInspection) {
        if (!ocInspection.getInspection().getInspectionSupportDocs().isEmpty())
            ocInspection.getInspection().getInspectionSupportDocs().forEach(
                    docketFile -> {
                        if (docketFile != null) {
                            Map<Long, String> imageMap = new HashMap<>();
                            docketFile.getImages().forEach(
                                    imageFilestore -> {
                                        final File file = fileStoreService.fetch(imageFilestore.getFileStoreId(),
                                                BpaConstants.FILESTORE_MODULECODE);
                                        if (file != null) {
                                            try {
                                                imageMap.put(imageFilestore.getId(), Base64.getEncoder().encodeToString(
                                                        FileCopyUtils.copyToByteArray(file)));
                                            } catch (final IOException e) {
                                                LOGGER.error("Error while preparing the images for view", e);
                                            }
                                        }
                                    });
                            docketFile.setEncodedImages(imageMap);
                        }
                    });
    }
    
    public List<ChecklistServiceTypeMapping> buildPlanScrutiny(Long serviceTypeId){
        return checklistServicetypeMappingService.findByActiveByServiceTypeAndChecklist(serviceTypeId, "OCPLANSCRUTINYRULE");
    }
    
    public List<ChecklistServiceTypeMapping> buildPlanScrutinyDrawing(Long serviceTypeId){
        return checklistServicetypeMappingService.findByActiveByServiceTypeAndChecklist(serviceTypeId, "OCPLANSCRUTINYDRAWING");
    }

    public List<PlanScrutinyChecklistCommon> getPlanScrutinyForRuleValidation(OCInspection ocInspection){
  	   	return planScrutinyChecklistService
				.findByInspectionAndScrutinyChecklistType(ocInspection.getInspection(), ScrutinyChecklistType.RULE_VALIDATION);
    }

    public List<PlanScrutinyChecklistCommon> getPlanScrutinyForDrawingDetails(OCInspection ocInspection){
 	   return planScrutinyChecklistService.findByInspectionAndScrutinyChecklistType(ocInspection.getInspection(), ScrutinyChecklistType.DRAWING_DETAILS);
    }
    
    public void buildPlanScrutinyChecklistDetails(OCInspection inspectionObj) {
        inspectionObj.getInspection().getPlanScrutinyChecklistForRule().clear();
        inspectionObj.getInspection().setPlanScrutinyChecklistForRule(getPlanScrutinyForRuleValidation(inspectionObj));
        inspectionObj.getInspection().getPlanScrutinyChecklistForDrawing().clear();
        inspectionObj.getInspection().setPlanScrutinyChecklistForDrawing(getPlanScrutinyForDrawingDetails(inspectionObj));

    }
}
