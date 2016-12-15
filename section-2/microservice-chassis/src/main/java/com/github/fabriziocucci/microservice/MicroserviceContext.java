package com.github.fabriziocucci.microservice;

import javax.inject.Inject;

import org.glassfish.jersey.server.ResourceConfig;

import com.github.fabriziocucci.microservice.servicediscovery.DiscoverableServiceBuilder;

public class MicroserviceContext<Configuration extends MicroserviceConfiguration> {
	
	private final Configuration configuration;
	private final ResourceConfig resourceConfig;
	private final DiscoverableServiceBuilder discoverableServiceBuilder;
	
	@Inject
	@SuppressWarnings("unchecked")
	MicroserviceContext(MicroserviceConfiguration configuration, ResourceConfig resourceConfig, DiscoverableServiceBuilder discoverableServiceBuilder) {
		// https://github.com/hk2-project/hk2/issues/41
		this.configuration = (Configuration) configuration;
		this.resourceConfig = resourceConfig;
		this.discoverableServiceBuilder = discoverableServiceBuilder;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public ResourceConfig getResourceConfig() {
		return resourceConfig;
	}

	public DiscoverableServiceBuilder getDiscoverableServiceBuilder() {
		return discoverableServiceBuilder;
	}
	
}
