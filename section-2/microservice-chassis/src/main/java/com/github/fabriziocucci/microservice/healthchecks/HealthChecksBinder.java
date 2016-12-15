package com.github.fabriziocucci.microservice.healthchecks;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.glassfish.hk2.api.Factory;
import org.glassfish.jersey.server.ResourceConfig;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.github.fabriziocucci.microservice.ChassisBinder;

public class HealthChecksBinder extends ChassisBinder {

	@Inject
	public HealthChecksBinder(ResourceConfig resourceConfig) {
		resourceConfig.register(HealthChecksResource.class);
	}
	
	@Override
	protected void configure() {
		bind(LowDiskSpaceHealthCheck.class).to(HealthCheck.class);
		bindFactory(HealthCheckRegistryFactory.class).to(HealthCheckRegistry.class).in(Singleton.class);
	}

	private static class HealthCheckRegistryFactory implements Factory<HealthCheckRegistry> {

		private final Iterable<HealthCheck> healthChecks;
		
		@Inject
		public HealthCheckRegistryFactory(Iterable<HealthCheck> healthChecks) {
			this.healthChecks = healthChecks;
		}

		@Override
		public HealthCheckRegistry provide() {
			HealthCheckRegistry healthCheckRegistry = new HealthCheckRegistry();
			for (HealthCheck healthCheck : healthChecks) {
				healthCheckRegistry.register(healthCheck.getClass().getName(), healthCheck);
			}
			return healthCheckRegistry;
		}

		@Override
		public void dispose(HealthCheckRegistry instance) {
			// nothing interesting to do!
		}
		
	}
	
}
