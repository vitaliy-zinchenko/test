package jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Created by Vitaliy on 10/28/2015.
 */
@Component
public class StringMessageReceiver {

    @JmsListener(containerFactory = "myContainerFactory", destination = "TEST.FOO")
    public void handleMessage(User message) {

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(message);

//        return "Hi " + message;
    }
}
