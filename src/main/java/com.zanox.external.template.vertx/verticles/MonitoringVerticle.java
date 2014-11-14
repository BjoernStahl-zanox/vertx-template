package com.zanox.external.template.vertx.verticles;

import com.zanox.external.template.vertx.constants.Constants;
import com.zanox.external.template.vertx.utils.NetworkUtil;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.http.HttpClient;
import org.vertx.java.core.http.HttpClientRequest;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by bjoern.stahl on 14.11.2014
 */
public class MonitoringVerticle extends Verticle {


    private static final String SQL_SIMPLE = "Select ?";
    private static final String HEALTH_NAME = "healthCheckName";
    private static final String HEALTH_STATUS = "healthStatus";
    private static final String HEALTH = "health";
    private static final String DETAIL = "detail";
    private static final String HEALTHY = "HEALTHY";
    private static final String UNHEALTHY = "UNHEALTHY";

    private static HttpServerRequest lrequest = null;
    private static String answer;

    private JsonObject httpStatus;

    private EventBus eventBus;

    @Override
    public void start() {
        eventBus = vertx.eventBus();
        RouteMatcher routeMatcher = new RouteMatcher();

        routeMatcher.get(Constants.ZXMONITOR_PATH, request -> request.response().sendFile(Constants.ZXMONITOR_FILE));

        routeMatcher.get("/admin/health/heartbeat", request -> {
            JsonObject result = new JsonObject();
            result.putString("ping", "pong");
            request.response().end(result.encodePrettily());
        });

        routeMatcher.get("/admin/health/check", request -> {
            lrequest = request;
            checkHttpConnectionToOutside();
            //waiting fixed 2 seconds for an answer
            vertx.setTimer(2000, event -> {
                answer = checkIt().encodePrettily();
                lrequest.response().end(answer);
            });
        });

        vertx.createHttpServer().requestHandler(routeMatcher).listen(8080, NetworkUtil.getMyIPAddress());
    }

    /**
     * Do all kinds of internal checks here like connection to the outside, DB connections etc.
     * @return JsonArray, which contains all the results.
     */
    private JsonArray checkIt() {

        JsonObject result1 = new JsonObject();
        result1.putString(HEALTH_NAME, "HTTP Connection to the outside");
        result1.putObject(HEALTH_STATUS, httpStatus);

        JsonArray finalArray = new JsonArray();
        finalArray.add(result1);

        return finalArray;
    }


    /**
     * Doing a general check if the connection to the outside is working.
     */
    private void checkHttpConnectionToOutside() {
        try {
            URL url = new URL("http://www.heise.de");
            HttpClient vertxHttpClient = vertx.createHttpClient();
            vertxHttpClient.setHost(url.getHost());
            if (url.getPort() == -1) {
                vertxHttpClient.setPort(80);
            } else {
                vertxHttpClient.setPort(url.getPort());
            }

            HttpClientRequest request = vertxHttpClient.get(url.toExternalForm(), resp -> {
                httpStatus = new JsonObject();
                httpStatus.putString(HEALTH, HEALTHY);
                httpStatus.putString(DETAIL, "Test Call to http://www.heise.de");
                httpStatus.putString(DETAIL, "HTTP Status Code: " + resp.statusCode());

            });

            request.end();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
