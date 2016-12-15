package com.github.fabriziocucci.microservice.helloworld;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/hello-world")
public class HelloWorldResource {

	private final GreetingService greetingService;
	
	@Inject
	public HelloWorldResource(GreetingService greetingService) {
		this.greetingService = greetingService;
	}

	@GET
	public String hello() {
		return greetingService.greet();
	}
	
}
