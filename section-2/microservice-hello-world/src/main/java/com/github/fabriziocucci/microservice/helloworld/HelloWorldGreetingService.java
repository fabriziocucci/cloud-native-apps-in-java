package com.github.fabriziocucci.microservice.helloworld;

import com.github.fabriziocucci.microservice.ChassisConstants;

public class HelloWorldGreetingService implements GreetingService {
	
	@Override
	public String greet() {
		return ChassisConstants.HELLO_CHASSIS;
	}
	
}
