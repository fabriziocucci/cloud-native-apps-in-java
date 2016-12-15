package com.github.fabriziocucci.microservice.helloworld;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class HelloWorldBinder extends AbstractBinder {

	private final HelloWorldConfiguration helloWorldConfiguration;
	
	public HelloWorldBinder(HelloWorldConfiguration helloWorldConfiguration) {
		this.helloWorldConfiguration = helloWorldConfiguration;
	}

	@Override
	protected void configure() {
		bind(greetingService()).to(GreetingService.class);
	}
	
	private GreetingService greetingService() {
		return new HelloWorldGreetingService(helloWorldConfiguration.getGreeting());
	}
	
}
