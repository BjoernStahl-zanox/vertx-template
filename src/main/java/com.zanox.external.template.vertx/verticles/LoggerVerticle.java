package com.zanox.external.template.vertx.verticles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;

/**
 * Created by bjoern.stahl on 14.11.2014
 */
public class LoggerVerticle extends BaseVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerVerticle.class);

    private EventBus eventBus;

    public void start() {
        LOGGER.info("Starting LoggerVerticle ... ");

        eventBus = vertx.eventBus();
        eventBus.registerHandler(getCurrentVerticleAddress(), new TrackingDataHandler());
    }


    private class TrackingDataHandler implements Handler<Message<String>> {

        @Override
        public void handle(Message<String> event) {
            LOGGER.info(event.body());
        }
    }

    public String getCurrentVerticleAddress() {
        return this.getClass().getSimpleName();
    }
}
