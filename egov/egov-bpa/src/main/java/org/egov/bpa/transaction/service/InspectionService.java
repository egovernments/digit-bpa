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

import org.apache.log4j.Logger;
import org.egov.bpa.autonumber.InspectionNumberGenerator;
import org.egov.bpa.master.entity.CheckListDetail;
import org.egov.bpa.master.service.CheckListDetailService;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.Docket;
import org.egov.bpa.transaction.entity.DocketDetail;
import org.egov.bpa.transaction.entity.Inspection;
import org.egov.bpa.transaction.repository.InspectionRepository;
import org.egov.bpa.utils.BpaConstants;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.admin.master.service.UserService;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.filestore.service.FileStoreService;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class InspectionService {

	private static final Logger LOGGER = Logger.getLogger(InspectionService.class);

	@Autowired
	private CheckListDetailService checkListDetailService;
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
	private PlanScrutinyChecklistService planScrutinyChecklistService;
	
	public Inspection findById(Long id)
	{
	    return inspectionRepository.getOne(id);
	}
	public Session getCurrentSession() {
		return entityManager.unwrap(Session.class);
	}

	@Transactional
	public Inspection save(final Inspection inspection, final BpaApplication application) {
		User currentUser = null;
		if (inspection.getId() == null) {
			if (ApplicationThreadLocals.getUserId() != null)
				currentUser = userService.getUserById(ApplicationThreadLocals.getUserId());

			inspection.setInspectedBy(currentUser);

			inspection.setInspectionNumber(generateInspectionNumber());
		}
		if (inspection.getInspectionDate() == null)
			inspection.setInspectionDate(new Date());
		buildInspectionFiles(inspection);
		buildPlanScrutinyChecklistItems(inspection);
		inspection.setApplication(application);
		inspection.getDocket().get(0).setInspection(inspection);
		buildDocketDetails(inspection.getDocket().get(0));
		return inspectionRepository.save(inspection);
	}

	private void buildPlanScrutinyChecklistItems(final Inspection inspection) {
		if(!inspection.getPlanScrutinyChecklistTemp().isEmpty() && !inspection.getPlanScrutinyChecklistForDrawingTemp().isEmpty()) {
			planScrutinyChecklistService.delete(inspection.getPlanScrutinyChecklist());
			planScrutinyChecklistService.delete(inspection.getPlanScrutinyChecklistForDrawing());
			inspection.getPlanScrutinyChecklist().clear();
			inspection.getPlanScrutinyChecklistForDrawing().clear();
			inspection.setPlanScrutinyChecklist(inspection.getPlanScrutinyChecklistTemp());
			inspection.setPlanScrutinyChecklistForDrawing(inspection.getPlanScrutinyChecklistForDrawingTemp());
			inspection.getPlanScrutinyChecklist().forEach(planScrutiny -> planScrutiny.setInspection(inspection));
			inspection.getPlanScrutinyChecklistForDrawing().forEach(planScrutiny -> planScrutiny.setInspection(inspection));
		}
	}

	private void buildInspectionFiles(Inspection inspection) {
		if (inspection.getFiles() != null && inspection.getFiles().length > 0) {
			Set<FileStoreMapper> existingInsnFiles = new HashSet<>();
			existingInsnFiles.addAll(inspection.getInspectionSupportDocs());
			existingInsnFiles.addAll(applicationBpaService.addToFileStore(inspection.getFiles()));
			inspection.setInspectionSupportDocs(existingInsnFiles);
		}
	}

	public List<Inspection> findByIdOrderByIdAsc(final Long id) {
		return inspectionRepository.findByIdOrderByIdAsc(id);
	}

	public List<Inspection> findByBpaApplicationOrderByIdAsc(final BpaApplication application) {
		return inspectionRepository.findByApplicationOrderByIdDesc(application);
	}

	private String generateInspectionNumber() {
		final InspectionNumberGenerator inspectionNUmber = beanResolver
				.getAutoNumberServiceFor(InspectionNumberGenerator.class);
		return inspectionNUmber.generateInspectionNumber("INSP");
	}

	public Docket buildDocketDetails(final Docket docket) {
		for (final DocketDetail dd : docket.getDocketDetail()) {
			final CheckListDetail checkdet = checkListDetailService.findOne(dd.getCheckListDetail().getId());
			dd.setCheckListDetail(checkdet);
			dd.setDocket(docket);
		}
		return docket;

	}

	public List<Docket> buildDocDetFromUI(final Inspection inspection) {
		List<Docket> docket = new ArrayList<>();
		Docket docObject = new Docket();
		final List<DocketDetail> docketDetailList = buildDocketDetail(inspection);
		docObject.setDocketDetail(docketDetailList);
		docket.add(docObject);
		return docket;
	}

	public List<DocketDetail> buildDocketDetail(final Inspection inspection) {
		final List<DocketDetail> docketDetailList = new ArrayList<>();
		docketDetailList.addAll(inspection.getDocketDetailLocList());
		docketDetailList.addAll(inspection.getDocketDetailMeasumentList());
		docketDetailList.addAll(inspection.getDocketDetailAccessList());
		docketDetailList.addAll(inspection.getDocketDetlSurroundingPlotList());
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
	public void buildDocketDetailList(Inspection inspection) {
		List<DocketDetail> docketTempLocList = new ArrayList<>();
		List<DocketDetail> docketTempMeasumentList = new ArrayList<>();
		List<DocketDetail> docketTempAccessList = new ArrayList<>();
		List<DocketDetail> docketTempSurroundingList = new ArrayList<>();
		List<DocketDetail> docketTempLandList = new ArrayList<>();
		List<DocketDetail> docketTempProposedWorkList = new ArrayList<>();
		List<DocketDetail> docketTempWorkAsPerPlanList = new ArrayList<>();
		List<DocketDetail> docketTempAbbuteRoadList = new ArrayList<>();
		List<DocketDetail> docketTempAreaLoc = new ArrayList<>();
		List<DocketDetail> docketTempLengthOfCompoundWall = new ArrayList<>();
		List<DocketDetail> docketTempNumberofWell = new ArrayList<>();
		List<DocketDetail> docketTempShutter = new ArrayList<>();
		List<DocketDetail> docketTempErectionofTower = new ArrayList<>();
		List<DocketDetail> docketTempRoofConv = new ArrayList<>();
		Criteria criteriaLoc = getCheckListByServiceAndType(BpaConstants.INSPECTIONLOCATION);
		Criteria criteriaMeasur = getCheckListByServiceAndType(BpaConstants.INSPECTIONMEASUREMENT);
		Criteria criteriaAccess = getCheckListByServiceAndType(BpaConstants.INSPECTIONACCESS);
		Criteria criteriaSurrounding = getCheckListByServiceAndType(BpaConstants.INSPECTIONSURROUNDING);
		Criteria criteriaTypeofLand = getCheckListByServiceAndType(BpaConstants.INSPECTIONTYPEOFLAND);
		Criteria criteriaProposedStage = getCheckListByServiceAndType(BpaConstants.INSPECTIONPROPOSEDSTAGEWORK);
		Criteria criteriaWorkPerPlan = getCheckListByServiceAndType(BpaConstants.INSPECTIONWORKCOMPLETEDPERPLAN);
		Criteria criteriaHgtAbutRoad = getCheckListByServiceAndType(BpaConstants.INSPECTIONHGTBUILDABUTROAD);
		Criteria criteriaAreaLoc = getCheckListByServiceAndType(BpaConstants.INSPECTIONAREALOC);
		Criteria criteriaLengthOfCompoundWall = getCheckListByServiceAndType(BpaConstants.INSPECTIONLENGTHOFCOMPOUNDWALL);
		Criteria criteriaNumberofWell = getCheckListByServiceAndType(BpaConstants.INSPECTIONNUMBEROFWELLS);
		Criteria criteriaShutter = getCheckListByServiceAndType(BpaConstants.INSPECTIONSHUTTER);
		Criteria criteriaErectionofTower = getCheckListByServiceAndType(BpaConstants.INSPECTIONERECTIONOFTOWER);
		Criteria criteriaRoofConv = getCheckListByServiceAndType(BpaConstants.INSPECTIONROOFCONVERSION);
		List<CheckListDetail> inspectionCheckList = criteriaLoc.list();
		List<CheckListDetail> inspectionCheckList2 = criteriaMeasur.list();
		List<CheckListDetail> inspectionCheckList3 = criteriaAccess.list();
		List<CheckListDetail> inspectionCheckList4 = criteriaSurrounding.list();
		List<CheckListDetail> inspectionCheckList5 = criteriaTypeofLand.list();
		List<CheckListDetail> inspectionCheckList6 = criteriaProposedStage.list();
		List<CheckListDetail> inspectionCheckList7 = criteriaWorkPerPlan.list();
		List<CheckListDetail> inspectionCheckList8 = criteriaHgtAbutRoad.list();
		List<CheckListDetail> inspectionCheckAreaLoc = criteriaAreaLoc.list();
		List<CheckListDetail> inspectionCheckLenCompound = criteriaLengthOfCompoundWall.list();
		List<CheckListDetail> inspectionCheckNumberofWell = criteriaNumberofWell.list();
		List<CheckListDetail> inspectionCheckErectionOfTower = criteriaErectionofTower.list();
		List<CheckListDetail> inspectionCheckShutter = criteriaShutter.list();
		List<CheckListDetail> inspectionCheckRoofCnv = criteriaRoofConv.list();

		for (final CheckListDetail checkDet : inspectionCheckList) {
			final DocketDetail docdet = createDocketDetailObject(checkDet);
			docketTempLocList.add(docdet);
		}
		for (final CheckListDetail checkDet : inspectionCheckList2) {
			final DocketDetail docdet = createDocketDetailObject(checkDet);
			docketTempMeasumentList.add(docdet);
		}
		for (final CheckListDetail checkDet : inspectionCheckList3) {
			final DocketDetail docdet = createDocketDetailObject(checkDet);
			docketTempAccessList.add(docdet);
		}

		for (final CheckListDetail checkDet : inspectionCheckList4) {
			final DocketDetail docdet = createDocketDetailObject(checkDet);
			docketTempSurroundingList.add(docdet);
		}
		for (final CheckListDetail checkDet : inspectionCheckList5) {
			final DocketDetail docdet = createDocketDetailObject(checkDet);
			docketTempLandList.add(docdet);
		}

		for (final CheckListDetail checkDet : inspectionCheckList6) {
			final DocketDetail docdet = createDocketDetailObject(checkDet);
			docketTempProposedWorkList.add(docdet);
		}

		for (final CheckListDetail checkDet : inspectionCheckList7) {
			final DocketDetail docdet = createDocketDetailObject(checkDet);
			docketTempWorkAsPerPlanList.add(docdet);
		}

		for (final CheckListDetail checkDet : inspectionCheckList8) {
			final DocketDetail docdet = createDocketDetailObject(checkDet);
			docketTempAbbuteRoadList.add(docdet);
		}

		for (final CheckListDetail checkDet : inspectionCheckAreaLoc) {
			final DocketDetail docdet = createDocketDetailObject(checkDet);
			docketTempAreaLoc.add(docdet);
		}

		for (final CheckListDetail checkDet : inspectionCheckLenCompound) {
			final DocketDetail docdet = createDocketDetailObject(checkDet);
			docketTempLengthOfCompoundWall.add(docdet);
		}

		for (final CheckListDetail checkDet : inspectionCheckNumberofWell) {
			final DocketDetail docdet = createDocketDetailObject(checkDet);
			docketTempNumberofWell.add(docdet);
		}

		for (final CheckListDetail checkDet : inspectionCheckErectionOfTower) {
			final DocketDetail docdet = createDocketDetailObject(checkDet);
			docketTempErectionofTower.add(docdet);
		}

		for (final CheckListDetail checkDet : inspectionCheckRoofCnv) {
			final DocketDetail docdet = createDocketDetailObject(checkDet);
			docketTempRoofConv.add(docdet);
		}

		for (final CheckListDetail checkDet : inspectionCheckShutter) {
			final DocketDetail docdet = createDocketDetailObject(checkDet);
			docketTempShutter.add(docdet);
		}
		inspection.setDocketDetailLocList(docketTempLocList);
		inspection.setDocketDetailMeasumentList(docketTempMeasumentList);
		inspection.setDocketDetailAccessList(docketTempAccessList);
		inspection.setDocketDetlSurroundingPlotList(docketTempSurroundingList);
		inspection.setDocketDetailLandTypeList(docketTempLandList);
		inspection.setDocketDetailProposedWorkList(docketTempProposedWorkList);
		inspection.setDocketDetailWorkAsPerPlanList(docketTempWorkAsPerPlanList);
		inspection.setDocketDetailHgtAbuttRoadList(docketTempAbbuteRoadList);
		inspection.setDocketDetailAreaLoc(docketTempAreaLoc);
		inspection.setDocketDetailLengthOfCompWall(docketTempLengthOfCompoundWall);
		inspection.setDocketDetailNumberOfWell(docketTempNumberofWell);
		inspection.setDocketDetailErectionTower(docketTempErectionofTower);
		inspection.setDocketDetailShutter(docketTempShutter);
		inspection.setDocketDetailRoofConversion(docketTempRoofConv);
	}

	private DocketDetail createDocketDetailObject(final CheckListDetail checkDet) {
		final DocketDetail docdet = new DocketDetail();
		docdet.setCheckListDetail(checkDet);
		return docdet;
	}

	public Criteria getCheckListByServiceAndType(final String checkListTypeVal) {

		final Criteria checkListDet = getCurrentSession().createCriteria(CheckListDetail.class, "checklistdet");
		checkListDet.createAlias("checklistdet.checkList", "checkList");
		checkListDet.add(Restrictions.eq("checkList.checklistType", checkListTypeVal));
		return checkListDet;
	}

	public void setDocketDetList(final List<DocketDetail> docketTempList, final CheckListDetail checkDet) {
		final DocketDetail docdet = createDocketDetailObject(checkDet);
		docketTempList.add(docdet);
	}

	public void buildDocketDetailForModifyAndViewList(final Inspection inspection, final Model model) {
		if (inspection != null && !inspection.getDocket().isEmpty())
			for (final DocketDetail docketDet : inspection.getDocket().get(0).getDocketDetail()) {
				if (docketDet.getCheckListDetail().getCheckList().getChecklistType().equals(BpaConstants.INSPECTIONLOCATION))
					inspection.getDocketDetailLocList().add(docketDet);
				if (docketDet.getCheckListDetail().getCheckList().getChecklistType().equals(BpaConstants.INSPECTIONMEASUREMENT))
					inspection.getDocketDetailMeasumentList().add(docketDet);
				if (docketDet.getCheckListDetail().getCheckList().getChecklistType().equals(BpaConstants.INSPECTIONACCESS))
					inspection.getDocketDetailAccessList().add(docketDet);
				if (docketDet.getCheckListDetail().getCheckList().getChecklistType().equals(BpaConstants.INSPECTIONSURROUNDING))
					inspection.getDocketDetlSurroundingPlotList().add(docketDet);
				if (docketDet.getCheckListDetail().getCheckList().getChecklistType().equals(BpaConstants.INSPECTIONTYPEOFLAND))
					inspection.getDocketDetailLandTypeList().add(docketDet);
				if (docketDet.getCheckListDetail().getCheckList().getChecklistType()
							 .equals(BpaConstants.INSPECTIONPROPOSEDSTAGEWORK))
					inspection.getDocketDetailProposedWorkList().add(docketDet);
				if (docketDet.getCheckListDetail().getCheckList().getChecklistType()
							 .equals(BpaConstants.INSPECTIONWORKCOMPLETEDPERPLAN))
					inspection.getDocketDetailWorkAsPerPlanList().add(docketDet);
				if (docketDet.getCheckListDetail().getCheckList().getChecklistType()
							 .equals(BpaConstants.INSPECTIONHGTBUILDABUTROAD))
					inspection.getDocketDetailHgtAbuttRoadList().add(docketDet);
				if (docketDet.getCheckListDetail().getCheckList().getChecklistType()
							 .equals(BpaConstants.INSPECTIONAREALOC))
					inspection.getDocketDetailAreaLoc().add(docketDet);

				if (docketDet.getCheckListDetail().getCheckList().getChecklistType()
							 .equals(BpaConstants.INSPECTIONLENGTHOFCOMPOUNDWALL))
					inspection.getDocketDetailLengthOfCompWall().add(docketDet);
				if (docketDet.getCheckListDetail().getCheckList().getChecklistType()
							 .equals(BpaConstants.INSPECTIONNUMBEROFWELLS))
					inspection.getDocketDetailNumberOfWell().add(docketDet);
				if (docketDet.getCheckListDetail().getCheckList().getChecklistType()
							 .equals(BpaConstants.INSPECTIONSHUTTER))
					inspection.getDocketDetailShutter().add(docketDet);
				if (docketDet.getCheckListDetail().getCheckList().getChecklistType()
							 .equals(BpaConstants.INSPECTIONERECTIONOFTOWER))
					inspection.getDocketDetailErectionTower().add(docketDet);
				if (docketDet.getCheckListDetail().getCheckList().getChecklistType()
							 .equals(BpaConstants.INSPECTIONROOFCONVERSION))
					inspection.getDocketDetailRoofConversion().add(docketDet);
			}
		model.addAttribute("docketDetailLocList", inspection.getDocketDetailLocList());
		model.addAttribute("docketDetailMeasumentList", inspection.getDocketDetailMeasumentList());
		model.addAttribute("docketDetailAccessList", inspection.getDocketDetailAccessList());
		model.addAttribute("docketDetlSurroundingPlotList", inspection.getDocketDetlSurroundingPlotList());
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

	public Map<Long, String> prepareImagesForView(final Inspection inspection) {
		Map<Long, String> imageMap = new HashMap<>();
		if (!inspection.getInspectionSupportDocs().isEmpty())
			inspection.getInspectionSupportDocs().forEach(
					docketFileStoreObj -> {
						try {
							if (docketFileStoreObj != null) {
								final File file = fileStoreService.fetch(docketFileStoreObj.getFileStoreId(),
										BpaConstants.FILESTORE_MODULECODE);
								if (file != null)
									imageMap.put(docketFileStoreObj.getId(), Base64.getEncoder().encodeToString(
											FileCopyUtils.copyToByteArray(file)));
							}
						} catch (final IOException e) {
							LOGGER.error("Error while preparing the images for view", e);
						}
					});
		return imageMap;
	}

}
