package com.zanox.external.template.vertx.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by bjoern.stahl on 14.11.2014
 */
public class NetworkUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkUtil.class);

    public static String getMyIPAddress() {

        try {
            final InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostAddress();
        } catch (UnknownHostException e) {
            LOGGER.error("Could not find the host address", e);
            return null;
        }
    }

}
