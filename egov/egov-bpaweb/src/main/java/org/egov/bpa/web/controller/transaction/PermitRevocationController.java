package org.egov.bpa.web.controller.transaction;

import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_INIT_REVOKE;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_REVOKED;
import static org.egov.bpa.utils.BpaConstants.APPLICATION_STATUS_REVOKE_CANCELED;
import static org.egov.infra.utils.StringUtils.append;
import static org.springframework.http.MediaType.APPLICATION_PDF_VALUE;

import org.egov.bpa.master.entity.PermitRevocation;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.notice.impl.PermitRevocationFormat;
import org.egov.bpa.transaction.service.ApplicationBpaService;
import org.egov.bpa.transaction.service.PermitRevocationService;
import org.egov.bpa.utils.BpaUtils;
import org.egov.infra.custom.CustomImplProvider;
import org.egov.infra.reporting.engine.ReportOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/application")
public class PermitRevocationController {

    private static final String APPLICATION_SUCCESS = "application-success";
    private static final String PERMIT_REVOCATION = "permitRevocation";
    private static final String MESSAGE = "message";
    private static final String PDFEXTN = ".pdf";
    private static final String INLINE_FILENAME = "inline;filename=";
    private static final String CONTENT_DISPOSITION = "content-disposition";
    public static final String COMMON_ERROR = "common-error";

    @Autowired
    private PermitRevocationService permitRevocationService;
    @Autowired
    private ApplicationBpaService applicationBpaService;
    @Autowired
    protected ResourceBundleMessageSource messageSource;
    @Autowired
    private CustomImplProvider findImplementationBean;
    @Autowired
    private BpaUtils bpaUtils;

    @GetMapping("/initiate/revocation/new/{planPermissionNumber}")
    public String showPermitRevocationInitiateForm(final Model model, @PathVariable final String planPermissionNumber) {
        BpaApplication application = applicationBpaService.findByPermitNumber(planPermissionNumber);
        if (application != null && APPLICATION_STATUS_INIT_REVOKE.equals(application.getStatus().getCode())) {
            model.addAttribute(MESSAGE, "Permit revocation process is already initiated for the selected application.");
            return COMMON_ERROR;
        }
        PermitRevocation permitRevocation = new PermitRevocation();
        permitRevocation.setApplication(applicationBpaService.findByPermitNumber(planPermissionNumber));
        model.addAttribute(PERMIT_REVOCATION, permitRevocation);
        return "permit-revocation-initiate";
    }

    @PostMapping("/initiate/revocation/create")
    public String submitPermitRevocationInitiation(@ModelAttribute PermitRevocation permitRevocation, final Model model) {
        permitRevocationService.save(permitRevocation);
        permitRevocationService.sendSmsAndEmail(permitRevocation, null);
        bpaUtils.updatePortalUserinbox(permitRevocation.getApplication(), null);
        model.addAttribute(MESSAGE, messageSource.getMessage("msg.permit.revoke.initiate",
                new String[] { permitRevocation.getApplicationNumber() }, LocaleContextHolder.getLocale()));
        return APPLICATION_SUCCESS;
    }

    @GetMapping("/revocation/approval/edit/{planPermissionNumber}")
    public String permitRevocationApprovalForm(final Model model, @PathVariable final String planPermissionNumber) {
        BpaApplication application = applicationBpaService.findByPermitNumber(planPermissionNumber);
        if (application != null && (APPLICATION_STATUS_REVOKE_CANCELED.equals(application.getStatus().getCode())
                || APPLICATION_STATUS_REVOKED.equals(application.getStatus().getCode()))) {
            model.addAttribute(MESSAGE, "Permit revocation process is completed for the selected application.");
            return COMMON_ERROR;
        }
        PermitRevocation permitRevocation = permitRevocationService
                .findByPlanPermissionNumberAndRevocationApplnDate(planPermissionNumber);
        model.addAttribute(PERMIT_REVOCATION, permitRevocation);
        return "permit-revocation-approve";
    }

    @PostMapping("/revocation/approval/update")
    public String approvePermitRevocation(@ModelAttribute PermitRevocation permitRevocation, final Model model) {
        permitRevocationService.update(permitRevocation);
        if (!"Save Revocation".equalsIgnoreCase(permitRevocation.getWorkflowAction()))
            bpaUtils.updatePortalUserinbox(permitRevocation.getApplication(), null);
        if ("Cancel Revocation".equalsIgnoreCase(permitRevocation.getWorkflowAction())) {
            model.addAttribute(MESSAGE, messageSource.getMessage("msg.permit.revoke.cancel",
                    new String[] { permitRevocation.getApplicationNumber() }, LocaleContextHolder.getLocale()));
            permitRevocationService.sendSmsAndEmail(permitRevocation, null);
        } else if ("Approve Revocation".equalsIgnoreCase(permitRevocation.getWorkflowAction())) {
            PermitRevocationFormat permitRevocationFormat = (PermitRevocationFormat) findImplementationBean
                    .find(PermitRevocationFormat.class, findImplementationBean.getCityDetails());
            permitRevocationService.sendSmsAndEmail(permitRevocation, permitRevocationFormat.generateNotice(permitRevocation));
            return "redirect:/application/revocation/generateorder/" + permitRevocation.getRevocationNumber();
        } else
            model.addAttribute(MESSAGE, messageSource.getMessage("msg.permit.revoke.save",
                    new String[] { permitRevocation.getApplicationNumber() }, LocaleContextHolder.getLocale()));
        return APPLICATION_SUCCESS;
    }

    @GetMapping("/revocation/details/view/{applicationNumber}")
    public String viewRevocationDetails(final Model model, @PathVariable final String applicationNumber) {
        PermitRevocation permitRevocation = permitRevocationService.findByApplicationNumber(applicationNumber);
        model.addAttribute(PERMIT_REVOCATION, permitRevocation);
        return "view-permit-revocation-details";
    }

    @GetMapping(value = "/revocation/generateorder/{revocationNumber}", produces = APPLICATION_PDF_VALUE)
    @ResponseBody
    public ResponseEntity<InputStreamResource> generateRevocatioOrder(final Model model,
            @PathVariable final String revocationNumber) {
        PermitRevocation permitRevocation = permitRevocationService.findByRevocationNumber(revocationNumber);
        PermitRevocationFormat permitRevocationFormatFeature = (PermitRevocationFormat) findImplementationBean
                .find(PermitRevocationFormat.class, findImplementationBean.getCityDetails());
        ReportOutput reportOutput = permitRevocationFormatFeature
                .generateNotice(permitRevocation);
        return getFileAsResponseEntity(revocationNumber, reportOutput, "permit_revocation_");
    }

    private ResponseEntity<InputStreamResource> getFileAsResponseEntity(String applicationNumber, ReportOutput reportOutput,
            String prefixFileName) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_PDF)
                .cacheControl(CacheControl.noCache())
                .contentLength(reportOutput.getReportOutputData().length)
                .header(CONTENT_DISPOSITION, String.format(INLINE_FILENAME,
                        append(prefixFileName, applicationNumber) + PDFEXTN))
                .body(new InputStreamResource(reportOutput.asInputStream()));
    }

}
