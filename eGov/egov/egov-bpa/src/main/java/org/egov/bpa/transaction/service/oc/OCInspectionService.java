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

import org.apache.log4j.Logger;
import org.egov.bpa.autonumber.InspectionNumberGenerator;
import org.egov.bpa.master.entity.CheckListDetail;
import org.egov.bpa.master.service.CheckListDetailService;
import org.egov.bpa.transaction.entity.common.DocketCommon;
import org.egov.bpa.transaction.entity.common.DocketDetailCommon;
import org.egov.bpa.transaction.entity.common.InspectionCommon;
import org.egov.bpa.transaction.entity.common.InspectionFilesCommon;
import org.egov.bpa.transaction.entity.oc.OCInspection;
import org.egov.bpa.transaction.entity.oc.OccupancyCertificate;
import org.egov.bpa.transaction.repository.oc.OCInspectionRepository;
import org.egov.bpa.transaction.service.ApplicationBpaService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.infra.filestore.service.FileStoreService;
import org.egov.infra.security.utils.SecurityUtils;
import org.egov.infra.utils.autonumber.AutonumberServiceBeanResolver;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.egov.bpa.utils.OcConstants.OC_INSPECTION_ACCESS;
import static org.egov.bpa.utils.OcConstants.OC_INSPECTION_AREA_LOC;
import static org.egov.bpa.utils.OcConstants.OC_INSPECTION_HGT_BUILD_ABUT_ROAD;
import static org.egov.bpa.utils.OcConstants.OC_INSPECTION_LENGTH_OF_COMPOUND_WALL;
import static org.egov.bpa.utils.OcConstants.OC_INSPECTION_LOCATION;
import static org.egov.bpa.utils.OcConstants.OC_INSPECTION_MEASUREMENT;
import static org.egov.bpa.utils.OcConstants.OC_INSPECTION_NUMBER_OF_WELLS;
import static org.egov.bpa.utils.OcConstants.OC_INSPECTION_PROPOSED_STAGE_WORK;
import static org.egov.bpa.utils.OcConstants.OC_INSPECTION_SURROUNDING;
import static org.egov.bpa.utils.OcConstants.OC_INSPECTION_TYPE_OF_LAND;
import static org.egov.bpa.utils.OcConstants.OC_INSPECTION_WORK_COMPLETED_PER_PLAN;

@Service
@Transactional(readOnly = true)
public class OCInspectionService {

	private static final Logger LOGGER = Logger.getLogger(OCInspectionService.class);

	@Autowired
	private CheckListDetailService checkListDetailService;
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

	public Session getCurrentSession() {
		return entityManager.unwrap(Session.class);
	}

	@Transactional
	public OCInspection save(final OCInspection ocInspection) {
		InspectionCommon inspection = ocInspection.getInspection();
		if(inspection.getId() == null) {
			inspection.setInspectionNumber(generateInspectionNumber());
			inspection.setInspectedBy(securityUtils.getCurrentUser());
		}
		if (inspection.getInspectionDate() == null)
			inspection.setInspectionDate(new Date());
		buildInspectionFiles(inspection);
		//inspection.getPlanScrutinyChecklist().forEach(planScrutiny -> planScrutiny.setInspection(inspection));
		inspection.getDocket().get(0).setInspection(inspection);
		buildDocketDetails(ocInspection.getInspection().getDocket().get(0));
		return inspectionRepository.save(ocInspection);
	}

	public OCInspection findById(Long id) {
		return inspectionRepository.findOne(id);
	}

	public OCInspection findByOcApplicationNoAndInspectionNo(String applicationNo, String inspectionNo) {
		return inspectionRepository.findByOc_ApplicationNumberAndInspection_InspectionNumber(applicationNo, inspectionNo);
	}

