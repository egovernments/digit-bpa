package org.egov.bpa.master.service;

import org.egov.bpa.master.entity.StakeHolder;
import org.egov.bpa.master.entity.StakeHolderState;
import org.egov.bpa.master.repository.StakeHolderStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class StakeHolderStateService {
	   @Autowired
	   private StakeHolderStateRepository stakeHolderStateRepository;
	 
	   public StakeHolderState findById(final Long id) {
	        return stakeHolderStateRepository.findOne(id);
	    }
	   public StakeHolderState findByStakeHolder(final StakeHolder stakeHolder) {
	        return stakeHolderStateRepository.findByStakeHolder(stakeHolder);
	    }
	   public StakeHolderState findByStakeHolderId(final Long stakeHolderId) {
	        return stakeHolderStateRepository.findByStakeHolderId(stakeHolderId);
	    }
}
