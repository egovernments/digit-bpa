package org.egov.bpa.master.service;

import java.util.List;

import org.egov.bpa.master.entity.NocConfiguration;
import org.egov.bpa.master.repository.NocConfigurationRepository;
import org.egov.bpa.transaction.entity.BpaStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class NocConfigurationService {

	@Autowired
    private NocConfigurationRepository nocConfigurationRepository;
	
	public NocConfiguration findByDepartment(String department){
		 return nocConfigurationRepository.findByDepartment(department);
	}
	public List<NocConfiguration> findIsDeemedApproval(){
		return nocConfigurationRepository.findIsDeemedApproval();
	}
	
    public List<NocConfiguration> findAll() {
        return nocConfigurationRepository.findAll();
    }
}
