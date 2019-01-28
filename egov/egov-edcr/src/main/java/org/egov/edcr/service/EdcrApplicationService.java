package org.egov.edcr.service;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.egov.edcr.utility.DcrConstants.FILESTORE_MODULECODE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.egov.bpa.entity.es.BpaIndex;
import org.egov.bpa.entity.es.StakeHolderIndex;
import org.egov.edcr.entity.ApplicationType;
import org.egov.edcr.entity.EdcrApplication;
import org.egov.edcr.entity.EdcrApplicationDetail;
import org.egov.edcr.entity.Plan;
import org.egov.edcr.entity.SearchBuildingPlanScrutinyForm;
import org.egov.edcr.repository.EdcrApplicationDetailRepository;
import org.egov.edcr.repository.EdcrApplicationRepository;
import org.egov.edcr.service.es.EdcrIndexService;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.config.persistence.datasource.routing.annotation.ReadOnly;
import org.egov.infra.exception.ApplicationRuntimeException;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.filestore.service.FileStoreService;
import org.egov.infra.security.utils.SecurityUtils;
import org.egov.infra.utils.ApplicationNumberGenerator;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
public class EdcrApplicationService {
	private static final String RESUBMIT_SCRTNY = "Resubmit Plan Scrutiny";
	private static final String NEW_SCRTNY = "New Plan Scrutiny";
	public static final String ULB_NAME = "ulbName";
	public static final String ABORTED = "Aborted";
	private static Logger LOG = Logger.getLogger(EdcrApplicationService.class);
	@Autowired
	protected SecurityUtils securityUtils;

	@Autowired
	private EdcrApplicationRepository edcrApplicationRepository;

	@Autowired
	private EdcrApplicationDetailRepository edcrApplicationDetailRepository;

	@Autowired
	private PlanService planService;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private FileStoreService fileStoreService;

	@Autowired
	private PortalInetgrationService portalInetgrationService;

	@Autowired
	private ApplicationNumberGenerator applicationNumberGenerator;

	@Autowired
	private EdcrIndexService edcrIndexService;

	@Autowired
	private EdcrApplicationDetailService edcrApplicationDetailService;

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	public Session getCurrentSession() {
		return entityManager.unwrap(Session.class);
	}

	@Transactional
	public EdcrApplication create(final EdcrApplication edcrApplication) {

		edcrApplication.setApplicationDate(new Date());
		edcrApplication.setApplicationNumber(applicationNumberGenerator.generate());
		edcrApplication.setSavedDxfFile(saveDXF(edcrApplication));
		edcrApplication.setStatus(ABORTED);
		// edcrApplication.setPlanInformation(edcrApplication.getPlanInformation());

		edcrApplicationRepository.save(edcrApplication);

		edcrIndexService.updateIndexes(edcrApplication, NEW_SCRTNY);
		// portalInetgrationService.createPortalUserinbox(edcrApplication,
		// Arrays.asList(securityUtils.getCurrentUser()));

		Plan planDetail = callDcrProcess(edcrApplication, NEW_SCRTNY);
		portalInetgrationService.updatePortalUserinbox(edcrApplication, securityUtils.getCurrentUser());
		edcrIndexService.updateIndexes(edcrApplication, NEW_SCRTNY);

		return edcrApplication;
	}

	@Transactional
	public EdcrApplication update(final EdcrApplication edcrApplication) {
		edcrApplication.setSavedDxfFile(saveDXF(edcrApplication));
		edcrApplication.setStatus(ABORTED);
		Plan unsavedPlanDetail = edcrApplication.getEdcrApplicationDetails().get(0).getPlan();
		EdcrApplication applicationRes = edcrApplicationRepository.save(edcrApplication);
		edcrApplication.getEdcrApplicationDetails().get(0).setPlan(unsavedPlanDetail);

		edcrIndexService.updateIndexes(edcrApplication, RESUBMIT_SCRTNY);
		portalInetgrationService.updatePortalUserinbox(applicationRes, securityUtils.getCurrentUser());

		Plan planDetail = callDcrProcess(edcrApplication, RESUBMIT_SCRTNY);

		portalInetgrationService.updatePortalUserinbox(applicationRes, securityUtils.getCurrentUser());
		return applicationRes;
	}

