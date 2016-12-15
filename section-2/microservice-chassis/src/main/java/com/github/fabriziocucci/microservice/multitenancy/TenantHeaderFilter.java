package com.github.fabriziocucci.microservice.multitenancy;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

public class TenantHeaderFilter implements ContainerRequestFilter, ContainerResponseFilter, ClientRequestFilter {

	private static final Logger LOGGER = LogManager.getLogger();
	
	private static final String HEADER_KEY_TENANT_ID = "X-Tenant-Id";	
	
	@Override
	public void filter(ContainerRequestContext containerRequestContext) throws IOException {
		
		// we don't care about the X-Tenant-Id header in case of health checks!
		if (containerRequestContext.getUriInfo().getPath().endsWith("health")) {
			return;
		}
		
		List<String> tenantIdValues = containerRequestContext.getHeaders().get(HEADER_KEY_TENANT_ID);
		if (tenantIdValues != null && tenantIdValues.size() == 1) {
			String tenantIdValue = tenantIdValues.get(0);
			LOGGER.debug("Saving X-Tenant-Id header, current value is {}", tenantIdValue);
			ThreadContext.put(HEADER_KEY_TENANT_ID, tenantIdValue);
		} else {
			LOGGER.warn("Expected single value for X-Tenant-Id header, found {}", tenantIdValues);
			containerRequestContext.abortWith(Response.status(Status.BAD_REQUEST).build());
		}
	}

	@Override
	public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
		String tenantIdValue = ThreadContext.get(HEADER_KEY_TENANT_ID);
		if (tenantIdValue != null) {
			LOGGER.debug("Clearing X-Tenant-Id header, current value is {}", tenantIdValue);
			ThreadContext.remove(HEADER_KEY_TENANT_ID);
		}
	}
	
	@Override
	public void filter(ClientRequestContext clientRequestContext) throws IOException {
		
		// we don't care about the X-Tenant-Id header in case of health checks!
		if (clientRequestContext.getUri().getPath().endsWith("health")) {
			return;
		}
		
		String tenantIdValue = ThreadContext.get(HEADER_KEY_TENANT_ID);
		if (tenantIdValue != null) {
			LOGGER.debug("Forwarding X-Tenant-Id header, current value is {}", tenantIdValue);
			clientRequestContext.getHeaders().add(HEADER_KEY_TENANT_ID, tenantIdValue);
		} else {
			LOGGER.warn("Unable to set X-Tenant-Id header, value not found");
			clientRequestContext.abortWith(Response.status(Status.BAD_REQUEST).build());
		}
	}
	
}