package com.github.fabriziocucci.microservice;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;

import com.github.fabriziocucci.microservice.embeddedserver.EmbeddedServer;

public abstract class Microservice<Configuration extends MicroserviceConfiguration> {

	private final ServiceLocator serviceLocator;
	private final MicroserviceContext<Configuration> microserviceContext;
	
	protected Microservice(Class<Configuration> configurationClass) {
		this.serviceLocator = ServiceLocatorUtilities.bind("chassis", new MicroserviceContextBinder<>(configurationClass));
		this.microserviceContext = serviceLocator.getService(new TypeLiteral<MicroserviceContext<Configuration>>() {}.getType());
	}
	
	public void run() {
		configure(microserviceContext);
		startEmbeddedServer();
	}

	protected abstract void configure(MicroserviceContext<Configuration> microserviceContext);
	
	private void startEmbeddedServer() {
		this.serviceLocator.getService(EmbeddedServer.class).start();
	}
	
}
