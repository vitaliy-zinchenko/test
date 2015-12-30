package zinjvi.model;

import org.springframework.data.rest.core.config.Projection;
import zinjvi.model.Lesson;
import zinjvi.model.Topic;

import java.util.List;

/**
 * Created by Vitaliy on 10/26/2015.
 */
@Projection(name = "topicProjection", types = {Topic.class})
public interface TopicProjection {

    Long getIdTopic();

    String getName();

    List<Lesson> getLessons();

}
