package zinjvi.common;

import javax.jms.JMSException;
import javax.jms.Session;

/**
 * Created by Vitaliy on 9/27/2015.
 */
public interface JmsSessionFactory {

    Session create(int acknowledgeMode) throws JMSException;

    void destroy();

}
