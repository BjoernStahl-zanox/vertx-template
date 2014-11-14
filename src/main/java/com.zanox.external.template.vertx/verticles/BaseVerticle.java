package com.zanox.external.template.vertx.verticles;

import org.vertx.java.platform.Verticle;

/**
 * Created by bjoern.stahl on 14.11.2014
 */
public abstract class BaseVerticle extends Verticle {

    /**
     * Returns the current verticle address.
     *
     * @return  current running verticle address
     */
    public abstract String getCurrentVerticleAddress();
}
