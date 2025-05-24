package com.angeld.kafkaapp;

import java.util.HashMap;
import java.util.Map;

public class KafkaObjects {
	public static final Map<String, ProducerWrapper> mapProducers = new HashMap<>();
	public static final Map<String, ConsumerWrapper> mapConsumers = new HashMap<>();
}
