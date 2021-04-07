package com.perf.benchmark;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.validation.Validated;

@Validated
@Controller("/latency")
public class SimpleLatencyController {
	

    @Get
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<?> index(HttpRequest<?> request) {
    	String delay = request.getParameters().get("delay");
    	try {
    		Thread.sleep(delay!=null?Integer.parseInt(delay):0);
    	} catch (Exception e) { 
    		e.printStackTrace();
    	}
        return HttpResponse.status(HttpStatus.OK).body("Hello World with Delay:"+delay);
    }   
}
