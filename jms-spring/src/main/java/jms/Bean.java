package jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.messaging.support.MessageBuilder;

import javax.jms.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Vitaliy on 10/1/2015.
 */
public class Bean {

    private static final Logger LOGGER = LoggerFactory.getLogger(Bean.class);

    private ConnectionFactory connectionFactory;

    private JmsTemplate jmsTemplate;

    public Bean() {
        LOGGER.info("Bean started");
    }

    public void init() {
        new Timer(true).schedule(new TimerTask() {
            @Override
            public void run() {
                LOGGER.info("sender start");

                User u = new User();
                u.setId(1L);
                u.setName("name");

                jmsTemplate.convertAndSend(u, (Message message) -> {
                    message.setStringProperty("type", "user");
                    return message;
                });

                try {
                    Connection connection = connectionFactory.createConnection();
                    connection.start();
                    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

                    // Create the destination (Topic or Queue)
                    Destination destination = session.createQueue("TEST.FOO");

                    // Create a MessageProducer from the Session to the Topic or Queue
                    MessageProducer producer = session.createProducer(destination);
                    producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

                    Message message = session.createTextMessage("{\"id\":\"100\", \"name\":\"qqq2\"}");
                    message.setStringProperty("type", "user");

                    producer.send(message);

                    connection.close();
                } catch (JMSException e) {
                    LOGGER.error("Failed create JMS connection", e);
                }



            }
        }, 1000);
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
}
