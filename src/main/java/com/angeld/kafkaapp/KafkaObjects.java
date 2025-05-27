package com.angeld.kafkaapp;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaObjects {
	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaObjects.class);

	public static final Properties PRODUCER_PROPERTIES;
	public static final Properties CONSUMER_PROPERTIES;

	static {
		try {
			PRODUCER_PROPERTIES = new Properties();
			PRODUCER_PROPERTIES.load(KafkaObjects.class.getResourceAsStream("/producer.properties"));

			CONSUMER_PROPERTIES = new Properties();
			CONSUMER_PROPERTIES.load(KafkaObjects.class.getResourceAsStream("/consumer.properties"));
		} catch (Exception e) {
			LOGGER.error("Something went wrong...", e);
			throw new RuntimeException(e);
		}
	}

	public static final Map<String, ProducerWrapper> mapProducers = new HashMap<>();
	public static final Map<String, ConsumerWrapper> mapConsumers = new HashMap<>();

	public static final String PRODUCER = "PRODUCER";
	public static final String CONSUMER = "CONSUMER";

}
