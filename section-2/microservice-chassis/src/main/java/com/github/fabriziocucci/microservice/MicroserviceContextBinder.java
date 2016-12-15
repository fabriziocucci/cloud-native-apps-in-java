package com.github.fabriziocucci.microservice;

import java.util.Arrays;

import javax.inject.Inject;

import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.BuilderHelper;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import com.github.fabriziocucci.microservice.configuration.ConfigurationBinder;
import com.github.fabriziocucci.microservice.embeddedserver.EmbeddedServerBinder;
import com.github.fabriziocucci.microservice.healthchecks.HealthChecksBinder;
import com.github.fabriziocucci.microservice.multitenancy.MultitenancyBinder;
import com.github.fabriziocucci.microservice.servicediscovery.ServiceDiscoveryBinder;

class MicroserviceContextBinder<Configuration extends MicroserviceConfiguration> extends AbstractBinder {
	
	private final Class<Configuration> configurationClass;
	private final Class<? extends ChassisBinder>[] externalChassisBinderClasses;
	
	MicroserviceContextBinder(Class<Configuration> configurationClass, Class<? extends ChassisBinder>[] externalChassisBinderClasses) {
		this.configurationClass = configurationClass;
		this.externalChassisBinderClasses = externalChassisBinderClasses;
	}

	@Override
	protected void configure() {
		bindInternalChassisBinderClasses();
		bindExternalChassisBinderClasses();
		bindFactory(MicroserviceContextFactory.class).to(MicroserviceContext.class);
	}
	
	private void bindInternalChassisBinderClasses() {
		bind(BuilderHelper.createConstantDescriptor(configurationClass));
		bind(BuilderHelper.createConstantDescriptor(new ResourceConfig()));
		bind(ConfigurationBinder.class).to(ChassisBinder.class);
		bind(EmbeddedServerBinder.class).to(ChassisBinder.class);
		bind(ServiceDiscoveryBinder.class).to(ChassisBinder.class);
		bind(HealthChecksBinder.class).to(ChassisBinder.class);
		bind(MultitenancyBinder.class).to(ChassisBinder.class);
	}
	
	private void bindExternalChassisBinderClasses() {
		Arrays.stream(externalChassisBinderClasses)
			.forEach(externalChassisBinderClass -> bind(externalChassisBinderClass).to(ChassisBinder.class));
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
