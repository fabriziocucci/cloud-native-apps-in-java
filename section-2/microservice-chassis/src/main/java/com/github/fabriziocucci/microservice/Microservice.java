package com.github.fabriziocucci.microservice;

import org.glassfish.jersey.server.ResourceConfig;

import com.github.fabriziocucci.microservice.embeddedserver.EmbeddedServer;

public abstract class Microservice extends ResourceConfig {

	public void run() {
		EmbeddedServer.run(this);
	}
	
}
