package org.egov.bpa.repository.es;

import org.egov.bpa.entity.es.StakeHolderIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StakeHolderIndexRepository extends ElasticsearchRepository<StakeHolderIndex,String> {
}
