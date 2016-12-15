package com.github.fabriziocucci.microservice.greeting;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/greet")
public class GreetingResource {

	private final List<String> greetings = Arrays.asList(
			"Hi World!", 
			"Hello World!", 
			"Howdy World!");
	
	private final Random random = new Random();
	
	@GET
	public String greet() {
		return greetings.get(random.nextInt(greetings.size()));
	}
	
}
