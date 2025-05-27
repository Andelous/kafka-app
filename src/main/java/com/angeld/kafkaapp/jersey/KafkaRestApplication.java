package com.angeld.kafkaapp.jersey;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaRestApplication extends ResourceConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaRestApplication.class);

	public KafkaRestApplication() {
		LOGGER.info("Initializing Kafka REST Application...");

		packages("com.angeld.kafkaapp.jersey");
		register(JacksonFeature.class);

		LOGGER.info("Initialized Kafka REST Application");
	}
}
