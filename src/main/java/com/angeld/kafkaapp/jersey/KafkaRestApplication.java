package com.angeld.kafkaapp.jersey;

import org.glassfish.jersey.server.ResourceConfig;

public class KafkaRestApplication extends ResourceConfig {
	public KafkaRestApplication() {
		packages("com.angeld.kafkaapp.jersey");
	}
}
