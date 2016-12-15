package com.github.fabriziocucci.microservice.greeting;

import com.github.fabriziocucci.microservice.Microservice;
import com.github.fabriziocucci.microservice.MicroserviceConfiguration;
import com.github.fabriziocucci.microservice.MicroserviceContext;

public class GreetingMicroservice extends Microservice<MicroserviceConfiguration> {

	public GreetingMicroservice() {
		super(MicroserviceConfiguration.class);
	}

	@Override
	protected void configure(MicroserviceContext<MicroserviceConfiguration> microserviceContext) {
		microserviceContext.getResourceConfig()
			.register(GreetingResource.class);
	}
	
	public static void main(String[] args) {
		new GreetingMicroservice().run();
	}
	
}
