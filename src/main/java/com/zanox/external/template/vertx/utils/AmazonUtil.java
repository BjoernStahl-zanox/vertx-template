package com.zanox.external.template.vertx.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by sascha.moellering on 21/10/2014.
 */
public class AmazonUtil {


    private static final AmazonUtil AMAZON_UTIL = new AmazonUtil();

    private Boolean isAmazon;

    private AmazonUtil() {}

    public static final AmazonUtil getInstance() {
        return AMAZON_UTIL;
    }

    public boolean isEnvironmentAWS() {
        if (isAmazon == null) {
            isAmazon = true;
            BufferedReader in = null;

            try {
                // Checking "magic" AWS URL for instance meta-data
                // URL is available only in AWS
                // see: http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/ec2-instance-metadata.html

                URL url = new URL("http://169.254.169.254/latest/meta-data/");
                URLConnection connection = url.openConnection();
                connection.setConnectTimeout(50);
                in = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
            } catch (IOException exc) {
                isAmazon = false;
            } finally {
                try {
                    if (in != null)
                        in.close();
                } catch (IOException exc) {

                }
            }
        }

        return isAmazon;
    }
}
