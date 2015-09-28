package zinjvi.producer;

import zinjvi.common.JmsSessionFactory;

/**
 * Created by Vitaliy on 9/27/2015.
 */
public class Sender {

    private JmsSessionFactory jmsSessionFactory;

    public Sender(JmsSessionFactory jmsSessionFactory) {
        this.jmsSessionFactory = jmsSessionFactory;
    }
}
