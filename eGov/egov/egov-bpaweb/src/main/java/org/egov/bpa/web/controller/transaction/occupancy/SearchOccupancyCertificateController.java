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
package org.egov.bpa.web.controller.transaction.occupancy;

import org.egov.bpa.transaction.entity.dto.SearchBpaApplicationForm;
import org.egov.bpa.transaction.service.oc.SearchOCService;
import org.egov.bpa.web.controller.adaptor.SearchBpaApplicationAdaptor;
import org.egov.bpa.web.controller.transaction.BpaGenericApplicationController;
import org.egov.eis.entity.Employee;
import org.egov.eis.entity.Jurisdiction;
import org.egov.eis.service.EmployeeService;
import org.egov.infra.admin.master.entity.Boundary;
import org.egov.infra.admin.master.entity.BoundaryType;
import org.egov.infra.admin.master.service.BoundaryService;
import org.egov.infra.admin.master.service.BoundaryTypeService;
import org.egov.infra.admin.master.service.CrossHierarchyService;
import org.egov.infra.web.support.ui.DataTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.egov.bpa.utils.BpaConstants.BOUNDARY_TYPE_CITY;
import static org.egov.bpa.utils.BpaConstants.REVENUE_HIERARCHY_TYPE;
import static org.egov.bpa.utils.BpaConstants.WARD;

@Controller
@RequestMapping(value = "/application/occupancy-certificate")
public class SearchOccupancyCertificateController extends BpaGenericApplicationController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private BoundaryTypeService boundaryTypeService;
    @Autowired
    private CrossHierarchyService crossHierarchyService;
    @Autowired
    private BoundaryService boundaryService;
    @Autowired
    private SearchOCService searchOCService;

    @GetMapping("/view/{applicationNumber}")
    public String editOccupancyCertificateApplication(@PathVariable final String applicationNumber, final Model model, final HttpServletRequest request) {
        return null;
    }

    @RequestMapping(value = "/search/document-scrutiny", method = RequestMethod.GET)
    public String showDocumentScrutinyPendingRecords(final Model model) {
        List<Boundary> employeeMappedZone = new ArrayList<>();
        List<Boundary> mappedElectionWard = new ArrayList<>();
        Set<Boundary> electionWards = new HashSet<>();
        BoundaryType revenueType = boundaryTypeService.getBoundaryTypeByNameAndHierarchyTypeName(WARD, REVENUE_HIERARCHY_TYPE);
        final Employee employee = employeeService.getEmployeeById(securityUtils.getCurrentUser().getId());
        for (Jurisdiction jurisdiction : employee.getJurisdictions()) {
            if (!BOUNDARY_TYPE_CITY.equals(jurisdiction.getBoundaryType().getName())) {
                mappedElectionWard.add(jurisdiction.getBoundary());
                break;
            }
        }
        List<Boundary> revenueWards = crossHierarchyService.getParentBoundaryByChildBoundaryAndParentBoundaryType(mappedElectionWard.get(0).getId(), revenueType.getId());
        employeeMappedZone.add(revenueWards.get(0).getParent());
        model.addAttribute("searchBpaApplicationForm", new SearchBpaApplicationForm());
        List<Boundary> revWards = boundaryService.getActiveChildBoundariesByBoundaryId(revenueWards.get(0).getParent().getId());
        for (Boundary revenue : revWards) {
            electionWards.addAll(crossHierarchyService
                    .findChildBoundariesByParentBoundaryIdParentBoundaryTypeAndChildBoundaryType(WARD,
                            REVENUE_HIERARCHY_TYPE, WARD, revenue.getId()));
        }
        model.addAttribute("employeeMappedZone", employeeMappedZone);
        model.addAttribute("mappedRevenueBoundries", revWards);
        model.addAttribute("mappedElectionBoundries", electionWards);
        return "search-document-scrutiny-oc";
    }

    @RequestMapping(value = "/search/document-scrutiny", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String searchDocumentScrutinyPendingRecords(@ModelAttribute final SearchBpaApplicationForm searchBpaApplicationForm) {
        return new DataTable<>(searchOCService.searchForDocumentScrutinyPending(searchBpaApplicationForm),
                searchBpaApplicationForm.draw())
                .toJson(SearchBpaApplicationAdaptor.class);
    }


}
