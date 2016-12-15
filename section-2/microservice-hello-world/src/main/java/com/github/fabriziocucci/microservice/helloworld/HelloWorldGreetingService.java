package com.github.fabriziocucci.microservice.helloworld;

public class HelloWorldGreetingService implements GreetingService {
	
	private final String greeting;
	
	public HelloWorldGreetingService(String greeting) {
		this.greeting = greeting;
	}

	@Override
	public String greet() {
		return greeting;
	}
	
}
