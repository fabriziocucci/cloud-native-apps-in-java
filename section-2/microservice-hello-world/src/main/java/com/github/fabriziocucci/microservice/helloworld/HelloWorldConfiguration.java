package com.github.fabriziocucci.microservice.helloworld;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.fabriziocucci.microservice.MicroserviceConfiguration;

public interface HelloWorldConfiguration extends MicroserviceConfiguration {

	@JsonProperty("service.greetingServiceName")
	String getGreetingServiceName();
	
}
