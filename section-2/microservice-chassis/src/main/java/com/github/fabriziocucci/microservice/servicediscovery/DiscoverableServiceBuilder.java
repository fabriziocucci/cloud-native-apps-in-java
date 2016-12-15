package com.github.fabriziocucci.microservice.servicediscovery;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URI;

import javax.inject.Inject;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.client.proxy.WebResourceFactory;

public class DiscoverableServiceBuilder {

	private final ServiceDiscoveryManager serviceDiscoveryManager;
	
	@Inject
	public DiscoverableServiceBuilder(ServiceDiscoveryManager serviceDiscoveryManager) {
		this.serviceDiscoveryManager = serviceDiscoveryManager;
	}

	@SuppressWarnings("unchecked")
	public <T> T build(String serviceName, Class<T> serviceClass) {
		return (T) Proxy.newProxyInstance(serviceClass.getClassLoader(), new Class<?>[] { serviceClass }, new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				URI serviceUri = serviceDiscoveryManager.discover(serviceName);
				WebTarget serviceWebTarget = ClientBuilder.newClient().target(serviceUri);
				T service = WebResourceFactory.newResource(serviceClass, serviceWebTarget);
				return method.invoke(service, args);
			}
		});
	}
	
}
