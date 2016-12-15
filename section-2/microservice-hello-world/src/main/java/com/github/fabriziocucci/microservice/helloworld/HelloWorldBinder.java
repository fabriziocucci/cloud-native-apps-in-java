package com.github.fabriziocucci.microservice.helloworld;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.github.fabriziocucci.microservice.MicroserviceContext;
import com.github.fabriziocucci.microservice.servicediscovery.DiscoverableServiceBuilder;

public class HelloWorldBinder extends AbstractBinder {

	private final HelloWorldConfiguration configuration;
	private final DiscoverableServiceBuilder discoverableServiceBuilder;
	

	public HelloWorldBinder(MicroserviceContext<HelloWorldConfiguration> microserviceContext) {
		this.configuration = microserviceContext.getConfiguration();
		this.discoverableServiceBuilder = microserviceContext.getDiscoverableServiceBuilder();
	}

	@Override
	protected void configure() {
		bind(greetingService()).to(GreetingService.class);
	}
	
	private GreetingService greetingService() {
		return discoverableServiceBuilder.build(configuration.getGreetingServiceName(), GreetingService.class);
	}
	
}
