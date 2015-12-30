package zinjvi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Vitaliy on 10/22/2015.
 */
@Entity
public class Topic {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id_topic")
    private Long idTopic;

    private String name;

    @OneToMany(mappedBy = "topic", fetch = FetchType.EAGER)
//    @JsonManagedReference
    private List<Lesson> lessons;

    @Override
    public String toString() {
        return "Topic{" +
                "name='" + name + '\'' +
                ", idTopic=" + idTopic +
                '}';
    }

    public Long getIdTopic() {
        return idTopic;
    }

    public void setIdTopic(Long idTopic) {
        this.idTopic = idTopic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
}
