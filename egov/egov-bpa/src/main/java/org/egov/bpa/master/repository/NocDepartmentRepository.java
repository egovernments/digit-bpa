package org.egov.bpa.master.repository;


import org.egov.bpa.master.entity.NocDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


@Repository 
public interface NocDepartmentRepository extends JpaRepository<NocDepartment,java.lang.Long>,JpaSpecificationExecutor<NocDepartment>  {

NocDepartment findByName(String name);

NocDepartment findByCode(String code);

}