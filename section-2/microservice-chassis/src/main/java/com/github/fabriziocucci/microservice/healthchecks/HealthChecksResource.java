package com.github.fabriziocucci.microservice.healthchecks;

import java.util.SortedMap;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.codahale.metrics.health.HealthCheck.Result;
import com.codahale.metrics.health.HealthCheckRegistry;

@Path("/health")
public class HealthChecksResource {

	private final HealthCheckRegistry healthCheckRegistry;
	
	@Inject
	public HealthChecksResource(HealthCheckRegistry healthCheckRegistry) {
		this.healthCheckRegistry = healthCheckRegistry;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response healthChecks() {
		SortedMap<String, Result> healthCheckResults = healthCheckRegistry.runHealthChecks();
		boolean isHealthy = healthCheckResults.values().stream().allMatch(healthCheckResult -> healthCheckResult.isHealthy());
		return isHealthy ?
				Response.status(Status.OK).entity(healthCheckResults).build() :
				Response.status(Status.SERVICE_UNAVAILABLE).entity(healthCheckResults).build();
	}
	
}
