package com.github.fabriziocucci.microservice.healthchecks;

import javax.inject.Inject;

import org.glassfish.jersey.server.ResourceConfig;

import com.github.fabriziocucci.microservice.ChassisBinder;

public class HealthChecksBinder extends ChassisBinder {

	@Inject
	public HealthChecksBinder(ResourceConfig resourceConfig) {
		resourceConfig.register(HealthChecksResource.class);
	}
	
	@Override
	protected void configure() {
		// nothing interesting to do!
	}

}
