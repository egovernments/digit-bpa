package org.egov.edcr.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.egov.common.entity.edcr.Plan;
import org.egov.common.entity.edcr.PlanFeature;
import org.egov.common.entity.edcr.PlanInformation;
import org.egov.edcr.entity.EdcrApplication;
import org.egov.edcr.entity.EdcrApplicationDetail;
import org.egov.edcr.feature.FeatureProcess;
import org.egov.edcr.utility.DcrConstants;
import org.egov.infra.custom.CustomImplProvider;
import org.egov.infra.filestore.entity.FileStoreMapper;
import org.egov.infra.filestore.service.FileStoreService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.egov.infra.custom.CustomImplProvider;

@Service
public class PlanService {
    private Logger LOG = Logger.getLogger(PlanService.class);
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private PlanFeatureService featureService;
    @Autowired
    private FileStoreService fileStoreService;
    @Autowired
    private CustomImplProvider specificRuleService;
    @Autowired
    private EdcrApplicationDetailService edcrApplicationDetailService;

    @Autowired
    private ExtractService extractService;

    public Plan process(EdcrApplication dcrApplication, String applicationType) {

        Plan plan = extractService.extract(dcrApplication.getSavedDxfFile(), featureService.getFeatures());

        Map<String, String> cityDetails = specificRuleService.getCityDetails();
        plan = applyRules(plan, cityDetails);

        InputStream reportStream = generateReport(plan, dcrApplication);

        saveOutputReport(dcrApplication, reportStream, plan);

        return plan;
    }

    public void savePlanDetail(Plan plan, EdcrApplicationDetail detail) {
        /*
         * if (LOG.isInfoEnabled()) LOG.info("*************Before serialization******************"); File f = new
         * File("plandetail.txt"); try (FileOutputStream fos = new FileOutputStream(f); ObjectOutputStream oos = new
         * ObjectOutputStream(fos)) { oos.writeObject(plan); detail.setPlanDetailFileStore( fileStoreService.store(f, f.getName(),
         * "text/plain", DcrConstants.APPLICATION_MODULE_TYPE)); oos.flush(); } catch (IOException e) {
         * LOG.error("Unable to serialize!!!!!!", e); } if (LOG.isInfoEnabled())
         * LOG.info("*************Completed serialization******************");
         */
    }

    private Plan applyRules(Plan plan, Map<String, String> cityDetails) {
        for (PlanFeature ruleClass : featureService.getFeatures()) {
            FeatureProcess rule = (FeatureProcess) specificRuleService.find(ruleClass.getRuleClass(), cityDetails);
            rule.process(plan);
        }
        return plan;
    }

    private InputStream generateReport(Plan plan, EdcrApplication dcrApplication) {

        PlanReportService service = getReportService();
        InputStream reportStream = service.generateReport(plan, dcrApplication);
        return reportStream;
    }

    private PlanReportService getReportService() {
        Object bean = null;
        String beanName = "PlanReportService";
        PlanReportService service = null;
        try {
            beanName = beanName.substring(0, 1).toLowerCase() + beanName.substring(1);
            applicationContext.getBean(beanName);
            bean = applicationContext.getBean(beanName);
            service = (PlanReportService) bean;
            if (service == null) {
                LOG.error("No Service Found for " + beanName);
            }
        } catch (BeansException e) {
            LOG.error("No Bean Defined for the Rule " + beanName);
        }
        return service;
    }

    private FeatureProcess getRuleBean(String beanName) {
        Object bean = null;
        FeatureProcess rule = null;
        try {
            beanName = beanName.substring(0, 1).toLowerCase() + beanName.substring(1);
            // applicationContext.getBean(beanName);
            bean = applicationContext.getBean(beanName);

            rule = (FeatureProcess) bean;
            if (bean == null) {
                LOG.error("No Service Found for " + beanName);
            }
        } catch (BeansException e) {
            LOG.error("No Bean Defined for the Rule " + beanName);
        }
        return rule;
    }

    @Transactional
    public void saveOutputReport(EdcrApplication edcrApplication, InputStream reportOutputStream,
            Plan plan) {

        List<EdcrApplicationDetail> edcrApplicationDetails = edcrApplicationDetailService
                .fingByDcrApplicationId(edcrApplication.getId());
        final String fileName = edcrApplication.getApplicationNumber() + "-v" + edcrApplicationDetails.size() + ".pdf";

        final FileStoreMapper fileStoreMapper = fileStoreService.store(reportOutputStream, fileName, "application/pdf",
                DcrConstants.FILESTORE_MODULECODE);

        buildDocuments(edcrApplication, null, fileStoreMapper, plan);

        PlanInformation planInformation = plan.getPlanInformation();

        // planinfoService.save(planInformation);
        edcrApplication.getEdcrApplicationDetails().get(0).setPlanInformation(planInformation);
        edcrApplicationDetailService.saveAll(edcrApplication.getEdcrApplicationDetails());
    }

    public void buildDocuments(EdcrApplication edcrApplication, FileStoreMapper dxfFile, FileStoreMapper reportOutput,
            Plan plan) {

        if (dxfFile != null) {
            EdcrApplicationDetail edcrApplicationDetail = new EdcrApplicationDetail();

            edcrApplicationDetail.setDxfFileId(dxfFile);
            edcrApplicationDetail.setApplication(edcrApplication);
            for (EdcrApplicationDetail edcrApplicationDetail1 : edcrApplication.getEdcrApplicationDetails()) {
                edcrApplicationDetail.setPlan(edcrApplicationDetail1.getPlan());
            }
            List<EdcrApplicationDetail> edcrApplicationDetails = new ArrayList<>();
            edcrApplicationDetails.add(edcrApplicationDetail);
            edcrApplication.setSavedEdcrApplicationDetail(edcrApplicationDetail);
            edcrApplication.setEdcrApplicationDetails(edcrApplicationDetails);
        }

        if (reportOutput != null) {
            EdcrApplicationDetail edcrApplicationDetail = edcrApplication.getEdcrApplicationDetails().get(0);

            if (plan.getEdcrPassed()) {
                edcrApplicationDetail.setStatus("Accepted");
                edcrApplication.setStatus("Accepted");
            } else {
                edcrApplicationDetail.setStatus("Not Accepted");
                edcrApplication.setStatus("Not Accepted");
            }
            edcrApplicationDetail.setCreatedDate(new Date());
            edcrApplicationDetail.setReportOutputId(reportOutput);
            List<EdcrApplicationDetail> edcrApplicationDetails = new ArrayList<>();
            edcrApplicationDetails.add(edcrApplicationDetail);
            savePlanDetail(plan, edcrApplicationDetail);

            /*
             * if (plan.getEdcrPdfDetails() != null && plan.getEdcrPdfDetails().size() > 0) { for (EdcrPdfDetail edcrPdfDetail :
             * plan.getEdcrPdfDetails()) { edcrPdfDetail.setEdcrApplicationDetail(edcrApplicationDetail); }
             * edcrPdfDetailService.saveAll(plan.getEdcrPdfDetails()); }
             */
            edcrApplication.setEdcrApplicationDetails(edcrApplicationDetails);
        }
    }

}