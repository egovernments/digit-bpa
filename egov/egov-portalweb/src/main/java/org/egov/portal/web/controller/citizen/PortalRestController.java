package org.egov.portal.web.controller.citizen;

import java.util.ArrayList;
import java.util.List;

import org.egov.portal.entity.InboxRenderResponse;
import org.egov.portal.entity.PortalInbox;
import org.egov.portal.entity.PortalInboxHelper;
import org.egov.portal.entity.PortalInboxUser;
import org.egov.portal.service.PortalInboxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PortalRestController {

	@Autowired
	private PortalInboxUserService portalInboxUserService;

	@PostMapping(value = "/rest/fetch/servicesapplied", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public InboxRenderResponse fetchServicesApplied(@RequestParam final Long id) {
		List<PortalInboxUser> totalServicesAppliedList = portalInboxUserService.getPortalInboxByUserId(id);
		InboxRenderResponse inboxRenderResponse = new InboxRenderResponse();
		inboxRenderResponse.setPortalInboxHelper(getPortalInboxHelperList(totalServicesAppliedList));
		inboxRenderResponse.setTotalServices((long) totalServicesAppliedList.size());
		inboxRenderResponse.setCompletedServices(portalInboxUserService.getPortalInboxUserCountByResolved(id, true));
		inboxRenderResponse.setUnderScrutiny(portalInboxUserService.getPortalInboxUserCountByResolved(id, false));

		return inboxRenderResponse;
	}

	@PostMapping(value = "/rest/fetch/servicescompleted", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public InboxRenderResponse fetchServicesCompleted(@RequestParam final Long id) {
		List<PortalInboxUser> totalServicesCompletedList = portalInboxUserService.getPortalInboxByResolved(id, true);
		InboxRenderResponse inboxRenderResponse = new InboxRenderResponse();
		inboxRenderResponse.setPortalInboxHelper(getPortalInboxHelperList(totalServicesCompletedList));
		inboxRenderResponse.setTotalServices(portalInboxUserService.getPortalInboxUserCount(id));
		inboxRenderResponse.setCompletedServices((long) totalServicesCompletedList.size());
		inboxRenderResponse.setUnderScrutiny(portalInboxUserService.getPortalInboxUserCountByResolved(id, false));

		return inboxRenderResponse;
	}

	@PostMapping(value = "/rest/fetch/servicespending", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public InboxRenderResponse fetchServicesUnderScrutiny(@RequestParam final Long id) {
		List<PortalInboxUser> totalServicesPendingList = portalInboxUserService.getPortalInboxByResolved(id, false);
		InboxRenderResponse inboxRenderResponse = new InboxRenderResponse();
		inboxRenderResponse.setPortalInboxHelper(getPortalInboxHelperList(totalServicesPendingList));
		inboxRenderResponse.setTotalServices(portalInboxUserService.getPortalInboxUserCount(id));
		inboxRenderResponse.setCompletedServices(portalInboxUserService.getPortalInboxUserCountByResolved(id, true));
		inboxRenderResponse.setUnderScrutiny((long) totalServicesPendingList.size());

		return inboxRenderResponse;
	}

	public List<PortalInboxHelper> getPortalInboxHelperList(List<PortalInboxUser> servicesList) {
		List<PortalInboxHelper> portalInboxHelperList = new ArrayList<>();
		for (PortalInboxUser totalServicesApplied : servicesList) {
			PortalInboxHelper portalInboxHelper = new PortalInboxHelper();
			PortalInbox portalInbox = totalServicesApplied.getPortalInbox();
			portalInboxHelper.setApplicantName(portalInbox.getApplicantName());
			portalInboxHelper.setServiceRequestNo(portalInbox.getApplicationNumber());
			portalInboxHelper.setServiceRequestDate(portalInbox.getApplicationDate());
			portalInboxHelper.setServiceGroup(portalInbox.getModule().getName());
			portalInboxHelper.setServiceName(portalInbox.getServiceType());
			portalInboxHelper.setLink(portalInbox.getLink());
			portalInboxHelper.setStatus(portalInbox.getStatus());
			portalInboxHelper.setPendingAction(portalInbox.getState()!=null?portalInbox.getState().getNextAction():null);
			portalInboxHelper.setResolved(portalInbox.isResolved());
			portalInboxHelperList.add(portalInboxHelper);
		}
		return portalInboxHelperList;
	}
}
