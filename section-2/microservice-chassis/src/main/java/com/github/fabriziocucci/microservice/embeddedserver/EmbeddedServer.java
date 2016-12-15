package com.github.fabriziocucci.microservice.embeddedserver;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class EmbeddedServer {

	public static void run(ResourceConfig resourceConfig, EmbeddedServerConfiguration embeddedServerConfiguration) {
		try {
			URI baseUri = buildUri(embeddedServerConfiguration);
			HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, resourceConfig);
			server.start();
		} catch (Exception exception) {
			throw new IllegalStateException("Unable to start server!", exception);
		}
	}
	
	private static URI buildUri(EmbeddedServerConfiguration embeddedServerConfiguration) {
		return UriBuilder.fromUri("http://host:port")
				.host(embeddedServerConfiguration.getHost())
				.port(embeddedServerConfiguration.getPort())
				.build();
	}
	
}
