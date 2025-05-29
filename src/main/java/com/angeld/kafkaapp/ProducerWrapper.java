package com.angeld.kafkaapp;

import org.apache.kafka.clients.producer.KafkaProducer;

public class ProducerWrapper {
	private KafkaProducer<String, String> producer;
	private String name;
	private Long lastProduced;
	private int eventCount = 0;

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

	public Long getLastProduced() {
		return lastProduced;
	}

	public void setLastProduced(Long lastProduced) {
		this.lastProduced = lastProduced;
	}

	public int getEventCount() {
		return eventCount;
	}

	public void setEventCount(int eventCount) {
		this.eventCount = eventCount;
	}
}
