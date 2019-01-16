package org.egov.bpa.master.repository;

import org.egov.bpa.master.entity.ConstructionStages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConstructionStagesRepository  extends JpaRepository<ConstructionStages, Long> {

}