	private Plan callDcrProcess(EdcrApplication edcrApplication, String applicationType) {
		Plan planDetail = new Plan();
		try {
			planDetail = planService.process(edcrApplication, applicationType);
			updateFile(planDetail, edcrApplication);
			edcrApplicationDetailService.saveAll(edcrApplication.getEdcrApplicationDetails());
		} catch (Exception e) {
			LOG.error("Error occurred, when processing plan scrutiny!!!!!", e);
		}
		return planDetail;
	}

	private File saveDXF(EdcrApplication edcrApplication) {
		FileStoreMapper fileStoreMapper = addToFileStore(edcrApplication.getDxfFile());
		File dxfFile = fileStoreService.fetch(fileStoreMapper.getFileStoreId(), FILESTORE_MODULECODE);
		planService.buildDocuments(edcrApplication, fileStoreMapper, null, null);
		List<EdcrApplicationDetail> edcrApplicationDetails = edcrApplication.getEdcrApplicationDetails();
		edcrApplicationDetails.get(0).setStatus(ABORTED);
		edcrApplication.setEdcrApplicationDetails(edcrApplicationDetails);
		return dxfFile;

	}

	private FileStoreMapper addToFileStore(final MultipartFile file) {
		FileStoreMapper fileStoreMapper;
		try {
			fileStoreMapper = fileStoreService.store(file.getInputStream(), file.getOriginalFilename(),
					file.getContentType(), FILESTORE_MODULECODE);
		} catch (final IOException e) {
			throw new ApplicationRuntimeException("Error occurred, while getting input stream!!!!!", e);
		}
		return fileStoreMapper;
	}

	public List<EdcrApplication> findAll() {
		return edcrApplicationRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
	}

	public EdcrApplication findOne(Long id) {
		return edcrApplicationRepository.findOne(id);
	}

	public EdcrApplication findByApplicationNo(String appNo) {
		return edcrApplicationRepository.findByApplicationNumber(appNo);
	}

	public EdcrApplication findByApplicationNoAndType(String applnNo, ApplicationType type) {
		return edcrApplicationRepository.findByApplicationNumberAndApplicationType(applnNo, type);
	}

	public EdcrApplication findByPlanPermitNumber(String permitNo) {
		return edcrApplicationRepository.findByPlanPermitNumber(permitNo);
	}

	public List<EdcrApplication> search(EdcrApplication edcrApplication) {
		return edcrApplicationRepository.findAll();
	}

	@ReadOnly
	public Page<SearchBuildingPlanScrutinyForm> planScrutinyPagedSearch(SearchBuildingPlanScrutinyForm searchRequest) {
		final Pageable pageable = new PageRequest(searchRequest.pageNumber(), searchRequest.pageSize(),
				searchRequest.orderDir(), searchRequest.orderBy());
		List<SearchBuildingPlanScrutinyForm> searchResults = new ArrayList<>();
		if (searchRequest.getBpaApplicationNumber() != null) {
			Page<BpaIndex> bpaIndexList = getSearchResultForBpaApplicationNumber(searchRequest);
			if (!bpaIndexList.getContent().isEmpty())
				searchRequest.setBuildingPlanScrutinyNumber(bpaIndexList.getContent().get(0).geteDcrNumber());
			else
				searchRequest.setBuildingPlanScrutinyNumber("");
		}
		Page<EdcrApplicationDetail> dcrApplications = edcrApplicationDetailRepository
				.findAll(DcrReportSearchSpec.searchReportsSpecification(searchRequest), pageable);
		for (EdcrApplicationDetail applicationDetail : dcrApplications)
			searchResults.add(buildResponseAsPerForm(applicationDetail));
		return new PageImpl<>(searchResults, pageable, dcrApplications.getTotalElements());
	}

