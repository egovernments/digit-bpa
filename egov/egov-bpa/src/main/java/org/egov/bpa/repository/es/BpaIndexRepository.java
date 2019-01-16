package org.egov.bpa.repository.es;

import org.egov.bpa.entity.es.BpaIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BpaIndexRepository extends ElasticsearchRepository<BpaIndex, String> {

}
