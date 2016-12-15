package com.github.fabriziocucci.microservice.servicediscovery;

import java.net.URI;

public interface ServiceDiscoveryManager {

	void register();

	void deregister();
	
	URI discover(String serviceName);

}