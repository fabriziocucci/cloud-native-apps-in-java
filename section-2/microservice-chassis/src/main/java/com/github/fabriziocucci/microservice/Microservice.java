package com.github.fabriziocucci.microservice;

import org.glassfish.jersey.server.ResourceConfig;

import com.github.fabriziocucci.microservice.configuration.ConfigurationLoader;
import com.github.fabriziocucci.microservice.embeddedserver.EmbeddedServer;

public abstract class Microservice<Configuration extends MicroserviceConfiguration> extends ResourceConfig {

	protected final Configuration configuration;

	protected Microservice(Class<Configuration> configurationClass) {
		this.configuration = ConfigurationLoader.load(configurationClass);
	}

	public void run() {
		EmbeddedServer.run(this, configuration.getEmbeddedServerConfiguration());
	}

}
