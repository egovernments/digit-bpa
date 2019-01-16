package org.egov.bpa.master.service;

import java.util.List;

import org.egov.bpa.master.entity.ConstructionStages;
import org.egov.bpa.master.repository.ConstructionStagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ConstructionStagesService {

    @Autowired
    private ConstructionStagesRepository constructionStagesRepository;
    

    public List<ConstructionStages> findAll() {
        return constructionStagesRepository.findAll();
    }

}
