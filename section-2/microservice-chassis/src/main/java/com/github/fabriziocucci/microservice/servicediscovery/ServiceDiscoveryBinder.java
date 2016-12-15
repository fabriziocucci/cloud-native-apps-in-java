package com.github.fabriziocucci.microservice.servicediscovery;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

import javax.inject.Inject;

import org.glassfish.hk2.api.Factory;
import org.glassfish.jersey.server.ResourceConfig;

import com.github.fabriziocucci.microservice.ChassisBinder;
import com.github.fabriziocucci.microservice.MicroserviceConfiguration;
import com.google.common.net.HostAndPort;
import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import com.orbitz.consul.model.agent.Registration;
import com.orbitz.consul.model.agent.Registration.RegCheck;

public class ServiceDiscoveryBinder extends ChassisBinder {

	@Inject
	public ServiceDiscoveryBinder(ResourceConfig resourceConfig) {
		resourceConfig.register(ServiceDiscoveryContainerLifecycleListener.class);
	}

	@Override
	protected void configure() {
		bindFactory(AgentClientFactory.class).to(AgentClient.class);
		bindFactory(RegistrationFactory.class).to(Registration.class);
		bind(ConsulServiceDiscoveryManager.class).to(ServiceDiscoveryManager.class);
	}

	private static class AgentClientFactory implements Factory<AgentClient> {
		
		private final ServiceDiscoveryConfiguration serviceDiscoveryConfiguration;
		
		@Inject
		private AgentClientFactory(MicroserviceConfiguration microserviceConfiguration) {
			this.serviceDiscoveryConfiguration = microserviceConfiguration.getServiceDiscoveryConfiguration();
		}
		
		@Override
		public AgentClient provide() {
			return Consul.builder()
					.withHostAndPort(HostAndPort.fromParts(serviceDiscoveryConfiguration.serverHost, serviceDiscoveryConfiguration.serverPort))
					.build()
					.agentClient();
		}

		@Override
		public void dispose(AgentClient instance) {
			// nothing interesting to do!
		}
		
	}
	
	private static class RegistrationFactory implements Factory<Registration> {

		private final ServiceDiscoveryConfiguration serviceDiscoveryConfiguration;
		
		@Inject
		private RegistrationFactory(MicroserviceConfiguration microserviceConfiguration) {
			this.serviceDiscoveryConfiguration = microserviceConfiguration.getServiceDiscoveryConfiguration();
		}

		@Override
		public Registration provide() {
			return ImmutableRegistration.builder()
					.id(serviceDiscoveryConfiguration.serviceId.orElseGet(this::getServiceId))
					.name(serviceDiscoveryConfiguration.serviceName)
					.address(serviceDiscoveryConfiguration.serviceHost.orElseGet(this::getServiceHost))
					.port(serviceDiscoveryConfiguration.servicePort)
					.check(RegCheck.http(getServiceHealthCheckUrl(), this.serviceDiscoveryConfiguration.serviceHealthCheckInterval))
					.build();
		}

		private String getServiceId() {
			return String.format("%s-%s", serviceDiscoveryConfiguration.serviceName, UUID.randomUUID());
		}
		
		private String getServiceHost() {
			try {
				return InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException unknownHostException) {
				throw new IllegalStateException(unknownHostException);
			}
		}
		
		private String getServiceHealthCheckUrl() {
			return String.format("http://%s:%d/%s", 
					serviceDiscoveryConfiguration.serviceHost.orElseGet(this::getServiceHost), 
					serviceDiscoveryConfiguration.servicePort, 
					serviceDiscoveryConfiguration.serviceHealthCheckPath);
		}

		@Override
		public void dispose(Registration instance) {
			// nothing interesting to do!
		}
		
	}
	
}
