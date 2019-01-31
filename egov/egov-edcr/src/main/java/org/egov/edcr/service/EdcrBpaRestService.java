package org.egov.edcr.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.egov.infra.web.utils.WebUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("edcrBpaRestService")
public class EdcrBpaRestService {

	private static final String BPACHECKDEMOND = "%s/bpa/rest/stakeholder/check/demand-pending/{userId}";
	private static final String REDIRECTTOCOLLECTION = "%s/bpa/application/stakeholder/generate-bill/{userId}";

	public Boolean checkAnyTaxIsPendingToCollectForStakeHolder(final Long userId, final HttpServletRequest request) {
		final RestTemplate restTemplate = new RestTemplate();

		final String url = String.format(BPACHECKDEMOND, WebUtils.extractRequestDomainURL(request, false));

		Map<String, Boolean> checkPending = restTemplate.getForObject(url, Map.class, userId);
		/*if(checkPending.get("pending").equals(true)){
		return  true;
		}
		return false;*/
		return checkPending.get("pending") != null ? checkPending.get("pending") : false;
	}
	
	public String generateBillAndRedirectToCollection(final Long userId, final HttpServletRequest request) {
		final RestTemplate restTemplate = new RestTemplate();

		final String url = String.format(REDIRECTTOCOLLECTION, WebUtils.extractRequestDomainURL(request, false));

		String collectionScreen = restTemplate.getForObject(url, String.class, userId);
		return collectionScreen;		
	}
}
