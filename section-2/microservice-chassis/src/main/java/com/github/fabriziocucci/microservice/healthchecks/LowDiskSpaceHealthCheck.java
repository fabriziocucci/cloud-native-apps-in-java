package com.github.fabriziocucci.microservice.healthchecks;

import java.io.File;

import com.codahale.metrics.health.HealthCheck;

class LowDiskSpaceHealthCheck extends HealthCheck{
	
	private static final long LOW_DISK_SPACE_THREASHOLD = 15 * 1024 * 1024;
	
	@Override
	protected Result check() throws Exception {
		long freeSpace = new File(".").getFreeSpace();
		return (freeSpace > LOW_DISK_SPACE_THREASHOLD) ?
				Result.healthy("Normal disk space: %d", freeSpace) :
				Result.unhealthy("Low disk space: %d", freeSpace);
	}

}
