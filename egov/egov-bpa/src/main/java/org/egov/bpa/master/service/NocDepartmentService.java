package org.egov.bpa.master.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.egov.bpa.master.entity.NocConfig;
import org.egov.bpa.master.entity.NocDepartment;
import org.egov.bpa.master.repository.NocDepartmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class NocDepartmentService {

	private final NocDepartmentRepository nocDepartmentRepository;
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	public NocDepartmentService(final NocDepartmentRepository nocDepartmentRepository) {
		this.nocDepartmentRepository = nocDepartmentRepository;
	}

	@Transactional
	public NocDepartment create(final NocDepartment nocDepartment) {
		for(NocConfig config:nocDepartment.getNocConfigs())
		{
			config.setNocDepartment(nocDepartment);
		}
		return nocDepartmentRepository.save(nocDepartment);
	}

	@Transactional
	public NocDepartment update(final NocDepartment nocDepartment) {
		return nocDepartmentRepository.save(nocDepartment);
	}

	public List<NocDepartment> findAll() {
		return nocDepartmentRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
	}

	public NocDepartment findByName(String name) {
		return nocDepartmentRepository.findByName(name);
	}

	public NocDepartment findByCode(String code) {
		return nocDepartmentRepository.findByCode(code);
	}

	public NocDepartment findOne(Long id) {
		return nocDepartmentRepository.findOne(id);
	}

	public List<NocDepartment> search(NocDepartment nocDepartment) {
		return nocDepartmentRepository.findAll();
	}
}