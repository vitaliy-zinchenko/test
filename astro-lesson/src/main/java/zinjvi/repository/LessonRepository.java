package zinjvi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import zinjvi.model.Lesson;
import zinjvi.model.Topic;

/**
 * Created by Vitaliy on 10/22/2015.
 */
@RepositoryRestResource(collectionResourceRel = "lesson", path = "lesson")
public interface LessonRepository extends CrudRepository<Lesson, Long> {
}
