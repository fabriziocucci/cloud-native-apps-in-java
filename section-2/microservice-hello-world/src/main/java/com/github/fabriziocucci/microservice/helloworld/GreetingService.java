package com.github.fabriziocucci.microservice.helloworld;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/greet")
public interface GreetingService {

	@GET
	String greet();
	
}
