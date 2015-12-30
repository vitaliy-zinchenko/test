package zinjvi.durableTopicTest;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by Vitaliy on 11/14/2015.
 */
public class Client {

    public static final String BROKER_URL = "tcp://localhost:61616";
    public static final String PASSWORD = "admin";
    public static final String LOGIN = "admin";
    public static final String TOPIC_NAME = "test-topic";

    public static void main(String[] args) throws JMSException {
        System.out.println("started subscriber");

        TopicConnectionFactory connectionFactory = new ActiveMQConnectionFactory(PASSWORD, LOGIN, BROKER_URL);
        TopicConnection connection = connectionFactory.createTopicConnection();
        TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        TopicSubscriber topicSubscriber = session.createSubscriber(topic);
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
