package org.egov.bpa.master.repository;

import org.egov.bpa.master.entity.StakeHolder;
import org.egov.bpa.master.entity.StakeHolderState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.repository.query.Param;

public interface StakeHolderStateRepository extends JpaRepository<StakeHolderState, Long>, RevisionRepository<StakeHolderState, Long, Integer> { 
	StakeHolderState findByStakeHolder(StakeHolder stakeHolder);
	@Query("select stakeHolderState from StakeHolderState stakeHolderState where stakeHolderState.stakeHolder.id=:id")
	StakeHolderState findByStakeHolderId(@Param("id") Long id);
}
