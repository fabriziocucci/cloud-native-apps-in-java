package com.github.fabriziocucci.microservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.fabriziocucci.microservice.embeddedserver.EmbeddedServerConfiguration;
import com.github.fabriziocucci.microservice.servicediscovery.ServiceDiscoveryConfiguration;

public interface MicroserviceConfiguration {

	@JsonProperty("service.name")
	String getServiceName();
	
	@JsonProperty("embeddedServer")
	EmbeddedServerConfiguration getEmbeddedServerConfiguration();
	
	@JsonProperty("serviceDiscovery")
	ServiceDiscoveryConfiguration getServiceDiscoveryConfiguration();
	
}
