package com.zanox.external.template.vertx.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Created by bjoern.stahl on 14.11.2014
 */
public class ServerProperties extends PropertyResolver {

    public static final String SERVER_ENVIRONMENT_LIVE  = "live";

    public static final String DB_SERVER_URL = "db.server.url";
    public static final String DB_SERVER_USERNAME = "db.server.username";
    public static final String DB_SERVER_PASSWORD = "db.server.password";
    public static final String DB_SERVER_DRIVER = "db.server.driver";

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyResolver.class);
    private static ServerProperties instance = null;

    private String stage;

    private String dbServerUrl;
    private String dbServerUsername;
    private String dbServerPassword;
    private String dbServerDriver;



    /**
     * Initializes Server related properties provided by server_[env].properties file into the application.
     */
    private ServerProperties() {

        stage = System.getenv("zanox_stage");

        LOGGER.info("Stage Environment: " + stage);

        if (stage == null || stage.trim().length() == 0) {
            LOGGER.error("Stage Environment not set! Vertx Project is shutting down to prevent undefined states!");
            LOGGER.error("Contact an Adminstrator immediately!");
            System.exit(-1); //  Do NOT apply the fallback here, it might lead to unwanted results!  stage = "staging";
        }

        // Load a properties file
        Properties prop = getPropertiesFromClasspath("server_" + stage + ".properties");

        if (prop != null) {
            dbServerUrl      = prop.getProperty(DB_SERVER_URL);
            dbServerUsername = prop.getProperty(DB_SERVER_USERNAME);
            dbServerPassword = prop.getProperty(DB_SERVER_PASSWORD);
            dbServerDriver   = prop.getProperty(DB_SERVER_DRIVER);
        }
    }


    /**
     * Creates a synchronized singleton instance of ServerProperties.
     */
    public static ServerProperties getInstance() {
        if (instance == null) {
            synchronized (ServerProperties.class) {
                if (instance == null) {
                    instance = new ServerProperties();
                }
            }
        }
        return instance;
    }

    public String getServerStage() {
        return stage;
    }

    public String getDbServerUrl() {
        return dbServerUrl;
    }

    public String getDbServerUsername() {
        return dbServerUsername;
    }

    public String getDbServerPassword() {
        return dbServerPassword;
    }

    public String getDbServerDriver() {
        return dbServerDriver;
    }
}
