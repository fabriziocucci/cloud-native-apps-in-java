package com.github.fabriziocucci.microservice.helloworld;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Path("/hello-world")
public class HelloWorldResource {

	private static final Logger LOGGER = LogManager.getLogger();
	
	private final GreetingService greetingService;
	
	@Inject
	public HelloWorldResource(GreetingService greetingService) {
		this.greetingService = greetingService;
	}

	@GET
	public String hello() {
		LOGGER.info("Received request in hello-world service");
		return greetingService.greet();
	}
	
}
