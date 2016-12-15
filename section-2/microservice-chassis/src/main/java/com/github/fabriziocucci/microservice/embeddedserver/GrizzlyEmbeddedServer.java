package com.github.fabriziocucci.microservice.embeddedserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;

import com.github.fabriziocucci.microservice.MicroserviceConfiguration;

class GrizzlyEmbeddedServer implements EmbeddedServer {

	private final MicroserviceConfiguration configuration;
	private final ResourceConfig resourceConfig;
	private final ServiceLocator serviceLocator;

	@Inject
	GrizzlyEmbeddedServer(MicroserviceConfiguration configuration, ResourceConfig resourceConfig, ServiceLocator serviceLocator) {
		this.configuration = configuration;
		this.resourceConfig = resourceConfig;
		this.serviceLocator = serviceLocator;
	}

	@Override
	public void start() {
		try {
			WebappContext webappContext = new WebappContext(configuration.getServiceName());
			
			webappContext.addListener(new ServletContextListener() {
				@Override
				public void contextInitialized(ServletContextEvent sce) {
					sce.getServletContext().setAttribute(ServletProperties.SERVICE_LOCATOR, serviceLocator);
				}
				@Override
				public void contextDestroyed(ServletContextEvent sce) { }
			});
			
			ServletRegistration microserviceServletRegistration = webappContext.addServlet("microservice", new ServletContainer(resourceConfig));
			microserviceServletRegistration.addMapping("/microservice/*");

			ServletRegistration helloServletRegistration = webappContext.addServlet("hello", HelloServlet.class);
			helloServletRegistration.addMapping("/hello/*");

			HttpServer createHttpServer = GrizzlyHttpServerFactory.createHttpServer(buildUri(), false);
			webappContext.deploy(createHttpServer);
			createHttpServer.start();
		} catch (Exception exception) {
			throw new IllegalStateException("Unable to start server!", exception);
		}
	}

	private URI buildUri() {
		return UriBuilder.fromUri("http://host:port")
				.host(configuration.getEmbeddedServerConfiguration().getHost())
				.port(configuration.getEmbeddedServerConfiguration().getPort())
				.build();
	}
	
	@SuppressWarnings("serial")
	public static class HelloServlet extends HttpServlet {

		public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<h1>Hello Servlet</h1>");
		}

	}

}
