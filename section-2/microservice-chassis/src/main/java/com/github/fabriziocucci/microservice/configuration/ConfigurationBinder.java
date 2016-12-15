package com.github.fabriziocucci.microservice.configuration;

import javax.inject.Inject;

import com.github.fabriziocucci.microservice.ChassisBinder;
import com.github.fabriziocucci.microservice.MicroserviceConfiguration;

public class ConfigurationBinder<Configuration extends MicroserviceConfiguration> extends ChassisBinder {

	private final Class<Configuration> configurationClass;

	@Inject
	public ConfigurationBinder(Class<Configuration> configurationClass) {
		this.configurationClass = configurationClass;
	}

	@Override
	protected void configure() {
		Configuration configuration = Yacl4jConfigurationLoader.load(configurationClass);
		bind(configuration).to(MicroserviceConfiguration.class);
	}
	
}
