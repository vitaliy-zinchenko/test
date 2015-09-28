package zinjvi.common.impl;

import org.apache.activemq.ActiveMQConnectionFactory;
import zinjvi.common.JmsSessionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

/**
 * Created by Vitaliy on 9/27/2015.
 */
public class ActiveMqJmsSessionFactory implements JmsSessionFactory {

    private String brokerUrl;
    private String user;
    private String password;

    private ConnectionFactory connectionFactory;
    private Connection connection;

    public ActiveMqJmsSessionFactory(String brokerUrl, String user, String password) {
        if(brokerUrl == null || user == null || password == null)
            throw new NullPointerException();
        this.brokerUrl = brokerUrl;
        this.user = user;
        this.password = password;
    }



    public Session create(int acknowledgeMode) throws JMSException {
        connectionFactory = new ActiveMQConnectionFactory(user, password, brokerUrl);
        connection = connectionFactory.createConnection();
        connection.start();
        return connection.createSession(false, acknowledgeMode);
    }



    public void destroy() {
        if(connection != null) {
            try {
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
