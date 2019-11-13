package org.egov.bpa.master.repository;


import java.util.List;

import org.egov.bpa.master.entity.BpaApplicationType;
import org.egov.bpa.master.entity.NocConfig;
import org.egov.bpa.transaction.entity.enums.NocIntegrationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository 
public interface NocConfigRepository extends JpaRepository<NocConfig,java.lang.Long>,JpaSpecificationExecutor<NocConfig> 
{
	List<NocConfig> findByApplicationType(BpaApplicationType type);
	List<NocConfig> findByIntegrationType(NocIntegrationType type);
	List<NocConfig> findByApplicationTypeAndIntegrationType(BpaApplicationType type,NocIntegrationType integrationType);
	
}