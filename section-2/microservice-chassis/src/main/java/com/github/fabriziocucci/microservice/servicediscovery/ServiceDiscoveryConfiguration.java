package com.github.fabriziocucci.microservice.servicediscovery;

import java.util.Optional;

public class ServiceDiscoveryConfiguration {

	public String serverHost;
	
	public int serverPort;
	
	public Optional<String> serviceId = Optional.empty();
	
	public String serviceName;
	
	public Optional<String> serviceHost = Optional.empty();
	
	public int servicePort;
	
	public String serviceHealthCheckPath;
	
	public int serviceHealthCheckInterval;
	
}
