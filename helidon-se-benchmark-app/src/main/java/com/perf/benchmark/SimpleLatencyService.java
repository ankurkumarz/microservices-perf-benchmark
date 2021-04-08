
package com.perf.benchmark;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonException;
import javax.json.JsonObject;

import io.helidon.common.http.Http;
import io.helidon.config.Config;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;

/**
 * A simple service to test. Examples:
 *
 * Get default greeting message:
 * curl -X GET http://localhost:8080/latency
 * 
 * The message is returned as a JSON object
 */

public class SimpleLatencyService implements Service {

    /**
     * The config value for the key {@code greeting}.
     */
    private final AtomicReference<String> greeting = new AtomicReference<>();

    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

    private static final Logger LOGGER = Logger.getLogger(SimpleLatencyService.class.getName());

    SimpleLatencyService(Config config) {
        greeting.set(config.get("app.greeting").asString().orElse("Ciao"));
    }

    /**
     * A service registers itself by updating the routing rules.
     * @param rules the routing rules.
     */
    @Override
    public void update(Routing.Rules rules) {
        rules
        	.get("/", this::getLatencyHandler);
    }

    /**
     * Latency Handler
     * @param request the server request
     * @param response the server response
     */
    private void getLatencyHandler(ServerRequest request, ServerResponse response) {
    	
        String delay = request.queryParams().first("delay").map(Object::toString)
                .orElse("0");
        sendResponse(response, "World", delay);
    }
    
    private void sendResponse(ServerResponse response, String name, String delay) {
    	try {
			Thread.sleep(Integer.parseInt(delay));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        String msg = String.format("%s %s with delay %s!", greeting.get(), name, delay);

        JsonObject returnObject = JSON.createObjectBuilder()
                .add("message", msg)
                .build();
        response.send(returnObject);
    }
}