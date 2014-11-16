package com.zanox.external.template.vertx.constants;

/**
 * Created by saschamoellering on 16/11/14.
 */
/**
 * Stores default kafka properties.
 */
public final class KafkaConstants {

    /* Non-instantiable class */
    private KafkaConstants() {}

    public static final String BROKER_LIST = "";
    // public static final String TOPIC = "tracking-topic";
    public static final String TOPIC = "";
    public static final String PARTITION = "";
    public static final int REQUEST_ACKS = 0;
    public static final String STRING_SERIALIZER_CLASS = "kafka.serializer.StringEncoder";
    public static final String BYTE_SERIALIZER_CLASS = "kafka.serializer.DefaultEncoder";

    public static final String PAYLOAD = "payload";
}
