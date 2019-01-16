package org.egov.bpa.transaction.repository;

import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.SlotApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlotApplicationRepository extends JpaRepository<SlotApplication, Long>, JpaSpecificationExecutor<SlotApplication> {

	List<SlotApplication> findByApplicationOrderByIdDesc(BpaApplication bpaApplication);
	

}
