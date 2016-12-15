package com.github.fabriziocucci.microservice.helloworld;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/")
public class HelloWorldMicroservice extends ResourceConfig {

	public HelloWorldMicroservice() {
		register(new HelloWorldBinder());
		register(HelloWorldResource.class);
	}
	
}
