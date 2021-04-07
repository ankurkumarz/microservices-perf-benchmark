package com.perf.benchmark;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;

@MicronautTest // <1>
public class SimpleLatencyControllerTest {

	  @Inject
	    @Client("/")
	    RxHttpClient client; // <2>

	    @Test
	    public void testHello() {
	        HttpRequest<String> request = HttpRequest.GET("/latency"); // <3>
	        String body = client.toBlocking().retrieve(request);

	        assertNotNull(body);
	        assertTrue(body.contains("Hello World"));
	    }
}