	private SearchBuildingPlanScrutinyForm buildResponseAsPerForm(EdcrApplicationDetail applicationDetail) {
		SearchBuildingPlanScrutinyForm planScrtnyFrm = new SearchBuildingPlanScrutinyForm();
		EdcrApplication application = applicationDetail.getApplication();
		planScrtnyFrm.setApplicationNumber(application.getApplicationNumber());
		planScrtnyFrm.setApplicationDate(application.getApplicationDate());
		planScrtnyFrm.setApplicantName(application.getApplicantName());
		planScrtnyFrm.setBuildingPlanScrutinyNumber(applicationDetail.getDcrNumber());
		planScrtnyFrm.setUploadedDateAndTime(applicationDetail.getCreatedDate());
		if (applicationDetail.getDxfFileId() != null)
			planScrtnyFrm.setDxfFileStoreId(applicationDetail.getDxfFileId().getFileStoreId());
		if (applicationDetail.getDxfFileId() != null)
			planScrtnyFrm.setDxfFileName(applicationDetail.getDxfFileId().getFileName());
		if (applicationDetail.getReportOutputId() != null)
			planScrtnyFrm.setReportOutputFileStoreId(applicationDetail.getReportOutputId().getFileStoreId());
		if (applicationDetail.getReportOutputId() != null)
			planScrtnyFrm.setReportOutputFileName(applicationDetail.getReportOutputId().getFileName());
		planScrtnyFrm.setStakeHolderId(application.getCreatedBy().getId());
		if (planScrtnyFrm.getStakeHolderId() != null) {
			List<StakeHolderIndex> stakeHolderIndexes = getSearchResultForStakeholder(planScrtnyFrm);
			if (!stakeHolderIndexes.isEmpty()) {
				StakeHolderIndex stakeHolderIndex = stakeHolderIndexes.get(0);
				if (stakeHolderIndex.getStakeHolderName() != null) {
					planScrtnyFrm.setBuildingLicenceeName(stakeHolderIndex.getStakeHolderName());
					planScrtnyFrm.setBuildingLicenceeType(stakeHolderIndex.getStakeHolderType());
				}
			}
		}
		if (isNotBlank(applicationDetail.getStatus())) {
			planScrtnyFrm.setStatus(applicationDetail.getStatus());
			if (planScrtnyFrm.getStatus().equals("Accepted")
					&& isNotBlank(planScrtnyFrm.getBuildingPlanScrutinyNumber())) {
				List<BpaIndex> bpaIndexList = getSearchResult(planScrtnyFrm);
				if (!bpaIndexList.isEmpty()) {
					BpaIndex bpaIndex = bpaIndexList.get(0);
					if (bpaIndex.getApplicationNumber() != null)
						planScrtnyFrm.setBpaApplicationNumber(bpaIndex.getApplicationNumber());
					if (bpaIndex.getPlanPermissionNumber() != null)
						planScrtnyFrm.setPermitNumber(bpaIndex.getPlanPermissionNumber());
				}
			}
		}
		return planScrtnyFrm;
	}

	private BoolQueryBuilder getQueryFilterForStakeHolder(final SearchBuildingPlanScrutinyForm planScrtnyFrm) {
		return QueryBuilders.boolQuery()
				.filter(QueryBuilders.matchQuery(ULB_NAME, ApplicationThreadLocals.getCityName()))
				.filter(QueryBuilders.matchQuery("id", planScrtnyFrm.getStakeHolderId()));
	}

	private List<StakeHolderIndex> getSearchResultForStakeholder(SearchBuildingPlanScrutinyForm planScrtnyFrm) {
		final BoolQueryBuilder boolQuery = getQueryFilterForStakeHolder(planScrtnyFrm);
		return getStakeHolderSearchResultByBoolQueryList(boolQuery);
	}

	private List<StakeHolderIndex> getStakeHolderSearchResultByBoolQueryList(BoolQueryBuilder boolQuery) {
		final SearchQuery searchQuery = new NativeSearchQueryBuilder().withIndices("stakeholder").withQuery(boolQuery)
				.withSort(new FieldSortBuilder("id").order(SortOrder.DESC)).build();
		return elasticsearchTemplate.queryForList(searchQuery, StakeHolderIndex.class);
	}

	private List<BpaIndex> getSearchResult(final SearchBuildingPlanScrutinyForm planScrutinyForm) {
		final BoolQueryBuilder boolQuery = getQueryFilterForEdcrNumber(planScrutinyForm);
		return getEdcrNumberSearchResultByBoolQuery(boolQuery);
	}

