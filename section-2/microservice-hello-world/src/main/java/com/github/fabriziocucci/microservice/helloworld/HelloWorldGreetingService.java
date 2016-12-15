package com.github.fabriziocucci.microservice.helloworld;

public class HelloWorldGreetingService implements GreetingService {
	
	@Override
	public String greet() {
		return "Hello World";
	}
	
}
