package com.github.fabriziocucci.microservice.helloworld;

import javax.inject.Inject;

import com.codahale.metrics.health.HealthCheck;
import com.github.fabriziocucci.microservice.ChassisBinder;
import com.github.fabriziocucci.microservice.servicediscovery.DiscoverableServiceBuilder;

public class HelloWorldBinder extends ChassisBinder {

	private final HelloWorldConfiguration configuration;
	private final DiscoverableServiceBuilder discoverableServiceBuilder;

	@Inject
	public HelloWorldBinder(HelloWorldConfiguration configuration, DiscoverableServiceBuilder discoverableServiceBuilder) {
		this.configuration = configuration;
		this.discoverableServiceBuilder = discoverableServiceBuilder;
	}

	@Override
	protected void configure() {
		bind(greetingService()).to(GreetingService.class);
		bind(GreetingServiceHealthCheck.class).to(HealthCheck.class);
	}

	private GreetingService greetingService() {
		return discoverableServiceBuilder.build(configuration.getGreetingServiceName(), GreetingService.class);
	}
	
}
