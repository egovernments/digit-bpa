package org.egov.bpa.master.repository;

import org.egov.infra.admin.master.entity.BoundaryPojo;
import org.egov.infra.microservice.utils.MicroserviceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class BoundaryPojoRepository {
	@Autowired
	MicroserviceUtils microserviceUtils;
	public BoundaryPojo findById(Long id)
	{
	//	microserviceUtils.getBoundaryById();
		
		BoundaryPojo boundary= new BoundaryPojo();
		boundary.setId(id);
		boundary.setName("From RM repo");
		return boundary;
		
	}

}
