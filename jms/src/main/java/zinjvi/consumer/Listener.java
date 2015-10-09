package zinjvi.consumer;

import org.apache.activemq.ActiveMQConnectionFactory;
import zinjvi.common.JmsSessionFactory;

import javax.jms.*;

/**
 * Created by Vitaliy on 9/27/2015.
 */
public class Listener {

    public static final String QUEUE_NAME = "zin.test";
    private JmsSessionFactory jmsSessionFactory;

    private Session session;

    public Listener(JmsSessionFactory jmsSessionFactory) {
        this.jmsSessionFactory = jmsSessionFactory;
    }

    public void start() {
        try {
            session = jmsSessionFactory.create(Session.AUTO_ACKNOWLEDGE);
            Destination queue = session.createQueue(QUEUE_NAME);
            MessageConsumer consumer = session.createConsumer(queue);
            consumer.setMessageListener(this::processMessage);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void processMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;
            System.out.println("processing message: " + textMessage.getText());
            Thread.sleep(10000);
            System.out.println("finished processing message: " + textMessage.getText());
        } catch (JMSException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        if (session != null) {
            try {
                session.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

}
