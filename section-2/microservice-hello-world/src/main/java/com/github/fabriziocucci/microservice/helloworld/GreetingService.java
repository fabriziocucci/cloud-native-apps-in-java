package com.github.fabriziocucci.microservice.helloworld;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/")
public interface GreetingService {

	@Path("/health")
	@GET
	Response health();
	
	@Path("/greet")
	@GET
	String greet();
	
}
