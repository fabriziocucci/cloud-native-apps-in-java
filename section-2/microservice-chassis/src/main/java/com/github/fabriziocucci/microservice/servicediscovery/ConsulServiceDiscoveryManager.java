package com.github.fabriziocucci.microservice.servicediscovery;

import java.net.URI;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;
import javax.ws.rs.core.UriBuilder;

import com.orbitz.consul.AgentClient;
import com.orbitz.consul.HealthClient;
import com.orbitz.consul.model.ConsulResponse;
import com.orbitz.consul.model.agent.Registration;
import com.orbitz.consul.model.health.ServiceHealth;

class ConsulServiceDiscoveryManager implements ServiceDiscoveryManager {
	
	private final AgentClient agentClient;
	private final Registration registration;
	private final HealthClient healthClient;
	private final Random random;

	@Inject
	ConsulServiceDiscoveryManager(AgentClient agentClient, Registration registration, HealthClient healthClient) {
		this.agentClient = agentClient;
		this.registration = registration;
		this.healthClient = healthClient;
		this.random = new Random();
	}

	@Override
	public void register() {
		agentClient.register(registration);
	}
	
	@Override
	public void deregister() {
		agentClient.deregister(registration.getId());
	}
	
	@Override
	public URI discover(String serviceName) {
		ConsulResponse<List<ServiceHealth>> response = healthClient.getHealthyServiceInstances(serviceName);
		List<ServiceHealth> healthyServiceInstances = response.getResponse();
		ServiceHealth healtyServiceInstance = healthyServiceInstances.get(random.nextInt(healthyServiceInstances.size()));
		return UriBuilder.fromUri("http://host:port")
				.host(healtyServiceInstance.getService().getAddress())
				.port(healtyServiceInstance.getService().getPort())
				.build();
	}
	
}
