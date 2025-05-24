package com.angeld.kafkaapp;

import java.time.Instant;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;

public class ProducerWrapper {
	private KafkaProducer<String, String> producer;
	private String name;
	private Instant lastProduced;

	public ProducerWrapper(String name) {
		super();

		Properties props = new Properties();
		props.put("bootstrap.servers", "192.168.1.100:9092");
		props.put("linger.ms", 1);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		KafkaProducer<String, String> producer = new KafkaProducer<>(props);

		this.producer = producer;
		this.name = name;
	}

	public KafkaProducer<String, String> getProducer() {
		return producer;
	}

	public void setProducer(KafkaProducer<String, String> producer) {
		this.producer = producer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Instant getLastProduced() {
		return lastProduced;
	}

	public void setLastProduced(Instant lastProduced) {
		this.lastProduced = lastProduced;
	}
}
