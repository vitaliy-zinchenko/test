package zinjvi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import zinjvi.model.Task;
import zinjvi.model.Topic;

/**
 * Created by Vitaliy on 10/22/2015.
 */
@RepositoryRestResource(path = "task")
public interface TaskRepository extends CrudRepository<Task, Long> {
}