	private void buildInspectionFiles(InspectionCommon inspection) {
		if(!inspection.getInspectionSupportDocs().isEmpty())
			for (InspectionFilesCommon filesCommon : inspection.getInspectionSupportDocs()) {
				if (filesCommon.getFile() != null && filesCommon.getFile().getSize() > 0) {
					filesCommon.setFileStoreMapper(applicationBpaService.addToFileStore(filesCommon.getFile()));
				} else if(filesCommon.getFileStoreMapper() != null) {
					filesCommon.setFileStoreMapper(filesCommon.getFileStoreMapper());
				}
				filesCommon.setInspection(inspection);
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
			final CheckListDetail chkListDtl = checkListDetailService.findOne(dd.getCheckListDetail().getId());
			dd.setCheckListDetail(chkListDtl);
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

	@SuppressWarnings("unchecked")
	public void buildDocketDetailList(InspectionCommon inspection) {
		Criteria criteriaLocation = getCheckListByServiceAndType(OC_INSPECTION_LOCATION);
		Criteria criteriaMeasurement = getCheckListByServiceAndType(OC_INSPECTION_MEASUREMENT);
		Criteria criteriaAccess = getCheckListByServiceAndType(OC_INSPECTION_ACCESS);
		Criteria criteriaSurrounding = getCheckListByServiceAndType(OC_INSPECTION_SURROUNDING);
		Criteria criteriaTypeofLand = getCheckListByServiceAndType(OC_INSPECTION_TYPE_OF_LAND);
		Criteria criteriaProposedStage = getCheckListByServiceAndType(OC_INSPECTION_PROPOSED_STAGE_WORK);
		Criteria criteriaWorkPerPlan = getCheckListByServiceAndType(OC_INSPECTION_WORK_COMPLETED_PER_PLAN);
		Criteria criteriaHgtAbutRoad = getCheckListByServiceAndType(OC_INSPECTION_HGT_BUILD_ABUT_ROAD);
		Criteria criteriaAreaLoc = getCheckListByServiceAndType(OC_INSPECTION_AREA_LOC);
		Criteria criteriaLengthOfCompoundWall = getCheckListByServiceAndType(OC_INSPECTION_LENGTH_OF_COMPOUND_WALL);
		Criteria criteriaNumberOfWell = getCheckListByServiceAndType(OC_INSPECTION_NUMBER_OF_WELLS);

		List<CheckListDetail> inspectionCheckList = criteriaLocation.list();
		List<CheckListDetail> inspectionCheckList2 = criteriaMeasurement.list();
		List<CheckListDetail> inspectionCheckList3 = criteriaAccess.list();
		List<CheckListDetail> inspectionCheckList4 = criteriaSurrounding.list();
		List<CheckListDetail> inspectionCheckList5 = criteriaTypeofLand.list();
		List<CheckListDetail> inspectionCheckList6 = criteriaProposedStage.list();
		List<CheckListDetail> inspectionCheckList7 = criteriaWorkPerPlan.list();
		List<CheckListDetail> inspectionCheckList8 = criteriaHgtAbutRoad.list();
		List<CheckListDetail> inspectionCheckAreaLoc = criteriaAreaLoc.list();
		List<CheckListDetail> inspectionCheckLenCompound = criteriaLengthOfCompoundWall.list();
		List<CheckListDetail> inspectionCheckNumberOfWell = criteriaNumberOfWell.list();

		List<DocketDetailCommon> docketTempLocList = inspectionCheckList.stream().map(chkListDtl -> new DocketDetailCommon(chkListDtl)).collect(Collectors.toList());
		List<DocketDetailCommon> docketTempMeasurementList = inspectionCheckList2.stream().map(chkListDtl -> new DocketDetailCommon(chkListDtl)).collect(Collectors.toList());
		List<DocketDetailCommon> docketTempAccessList = inspectionCheckList3.stream().map(chkListDtl -> new DocketDetailCommon(chkListDtl)).collect(Collectors.toList());
		List<DocketDetailCommon> docketTempSurroundingList = inspectionCheckList4.stream().map(chkListDtl -> new DocketDetailCommon(chkListDtl)).collect(Collectors.toList());
		List<DocketDetailCommon> docketTempLandList = inspectionCheckList5.stream().map(chkListDtl -> new DocketDetailCommon(chkListDtl)).collect(Collectors.toList());
		List<DocketDetailCommon> docketTempProposedWorkList = inspectionCheckList6.stream().map(chkListDtl -> new DocketDetailCommon(chkListDtl)).collect(Collectors.toList());
		List<DocketDetailCommon> docketTempWorkAsPerPlanList = inspectionCheckList7.stream().map(chkListDtl -> new DocketDetailCommon(chkListDtl)).collect(Collectors.toList());
		List<DocketDetailCommon> docketTempAbuttRoadList = inspectionCheckList8.stream().map(chkListDtl -> new DocketDetailCommon(chkListDtl)).collect(Collectors.toList());
		List<DocketDetailCommon> docketTempAreaLoc = inspectionCheckAreaLoc.stream().map(chkListDtl -> new DocketDetailCommon(chkListDtl)).collect(Collectors.toList());
		List<DocketDetailCommon> docketTempLengthOfCompoundWall = inspectionCheckLenCompound.stream().map(chkListDtl -> new DocketDetailCommon(chkListDtl)).collect(Collectors.toList());
		List<DocketDetailCommon> docketTempNumberOfWell = inspectionCheckNumberOfWell.stream().map(chkListDtl -> new DocketDetailCommon(chkListDtl)).collect(Collectors.toList());

		inspection.setDocketDetailLocList(docketTempLocList);
		inspection.setDocketDetailMeasurementList(docketTempMeasurementList);
		inspection.setDocketDetailAccessList(docketTempAccessList);
		inspection.setDocketDetailSurroundingPlotList(docketTempSurroundingList);
		inspection.setDocketDetailLandTypeList(docketTempLandList);
		inspection.setDocketDetailProposedWorkList(docketTempProposedWorkList);
		inspection.setDocketDetailWorkAsPerPlanList(docketTempWorkAsPerPlanList);
		inspection.setDocketDetailHgtAbuttRoadList(docketTempAbuttRoadList);
		inspection.setDocketDetailAreaLoc(docketTempAreaLoc);
		inspection.setDocketDetailLengthOfCompWall(docketTempLengthOfCompoundWall);
		inspection.setDocketDetailNumberOfWell(docketTempNumberOfWell);
	}

	public Criteria getCheckListByServiceAndType(final String checkListTypeVal) {

		final Criteria checkListDet = getCurrentSession().createCriteria(CheckListDetail.class, "checklistdet");
		checkListDet.createAlias("checklistdet.checkList", "checkList");
		checkListDet.add(Restrictions.eq("checkList.checklistType", checkListTypeVal));
		return checkListDet;
	}

	public void buildDocketDetailForModifyAndViewList(final InspectionCommon inspection, final Model model) {
		if (inspection != null && !inspection.getDocket().isEmpty())
			for (final DocketDetailCommon docketDet : inspection.getDocket().get(0).getDocketDetail()) {
				String checkListType = docketDet.getCheckListDetail().getCheckList().getChecklistType();
				if (OC_INSPECTION_LOCATION.equals(checkListType))
					inspection.getDocketDetailLocList().add(docketDet);
				if (OC_INSPECTION_MEASUREMENT.equals(checkListType))
					inspection.getDocketDetailMeasurementList().add(docketDet);
				if (OC_INSPECTION_ACCESS.equals(checkListType))
					inspection.getDocketDetailAccessList().add(docketDet);
				if (OC_INSPECTION_SURROUNDING.equals(checkListType))
					inspection.getDocketDetailSurroundingPlotList().add(docketDet);
				if (OC_INSPECTION_TYPE_OF_LAND.equals(checkListType))
					inspection.getDocketDetailLandTypeList().add(docketDet);
				if (OC_INSPECTION_PROPOSED_STAGE_WORK.equals(checkListType))
					inspection.getDocketDetailProposedWorkList().add(docketDet);
				if (OC_INSPECTION_WORK_COMPLETED_PER_PLAN.equals(checkListType))
					inspection.getDocketDetailWorkAsPerPlanList().add(docketDet);
				if (OC_INSPECTION_HGT_BUILD_ABUT_ROAD.equals(checkListType))
					inspection.getDocketDetailHgtAbuttRoadList().add(docketDet);
				if (OC_INSPECTION_AREA_LOC.equals(checkListType))
					inspection.getDocketDetailAreaLoc().add(docketDet);
				if (OC_INSPECTION_LENGTH_OF_COMPOUND_WALL.equals(checkListType))
					inspection.getDocketDetailLengthOfCompWall().add(docketDet);
				if (OC_INSPECTION_NUMBER_OF_WELLS.equals(checkListType))
					inspection.getDocketDetailNumberOfWell().add(docketDet);
			}
		model.addAttribute("docketDetailLocList", inspection.getDocketDetailLocList());
		model.addAttribute("docketDetailMeasumentList", inspection.getDocketDetailMeasurementList());
		model.addAttribute("docketDetailAccessList", inspection.getDocketDetailAccessList());
		model.addAttribute("docketDetlSurroundingPlotList", inspection.getDocketDetailSurroundingPlotList());
		model.addAttribute("docketDetailLandTypeList", inspection.getDocketDetailLandTypeList());
		model.addAttribute("docketDetailProposedWorkList", inspection.getDocketDetailProposedWorkList());
		model.addAttribute("docketDetailWorkAsPerPlanList", inspection.getDocketDetailWorkAsPerPlanList());
		model.addAttribute("docketDetailHgtAbuttRoadList", inspection.getDocketDetailHgtAbuttRoadList());
		model.addAttribute("docketDetailAreaLoc", inspection.getDocketDetailAreaLoc());
		model.addAttribute("docketDetailLengthOfCompWall", inspection.getDocketDetailLengthOfCompWall());
		model.addAttribute("docketDetailNumberOfWell", inspection.getDocketDetailNumberOfWell());
	}

	public Map<Long, String> prepareImagesForView(final OCInspection ocInspection) {
		Map<Long, String> imageMap = new HashMap<>();
		if (!ocInspection.getInspection().getInspectionSupportDocs().isEmpty())
			ocInspection.getInspection().getInspectionSupportDocs().forEach(
					imageFileDoc -> {
						try {
							if (imageFileDoc.getFileStoreMapper() != null) {
								final File file = fileStoreService.fetch(imageFileDoc.getFileStoreMapper().getFileStoreId(),
										BpaConstants.FILESTORE_MODULECODE);
								if (file != null)
									imageMap.put(imageFileDoc.getId(), Base64.getEncoder().encodeToString(
											FileCopyUtils.copyToByteArray(file)));
							}
						} catch (final IOException e) {
							LOGGER.error("Error while preparing the images for view", e);
						}
					});
		return imageMap;
	}

}
