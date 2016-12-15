package com.github.fabriziocucci.microservice;

import javax.inject.Inject;

import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.BuilderHelper;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import com.github.fabriziocucci.microservice.configuration.ConfigurationBinder;
import com.github.fabriziocucci.microservice.embeddedserver.EmbeddedServerBinder;

class MicroserviceContextBinder<Configuration extends MicroserviceConfiguration> extends AbstractBinder {
	
	private final Class<Configuration> configurationClass;
	
	MicroserviceContextBinder(Class<Configuration> configurationClass) {
		this.configurationClass = configurationClass;
	}

	@Override
	protected void configure() {
		bind(BuilderHelper.createConstantDescriptor(configurationClass));
		bind(BuilderHelper.createConstantDescriptor(new ResourceConfig()));
		bind(ConfigurationBinder.class).to(ChassisBinder.class);
		bind(EmbeddedServerBinder.class).to(ChassisBinder.class);
		bindFactory(MicroserviceContextFactory.class).to(MicroserviceContext.class);
	}

	@SuppressWarnings("rawtypes")
	private static class MicroserviceContextFactory implements Factory<MicroserviceContext> {
		
		private final ServiceLocator serviceLocator;
		private final Iterable<ChassisBinder> chassisBinders;
		
		@Inject
		public MicroserviceContextFactory(ServiceLocator serviceLocator, Iterable<ChassisBinder> chassisBinders) {
			this.serviceLocator = serviceLocator;
			this.chassisBinders = chassisBinders;
		}

		@Override
		public MicroserviceContext provide() {
			for (ChassisBinder chassisBinder : chassisBinders) {
				ServiceLocatorUtilities.bind(serviceLocator, chassisBinder);
			}
			return serviceLocator.createAndInitialize(MicroserviceContext.class);
		}

		@Override
		public void dispose(MicroserviceContext instance) {
			// nothing interesting to do!
		}
		
	}
	
}
