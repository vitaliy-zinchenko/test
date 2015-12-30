package zinjvi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import zinjvi.model.Topic;
import zinjvi.model.TopicProjection;

/**
 * Created by Vitaliy on 10/22/2015.
 */
@RepositoryRestResource(collectionResourceRel = "topic", path = "topic", excerptProjection = TopicProjection.class)
public interface TopicRepository extends CrudRepository<Topic, Long> {
}
