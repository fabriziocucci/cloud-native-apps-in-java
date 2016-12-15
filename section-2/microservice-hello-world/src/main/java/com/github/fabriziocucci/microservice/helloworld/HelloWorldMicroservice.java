package com.github.fabriziocucci.microservice.helloworld;

import com.github.fabriziocucci.microservice.Microservice;
import com.github.fabriziocucci.microservice.MicroserviceContext;

public class HelloWorldMicroservice extends Microservice<HelloWorldConfiguration> {

	public HelloWorldMicroservice() {
		super(HelloWorldConfiguration.class, HelloWorldBinder.class);
	}
	
	@Override
	protected void configure(MicroserviceContext<HelloWorldConfiguration> microserviceContext) {
		microserviceContext.getResourceConfig()
			.register(HelloWorldResource.class);
	}
	
	public static void main(String[] args) {
		new HelloWorldMicroservice().run();
	}
	
}
