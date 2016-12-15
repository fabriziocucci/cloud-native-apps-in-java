package com.github.fabriziocucci.microservice.embeddedserver;

import java.net.URI;

import javax.inject.Inject;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.github.fabriziocucci.microservice.MicroserviceConfiguration;

class GrizzlyEmbeddedServer implements EmbeddedServer {

	private final ResourceConfig resourceConfig;
	private final MicroserviceConfiguration configuration;
	
	@Inject
	GrizzlyEmbeddedServer(ResourceConfig resourceConfig, MicroserviceConfiguration configuration) {
		this.resourceConfig = resourceConfig;
		this.configuration = configuration;
	}

	@Override
	public void start() {
		try {
			GrizzlyHttpServerFactory.createHttpServer(buildUri(), resourceConfig).start();
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
