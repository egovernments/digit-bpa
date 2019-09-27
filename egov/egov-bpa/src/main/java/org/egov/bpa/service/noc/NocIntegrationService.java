package org.egov.bpa.service.noc;

import java.util.List;

import org.apache.log4j.Logger;
import org.egov.bpa.master.entity.BpaApplicationType;
import org.egov.bpa.master.entity.NocConfig;
import org.egov.bpa.master.service.NocConfigService;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.NoObjectionCertificate;
import org.egov.bpa.transaction.entity.NocList;
import org.egov.bpa.transaction.entity.enums.NocIntegrationType;
import org.egov.bpa.transaction.entity.enums.NocStatus;
import org.egov.infra.custom.CustomImplProvider;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NocIntegrationService {
	@Autowired
	private NocConfigService nocConfigService;
	@Autowired
	private CustomImplProvider customImplProvider;
	private static final Logger LOG=Logger.getLogger(NocIntegrationService.class);

	public Boolean pushNocRequest(BpaApplication application) {
		Boolean overAllStatus = true;

		List<NocConfig> nocAutoConfigs = nocConfigService
				.findByApplicationTypeAndIntegrationType(BpaApplicationType.PLAN_PERMIT, NocIntegrationType.API);
		for (NocConfig nocConfig : nocAutoConfigs) {
			Boolean status = false;
			String serviceName = nocConfig.getNocDepartment().getCode();
			serviceName = serviceName + "Service";
			NocService service = (NocService) customImplProvider.find(serviceName, null);
			if(service==null)
			{
				LOG.fatal("NoC service not found with name  "+serviceName);
			}else
			{
			status = service.push(application);
			}
		   
			if (status == false)
				overAllStatus = false;
	/*		NoObjectionCertificate noco=new NoObjectionCertificate();
			noco.
			application.getNocList().add
*/
		}

		List<NocConfig> nocManualConfigs = nocConfigService
				.findByApplicationTypeAndIntegrationType(BpaApplicationType.PLAN_PERMIT, NocIntegrationType.ONBOARD);
		for (NocConfig nocConfig : nocAutoConfigs) {
			Boolean status = false;
			String serviceName = nocConfig.getNocDepartment().getCode();
			serviceName = serviceName + "Service";
			NocService service = (NocService) customImplProvider.find(serviceName, null);
			if(service==null)
			{
				LOG.fatal("NoC service not found with name  "+serviceName);
			}else
			{
			status = service.push(application);
			
			}
			if (status == false)
				overAllStatus = false;
		}

		return overAllStatus;

	}

	public Boolean updateNocStatus(BpaApplication application) {
		Boolean overAllStatus = true;
		if (application != null) {

			NocList nocList = application.getNocList();
			for (NoObjectionCertificate noc : nocList.getNocList()) {
				if (!noc.getNocConfig().getStatusByPush()) {
					if (!noc.getStatus().equals(NocStatus.APPROVED) || noc.getStatus().equals(NocStatus.REJECTED)) {
						Boolean status = false;
						String serviceName = noc.getNocConfig().getNocDepartment().getCode();
						serviceName = serviceName + "Service";
						NocService service = (NocService) customImplProvider.find(serviceName, null);
						if(service==null)
						{
							LOG.fatal("NoC service not found with name  "+serviceName);
						}else
						{
						status = service.checkStatus(application);
						}
						 
						if (status == false)
							overAllStatus = false;

					}
				}
			}

		}
		return overAllStatus;
	}
	
	
	public Boolean updateDeemedApprovals(BpaApplication application) {
		Boolean overAllStatus = true;
		if (application != null) {
			NocList nocList = application.getNocList();
			for (NoObjectionCertificate noc : nocList.getNocList()) {
			 		if (!noc.getStatus().equals(NocStatus.APPROVED) || noc.getStatus().equals(NocStatus.REJECTED)) {
			 			Boolean status = false;
						String serviceName = noc.getNocConfig().getNocDepartment().getCode();
						serviceName = serviceName + "Service";
						NocService service = (NocService) customImplProvider.find(serviceName, null);
						status = service.updateDeemedApprovals(application);
						if (status == false)
							overAllStatus = false;
						 

					}
				}
			

		}
		return overAllStatus;
	}

}
