package org.egov.bpa.repository.es;

import org.egov.bpa.entity.es.OccupancyCertificateIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OccupancyCertificateIndexRepository extends ElasticsearchRepository<OccupancyCertificateIndex, String> {

}
