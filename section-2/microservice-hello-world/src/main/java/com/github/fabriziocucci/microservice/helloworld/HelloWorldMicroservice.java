package com.github.fabriziocucci.microservice.helloworld;

import com.github.fabriziocucci.microservice.Microservice;
import com.github.fabriziocucci.microservice.MicroserviceContext;

public class HelloWorldMicroservice extends Microservice<HelloWorldConfiguration> {

	public HelloWorldMicroservice() {
		super(HelloWorldConfiguration.class);
	}

	@Override
	protected void configure(MicroserviceContext<HelloWorldConfiguration> microserviceContext) {
		microserviceContext.getResourceConfig()
			.register(new HelloWorldBinder(microserviceContext.getConfiguration()))
			.register(HelloWorldResource.class);
	}
	
	public static void main(String[] args) {
		new HelloWorldMicroservice().run();
	}
	
}
