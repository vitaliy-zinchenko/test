package jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Vitaliy on 10/1/2015.
 */
public class Bean {

    private static final Logger LOGGER = LoggerFactory.getLogger(Bean.class);

    public Bean() {
        LOGGER.info("Bean started");
    }
}
