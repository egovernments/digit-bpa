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
package org.egov.bpa.service.rest;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.egov.bpa.contract.AirportAuthorityDetails;
import org.egov.bpa.contract.AirportAuthorityDocuments;
import org.egov.bpa.contract.ApplicationData;
import org.egov.bpa.contract.ApplicationDetails;
import org.egov.bpa.contract.CoOrdinates;
import org.egov.bpa.contract.SiteDetails;
import org.egov.bpa.transaction.entity.PermitNocApplication;
import org.egov.infra.config.core.ApplicationThreadLocals;
import org.egov.infra.exception.ApplicationRuntimeException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class NocRestService {

	public String buildAirportApplicationDetails(List<PermitNocApplication> nocApplications) {
		try {
			AirportAuthorityDetails airportDetails = new AirportAuthorityDetails();
			List<ApplicationDetails> applicationDetails = new ArrayList<>();
		for(PermitNocApplication permitNoc : nocApplications) {		
			
			CoOrdinates coOrdinate = new CoOrdinates();
			ApplicationData applicationData = new ApplicationData();
			ApplicationDetails applnDetails = new ApplicationDetails();
			SiteDetails siteDetails = new SiteDetails();
			AirportAuthorityDocuments documents = new AirportAuthorityDocuments();

			coOrdinate.setBuildingHeight(permitNoc.getBpaApplication().getBuildingDetail().get(0).getHeightFromGroundWithOutStairRoom().setScale(2,
                    BigDecimal.ROUND_HALF_UP).toString());			
			coOrdinate.setStructureNo(permitNoc.getBpaApplication().getSiteDetail().get(0).getPlotnumber());
			
			applicationData.setAuthority(ApplicationThreadLocals.getMunicipalityName());
			applicationData.setUniqueId(permitNoc.getBpaNocApplication().getNocApplicationNumber());
            applicationData.setApplicationDate(new SimpleDateFormat("dd-MM-yyyy").format(permitNoc.getBpaApplication().getApplicationDate()));
            applicationData.setApplicantNo(permitNoc.getBpaApplication().getOwner().getUser().getMobileNumber());
            applicationData.setApplicantName(permitNoc.getBpaApplication().getApplicantName());
            applicationData.setApplicantAddress(permitNoc.getBpaApplication().getOwner().getAddress());
            applicationData.setApplicantEmail(permitNoc.getBpaApplication().getOwner().getEmailId());
            applicationData.setOwnerName(permitNoc.getBpaApplication().getApplicantName());
            applicationData.setOwnerAddress(permitNoc.getBpaApplication().getOwner().getAddress());
            applicationData.setStructureType("Building");
            applicationData.setStructurePurpose(permitNoc.getBpaApplication().getOccupanciesName());
            applicationData.setSiteCity(permitNoc.getBpaApplication().getSiteDetail().get(0).getCitytown());
            applicationData.setSiteAddress(permitNoc.getBpaApplication().getSiteDetail().get(0).getStreetaddress1());
            applicationData.setSiteState(ApplicationThreadLocals.getStateName());
            applicationData.setPlotSize(permitNoc.getBpaApplication().getSiteDetail().get(0).getExtentinsqmts().toString());
            applicationData.setInAirportPremises("YES");
            applicationData.setPermissionTaken("YES");
    		siteDetails.setCoOrdinates(coOrdinate);

            applnDetails.setApplicationData(applicationData);
            applnDetails.setSiteDetails(siteDetails);
            applnDetails.setAirportAuthorityDocuments(documents);
            applicationDetails.add(applnDetails);
		}	
			airportDetails.setApplicationDetails(applicationDetails);
			return convertToXML(airportDetails);
		}catch (JAXBException e) {
            throw new ApplicationRuntimeException("Error occurred while fetching Noc Info", e);
        }
	}
	    
    private String convertToXML(final AirportAuthorityDetails airportAuthorityDetails) throws JAXBException {
	    try {	    		
		     JAXBContext jaxbContext = JAXBContext.newInstance(AirportAuthorityDetails.class);	        
		     Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		     jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		     StringWriter sw = new StringWriter();
		     jaxbMarshaller.marshal(airportAuthorityDetails, sw);
		     return sw.toString();
    	}catch (JAXBException e) {
            throw new ApplicationRuntimeException("Error occurred while fetching Noc Info", e);
        }
    }
}