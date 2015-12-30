package zinjvi.durableTopicTest;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by Vitaliy on 11/14/2015.
 */
public class DurableClient {

    public static final String BROKER_URL = "tcp://localhost:61616";
    public static final String PASSWORD = "admin";
    public static final String LOGIN = "admin";
    public static final String TOPIC_NAME = "test-topic";
    public static final String SUBSCRIBER_NAME = "subscriber-nameX";

    public static void main(String[] args) throws JMSException {
        System.out.println("started durable subscriber");

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(PASSWORD, LOGIN, BROKER_URL);
        Connection connection = connectionFactory.createConnection();
        connection.setClientID(SUBSCRIBER_NAME);
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, SUBSCRIBER_NAME);
        topicSubscriber.setMessageListener(message -> {
            TextMessage textMessage = (TextMessage) message;
            try {
                System.out.println("received message: " + textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
        System.out.println("registered message listener");
        connection.start();
    }
}
