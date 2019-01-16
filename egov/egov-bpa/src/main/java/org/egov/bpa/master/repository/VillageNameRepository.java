package org.egov.bpa.master.repository;

import org.egov.bpa.master.entity.VillageName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VillageNameRepository  extends JpaRepository<VillageName, Long> {

}
