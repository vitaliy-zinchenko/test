package zinjvi.common.impl;

import org.apache.activemq.ActiveMQConnectionFactory;
import zinjvi.common.JmsSessionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Created by Vitaliy on 9/27/2015.
 */
public class JndiJmsSessionFactory implements JmsSessionFactory {

    private String brokerUrl;
    private String user;
    private String password;

    private ConnectionFactory connectionFactory;
    private Connection connection;

    public JndiJmsSessionFactory() {
    }



    public Session create(int acknowledgeMode) throws JMSException {
        try {
            Context context = new InitialContext();
            connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
        } catch (NamingException e) {
            e.printStackTrace();
        }
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