	private BoolQueryBuilder getQueryFilterForEdcrNumber(final SearchBuildingPlanScrutinyForm planScrutinyForm) {
		return QueryBuilders.boolQuery()
				.filter(QueryBuilders.matchQuery(ULB_NAME, ApplicationThreadLocals.getCityName()))
				.filter(QueryBuilders.matchQuery("eDcrNumber", planScrutinyForm.getBuildingPlanScrutinyNumber()));
	}

	private List<BpaIndex> getEdcrNumberSearchResultByBoolQuery(final BoolQueryBuilder boolQuery) {
		final SearchQuery searchQuery = new NativeSearchQueryBuilder().withIndices("bpa").withQuery(boolQuery)
				.withSort(new FieldSortBuilder("id").order(SortOrder.DESC)).build();
		return elasticsearchTemplate.queryForList(searchQuery, BpaIndex.class);
	}

	private Page<BpaIndex> getSearchResultForBpaApplicationNumber(SearchBuildingPlanScrutinyForm srchPlnScrtny) {
		final BoolQueryBuilder boolQuery = getQueryFilterForBpaApplicationNumber(srchPlnScrtny);
		return getBpaApplicationNumberSearchResultByBoolQuery(boolQuery, srchPlnScrtny);
	}

	private Page<BpaIndex> getBpaApplicationNumberSearchResultByBoolQuery(BoolQueryBuilder boolQuery,
			SearchBuildingPlanScrutinyForm searchRequest) {
		final SearchQuery searchQuery = new NativeSearchQueryBuilder().withIndices("bpa").withQuery(boolQuery)
				.withPageable(new PageRequest(searchRequest.pageNumber(), searchRequest.pageSize(),
						searchRequest.orderDir(), searchRequest.orderBy()))
				.withSort(new FieldSortBuilder("id").order(SortOrder.DESC)).build();
		return elasticsearchTemplate.queryForPage(searchQuery, BpaIndex.class);
	}

	private BoolQueryBuilder getQueryFilterForBpaApplicationNumber(SearchBuildingPlanScrutinyForm srchPlnScrtny) {
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
				.filter(QueryBuilders.matchQuery(ULB_NAME, ApplicationThreadLocals.getCityName()));
		if (isNotBlank(srchPlnScrtny.getBpaApplicationNumber()) && boolQuery != null) {
			boolQuery = boolQuery
					.filter(QueryBuilders.matchQuery("applicationNumber", srchPlnScrtny.getBpaApplicationNumber()));
		}
		return boolQuery;
	}

	private static String readFile(File srcFile) {
		String fileAsString = null;

		try (InputStream is = new FileInputStream(srcFile);
				BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
			String line = br.readLine();
			StringBuilder sb = new StringBuilder();
			while (line != null) {
				sb.append(line).append("\n");
				line = br.readLine();
			}
			fileAsString = sb.toString();
		} catch (IOException e) {
			LOG.error("Error occurred when reading file!!!!!", e);
		}
		return fileAsString;
	}

	private void updateFile(Plan pl, EdcrApplication edcrApplication) {
		String readFile = readFile(edcrApplication.getSavedDxfFile());
		String replace = readFile.replace("ENTITIES", "ENTITIES\n0\n" + pl.getAdditionsToDxf());
		String newFile = edcrApplication.getDxfFile().getOriginalFilename().replace(".dxf", "_system_scrutinized.dxf");
		File f = new File(newFile);
		try (FileOutputStream fos = new FileOutputStream(f)) {
			if (!f.exists())
				f.createNewFile();
			fos.write(replace.getBytes());
			fos.flush();
			FileStoreMapper fileStoreMapper = fileStoreService.store(f, f.getName(),
					edcrApplication.getDxfFile().getContentType(), FILESTORE_MODULECODE);
			edcrApplication.getEdcrApplicationDetails().get(0).setScrutinizedDxfFileId(fileStoreMapper);
		} catch (IOException e) {
			LOG.error("Error occurred when reading file!!!!!", e);
		}
	}

}