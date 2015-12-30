package jms;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Created by Vitaliy on 10/28/2015.
 */
public class MessageReceiver implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println(message);
    }
}
