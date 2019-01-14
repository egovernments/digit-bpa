package org.egov.bpa.master.repository;


import org.egov.bpa.master.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository 
public interface HolidayRepository extends JpaRepository<Holiday,java.lang.Long>,JpaSpecificationExecutor<Holiday>  {

}