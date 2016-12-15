package com.github.fabriziocucci.microservice.multitenancy;

import javax.inject.Inject;

import org.glassfish.jersey.server.ResourceConfig;

import com.github.fabriziocucci.microservice.ChassisBinder;

public class MultitenancyBinder extends ChassisBinder {

	@Inject
	public MultitenancyBinder(ResourceConfig resourceConfig) {
		resourceConfig.register(TenantHeaderFilter.class);
	}

	@Override
	protected void configure() {
		// nothing interesting to do!
	}
	
}
