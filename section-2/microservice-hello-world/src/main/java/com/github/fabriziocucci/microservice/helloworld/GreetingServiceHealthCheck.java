package com.github.fabriziocucci.microservice.helloworld;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.codahale.metrics.health.HealthCheck;

public class GreetingServiceHealthCheck extends HealthCheck {

	private final GreetingService greetingService;
	
	@Inject
	public GreetingServiceHealthCheck(GreetingService greetingService) {
		this.greetingService = greetingService;
	}

	@Override
	protected Result check() throws Exception {
		Response response = greetingService.health();
		return (response.getStatus() == Status.OK.getStatusCode()) ?
				Result.healthy("Greeting service available") :
				Result.unhealthy("Greeting service unavailable");
	}
	
}
