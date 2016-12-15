package com.github.fabriziocucci.microservice.embeddedserver;

import java.net.URI;

import javax.inject.Inject;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.github.fabriziocucci.microservice.MicroserviceConfiguration;

class GrizzlyEmbeddedServer implements EmbeddedServer {

	private final MicroserviceConfiguration configuration;
	private final ResourceConfig resourceConfig;
	private final ServiceLocator serviceLocator;
	
	@Inject
	GrizzlyEmbeddedServer(MicroserviceConfiguration configuration, ResourceConfig resourceConfig, ServiceLocator serviceLocator) {
		this.configuration = configuration;
		this.resourceConfig = resourceConfig;
		this.serviceLocator = serviceLocator;
	}

	@Override
	public void start() {
		try {
			GrizzlyHttpServerFactory.createHttpServer(buildUri(), resourceConfig, serviceLocator).start();
		} catch (Exception exception) {
			throw new IllegalStateException("Unable to start server!", exception);
		}
	}
	
	private URI buildUri() {
		return UriBuilder.fromUri("http://host:port")
				.host(configuration.getEmbeddedServerConfiguration().getHost())
				.port(configuration.getEmbeddedServerConfiguration().getPort())
				.build();
	}
	
}
