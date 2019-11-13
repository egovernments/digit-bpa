package org.egov.bpa.master.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.egov.bpa.master.entity.BpaApplicationType;
import org.egov.bpa.master.entity.NocConfig;
import org.egov.bpa.master.repository.NocConfigRepository;
import org.egov.bpa.transaction.entity.enums.NocIntegrationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class NocConfigService {

	private final NocConfigRepository nocConfigRepository;
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	public NocConfigService(final NocConfigRepository nocConfigRepository) {
		this.nocConfigRepository = nocConfigRepository;
	}

	@Transactional
	public NocConfig create(final NocConfig nocConfig) {
		return nocConfigRepository.save(nocConfig);
	}

	@Transactional
	public NocConfig update(final NocConfig nocConfig) {
		return nocConfigRepository.save(nocConfig);
	}

	public List<NocConfig> findAll() {
		return nocConfigRepository.findAll(new Sort(Sort.Direction.ASC, "name"));
	}

	public NocConfig findOne(Long id) {
		return nocConfigRepository.findOne(id);
	}

	public List<NocConfig> search(NocConfig nocConfig) {
		return nocConfigRepository.findAll();
	}

	public List<NocConfig> findByApplicationType(BpaApplicationType type) {
		return nocConfigRepository.findByApplicationType(type);
	}

	public List<NocConfig> findByIntegrationType(NocIntegrationType type) {
		return nocConfigRepository.findByIntegrationType(type);
	}

	public List<NocConfig> findByApplicationTypeAndIntegrationType(BpaApplicationType type,
			NocIntegrationType integrationType) {
		return nocConfigRepository.findByApplicationTypeAndIntegrationType(type, integrationType);
	}

}