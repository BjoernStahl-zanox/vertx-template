package com.zanox.external.template.vertx.verticles;

import com.zanox.external.template.vertx.utils.AmazonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vertx.java.platform.Verticle;


/**
 * Created by bjoern.stahl on 14.11.2014
 */
public class StarterVerticle extends Verticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(StarterVerticle.class);

    public void start() {

        LOGGER.info("Deploying Verticles: ");

        LOGGER.info(" ---> " + LoggerVerticle.class.getName());
        container.deployWorkerVerticle(LoggerVerticle.class.getName(), 1);

        LOGGER.info(" ---> Are we running in AWS? " + AmazonUtil.getInstance().isEnvironmentAWS());

        LOGGER.info(" ---> " + MonitoringVerticle.class.getName());
        container.deployVerticle(MonitoringVerticle.class.getName());

        LOGGER.info(" ---> ScalaVerticle");
        container.deployVerticle("scala:com.zanox.external.template.vertx.verticles.ScalaVerticle");


    }
}
