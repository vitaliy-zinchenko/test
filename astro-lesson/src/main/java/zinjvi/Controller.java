package zinjvi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import zinjvi.model.Topic;
import zinjvi.repository.TopicRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Created by Vitaliy on 10/6/2015.
 */
@RestController
public class Controller {

    @Autowired
    private EntityManagerFactory emf;

    @Autowired
    private TopicRepository topicRepository;

    @ResponseBody
    @RequestMapping("/test")
    public Topic test() {

//        EntityManager manager = emf.createEntityManager();
//
//        Reader reader = new Reader();
//        reader.setName("n");
//        manager.getTransaction().begin();
//        manager.persist(reader);
//        manager.getTransaction().commit();
//
//        manager = emf.createEntityManager();
//        Reader reader1 = manager.find(Reader.class, 1L);
//        System.out.println(reader1);
//
//        manager.close();

        Topic topic = new Topic();
        topic.setName("t");
        topicRepository.save(topic);

        System.out.println("saved " + topic);
        System.out.println("retreived " + topicRepository.findOne(1L));



        return topicRepository.findOne(1L);
    }

}
