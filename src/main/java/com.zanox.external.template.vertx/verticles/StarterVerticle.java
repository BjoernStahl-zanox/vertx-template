package com.zanox.external.template.vertx.verticles;

import com.zanox.external.template.vertx.constants.KafkaConstants;
import com.zanox.external.template.vertx.constants.KinesisConstants;
import com.zanox.external.template.vertx.utils.AmazonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;

import static com.zanox.external.template.vertx.constants.Constants.*;
import static com.zanox.external.template.vertx.constants.VertxModuleRegistryConstants.*;


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

        if (AmazonUtil.getInstance().isEnvironmentAWS()) {
            LOGGER.info(" ---> " + MODULE_KINESIS);
            container.deployModule(MODULE_KINESIS, getKinesisModuleConfig());
        } else {
            LOGGER.info(" ---> " + MODULE_KAFKA + " worker module");
            container.deployModule(MODULE_KAFKA, getKafkaModuleConfig(), 4);
        }
    }

    private JsonObject getKafkaModuleConfig() {
        final JsonObject config = new JsonObject();
        config.putString("address", TRACKING_BUS);
        config.putString("metadata.broker.list", KafkaConstants.BROKER_LIST);
        config.putString("kafka-topic", KafkaConstants.TOPIC);
        config.putString("kafka-partition", KafkaConstants.PARTITION);
        config.putNumber("request.required.acks", KafkaConstants.REQUEST_ACKS);
        config.putString("serializer.class", KafkaConstants.BYTE_SERIALIZER_CLASS);

        return config;
    }

    private JsonObject getKinesisModuleConfig() {
        JsonObject config = new JsonObject();
        config.putString("address", TRACKING_BUS);
        config.putString("streamName", KinesisConstants.KINESIS_STREAM);
        config.putString("partitionKey", KinesisConstants.KINESIS_PARTITION_KEY);
        config.putString("region", KinesisConstants.KINESIS_REGION);

        return config;
    }
}
