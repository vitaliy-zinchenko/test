package zinjvi.consumer;

import zinjvi.common.JmsSessionFactory;
import zinjvi.common.impl.ActiveMqJmsSessionFactory;

import java.io.IOException;

/**
 * Created by Vitaliy on 9/27/2015.
 */
public class ConsumerApp {
    public static void main(String[] args) throws IOException {
        JmsSessionFactory jmsSessionFactory = new ActiveMqJmsSessionFactory("tcp://localhost:61616", "admin", "admin");
        Listener listener = new Listener(jmsSessionFactory);
        listener.start();

        System.in.read();
        listener.destroy();
        jmsSessionFactory.destroy();
    }
}
