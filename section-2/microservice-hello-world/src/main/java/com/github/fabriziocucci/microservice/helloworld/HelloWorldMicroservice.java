package com.github.fabriziocucci.microservice.helloworld;

import com.github.fabriziocucci.microservice.Microservice;

public class HelloWorldMicroservice extends Microservice<HelloWorldConfiguration> {

	public HelloWorldMicroservice() {
		super(HelloWorldConfiguration.class);
		register(new HelloWorldBinder(configuration));
		register(HelloWorldResource.class);
	}
	
	public static void main(String[] args) {
		new HelloWorldMicroservice().run();
	}
	
}
