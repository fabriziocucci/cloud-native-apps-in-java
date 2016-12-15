package com.github.fabriziocucci.microservice.greeting;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Path("/greet")
public class GreetingResource {

	private static final Logger LOGGER = LogManager.getLogger();
	
	private final List<String> greetings = Arrays.asList(
			"Hi World!", 
			"Hello World!", 
			"Howdy World!");
	
	private final Random random = new Random();
	
	@GET
	public String greet() {
		LOGGER.info("Received request in greeting service");
		return greetings.get(random.nextInt(greetings.size()));
	}
	
}
