package com.github.fabriziocucci.microservice.servicediscovery;

import javax.inject.Inject;

import com.orbitz.consul.AgentClient;
import com.orbitz.consul.model.agent.Registration;

class ConsulServiceDiscoveryManager implements ServiceDiscoveryManager {
	
	private final AgentClient agentClient;
	private final Registration registration;

	@Inject
	ConsulServiceDiscoveryManager(AgentClient agentClient, Registration registration) {
		this.agentClient = agentClient;
		this.registration = registration;
	}

	@Override
	public void register() {
		agentClient.register(registration);
	}
	
	@Override
	public void deregister() {
		agentClient.deregister(registration.getId());
	}
	
}
