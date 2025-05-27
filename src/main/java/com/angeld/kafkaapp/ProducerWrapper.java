package com.angeld.kafkaapp;

import java.time.Instant;

import org.apache.kafka.clients.producer.KafkaProducer;

public class ProducerWrapper {
	private KafkaProducer<String, String> producer;
	private String name;
	private Instant lastProduced;

	public ProducerWrapper(String name) {
		KafkaProducer<String, String> producer = new KafkaProducer<>(KafkaObjects.PRODUCER_PROPERTIES);

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
