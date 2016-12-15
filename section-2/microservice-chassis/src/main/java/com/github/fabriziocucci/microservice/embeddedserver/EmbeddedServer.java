package com.github.fabriziocucci.microservice.embeddedserver;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class EmbeddedServer {

	public static void run(ResourceConfig resourceConfig) {
		try {
			URI baseUri = UriBuilder.fromUri("http://localhost").port(8080).build();
			HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, resourceConfig);
			server.start();
		} catch (Exception exception) {
			throw new IllegalStateException("Unable to start server!", exception);
		}
	}
	
}
