package com.github.fabriziocucci.microservice.servicediscovery;

import javax.inject.Inject;

import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;

class ServiceDiscoveryContainerLifecycleListener implements ContainerLifecycleListener {
	
	private final ServiceDiscoveryManager serviceDiscoveryManager;
	
	@Inject
	ServiceDiscoveryContainerLifecycleListener(ServiceDiscoveryManager serviceDiscoveryManager) {
		this.serviceDiscoveryManager = serviceDiscoveryManager;
	}
	
	@Override
	public void onStartup(Container container) {
		serviceDiscoveryManager.register();
		Runtime.getRuntime().addShutdownHook(new Thread(serviceDiscoveryManager::deregister));
	}

	@Override
	public void onReload(Container container) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onShutdown(Container container) {
		serviceDiscoveryManager.deregister();
	}

}
