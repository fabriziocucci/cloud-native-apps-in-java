package com.github.fabriziocucci.microservice.helloworld;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class HelloWorldBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(HelloWorldGreetingService.class).to(GreetingService.class);
	}
	
}
