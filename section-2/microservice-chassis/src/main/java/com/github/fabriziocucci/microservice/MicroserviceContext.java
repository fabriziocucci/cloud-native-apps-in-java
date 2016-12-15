package com.github.fabriziocucci.microservice;

import javax.inject.Inject;

import org.glassfish.jersey.server.ResourceConfig;

public class MicroserviceContext<Configuration extends MicroserviceConfiguration> {
	
	private final Configuration configuration;
	private final ResourceConfig resourceConfig;
	
	@Inject
	@SuppressWarnings("unchecked")
	MicroserviceContext(MicroserviceConfiguration configuration, ResourceConfig resourceConfig) {
		// https://github.com/hk2-project/hk2/issues/41
		this.configuration = (Configuration) configuration;
		this.resourceConfig = resourceConfig;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public ResourceConfig getResourceConfig() {
		return resourceConfig;
	}
	
}
