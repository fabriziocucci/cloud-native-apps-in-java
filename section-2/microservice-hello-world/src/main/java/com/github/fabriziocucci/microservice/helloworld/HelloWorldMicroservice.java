package com.github.fabriziocucci.microservice.helloworld;

import com.github.fabriziocucci.microservice.Microservice;

public class HelloWorldMicroservice extends Microservice {

	public HelloWorldMicroservice() {
		register(new HelloWorldBinder());
		register(HelloWorldResource.class);
	}
	
	public static void main(String[] args) {
		new HelloWorldMicroservice().run();
	}
	
}
