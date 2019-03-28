/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2015>  eGovernments Foundation
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
package org.egov.bpa.web.controller.transaction;

import org.egov.bpa.master.entity.ServiceType;
import org.egov.bpa.transaction.entity.ApplicationStakeHolder;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.enums.ApplicantMode;
import org.egov.bpa.transaction.service.collection.GenericBillGeneratorService;
import org.egov.bpa.utils.BpaConstants;
import org.egov.commons.entity.Source;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.persistence.entity.enums.Gender;
import org.egov.infra.workflow.matrix.entity.WorkFlowMatrix;
import org.egov.pims.commons.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(value = "/application")
public class NewApplicationController extends BpaGenericApplicationController {

    private static final String NEWAPPLICATION_FORM = "newapplication-form";
    private static final String BPA_APPLICATION_RESULT = "bpa-application-result";
    private static final String MESSAGE = "message";
    @Autowired
    private GenericBillGeneratorService genericBillGeneratorService;

    @RequestMapping(value = "/newApplication-newform", method = GET)
    public String showNewApplicationForm(@ModelAttribute final BpaApplication bpaApplication, final Model model,
            final HttpServletRequest request) {
        if (request.getSession().getAttribute("cityname") != null)
            model.addAttribute("cityName", request.getSession().getAttribute("cityname"));
        return loadFormData(bpaApplication, model);
    }

    private String loadFormData(final BpaApplication bpaApplication, final Model model) {
        prepareFormData(model);
        bpaApplication.setApplicationDate(new Date());
        bpaApplication.setSource(Source.SYSTEM);
        bpaApplication.setApplicantMode(ApplicantMode.NEW);
        model.addAttribute("mode", "new");
        model.addAttribute("citizenOrBusinessUser", bpaUtils.logedInuseCitizenOrBusinessUser());
        model.addAttribute("genderList", Arrays.asList(Gender.values()));
        return NEWAPPLICATION_FORM;
    }

    @RequestMapping(value = "/newApplication-create", method = POST)
    public String createNewConnection(@Valid @ModelAttribute final BpaApplication bpaApplication,
            final BindingResult resultBinder, final RedirectAttributes redirectAttributes,
            final HttpServletRequest request, final Model model,
            final BindingResult errors) {
        String message;
        Long userPosition = null;
        String workFlowAction = request.getParameter("workFlowAction");
        if (bpaApplicationValidationService.validateBuildingDetails(bpaApplication, model)) {
            applicationBpaService.buildExistingAndProposedBuildingDetails(bpaApplication);
            return loadFormData(bpaApplication, model);
        }

        final WorkFlowMatrix wfmatrix = bpaUtils.getWfMatrixByCurrentState(bpaApplication.getIsOneDayPermitApplication(), bpaApplication.getStateType(), BpaConstants.WF_NEW_STATE,bpaApplication.getApplicationType().getName());
        if (wfmatrix != null)
			userPosition = bpaUtils.getUserPositionIdByZone(wfmatrix.getNextDesignation(),
					bpaUtils.getBoundaryForWorkflow(bpaApplication.getSiteDetail().get(0)).getId());
        if (userPosition == 0 || userPosition == null) {
            applicationBpaService.buildExistingAndProposedBuildingDetails(bpaApplication);
            return redirectOnValidationFailure(model);
        }

        if (!bpaApplicationValidationService.checkStakeholderIsValid(bpaApplication)) {
            applicationBpaService.buildExistingAndProposedBuildingDetails(bpaApplication);
            message = bpaApplicationValidationService.getValidationMessageForBusinessResgistration(bpaApplication);
            model.addAttribute("invalidStakeholder", message);
            return loadFormData(bpaApplication, model);
        }

        List<ApplicationStakeHolder> applicationStakeHolders = new ArrayList<>();
        ApplicationStakeHolder applicationStakeHolder = new ApplicationStakeHolder();
        applicationStakeHolder.setApplication(bpaApplication);
        applicationStakeHolder.setStakeHolder(bpaApplication.getStakeHolder().get(0).getStakeHolder());
        applicationStakeHolders.add(applicationStakeHolder);
        bpaApplication.setStakeHolder(applicationStakeHolders);
        applicationBpaService.persistOrUpdateApplicationDocument(bpaApplication);
        bpaApplication.setAdmissionfeeAmount(applicationBpaService.setAdmissionFeeAmountWithAmenities(
                bpaApplication.getServiceType().getId(), new ArrayList<ServiceType>()));
        if (bpaApplication.getOwner().getUser() != null && bpaApplication.getOwner().getUser().getId() == null)
            applicationBpaService.buildOwnerDetails(bpaApplication);

        bpaApplication.setCitizenAccepted(true);
        bpaApplication.setArchitectAccepted(true);

        BpaApplication bpaApplicationRes = applicationBpaService.createNewApplication(bpaApplication, workFlowAction);
        bpaUtils.createPortalUserinbox(bpaApplicationRes, Arrays.asList(bpaApplicationRes.getOwner().getUser(),
                bpaApplicationRes.getStakeHolder().get(0).getStakeHolder()), workFlowAction);

        // If There is no admission tax required to collect in first stage then forward record to next level user.
        if (!applicationBpaService.checkAnyTaxIsPendingToCollect(bpaApplicationRes)) {
            Position pos = null;
            if (bpaApplicationRes.getCurrentState() != null && bpaApplicationRes.getCurrentState().getOwnerPosition() != null)
                pos = bpaApplicationRes.getCurrentState().getOwnerPosition();

            bpaUtils.loadBoundary(bpaApplicationRes);
            message = messageSource.getMessage("msg.update.forward.registration", new String[] {
                    pos != null && pos.getDeptDesig() != null && pos.getDeptDesig().getDesignation() != null
                            ? pos.getDeptDesig().getDesignation().getName() : "",
                    bpaApplicationRes.getApplicationNumber() }, LocaleContextHolder.getLocale());
            model.addAttribute(MESSAGE, message);
            bpaSmsAndEmailService.sendSMSAndEmail(bpaApplicationRes,null,null);
            return BPA_APPLICATION_RESULT;
        }
        bpaSmsAndEmailService.sendSMSAndEmail(bpaApplicationRes,null,null);
        return genericBillGeneratorService.generateBillAndRedirectToCollection(bpaApplicationRes, model);
    }

    private String redirectOnValidationFailure(final Model model) {
        model.addAttribute("noJAORSAMessage", messageSource.getMessage("msg.official.not.exist", new String[]{
                ApplicationThreadLocals.getMunicipalityName()}, LocaleContextHolder.getLocale()));
        model.addAttribute("mode", "new");
        return NEWAPPLICATION_FORM;
    }
}