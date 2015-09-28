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
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    System.out.println(message);
                }
            });
        } catch (JMSException e) {
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
