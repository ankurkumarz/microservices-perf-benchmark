package com.perf.benchmark;

import io.micronaut.http.MediaType;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

@Controller("/latency")
public class SimpleLatencyController {
    @Get // <2>
    @Produces(MediaType.TEXT_PLAIN)
    public String index(HttpRequest<?> request) {
    	String delay = request.getParameters().get("delay");
    	try {
    		Thread.sleep(delay!=null?Integer.parseInt(delay):0);
    	} catch (Exception e) { 
    		e.printStackTrace();
    	}
        return "Hello World with Latency " + delay; //
    }
}
