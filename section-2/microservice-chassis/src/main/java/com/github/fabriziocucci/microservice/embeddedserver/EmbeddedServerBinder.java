package com.github.fabriziocucci.microservice.embeddedserver;

import com.github.fabriziocucci.microservice.ChassisBinder;

public class EmbeddedServerBinder extends ChassisBinder {

	@Override
	protected void configure() {
		bind(GrizzlyEmbeddedServer.class).to(EmbeddedServer.class);
	}

}
