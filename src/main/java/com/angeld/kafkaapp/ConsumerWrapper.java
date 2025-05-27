package com.angeld.kafkaapp;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;;

public class ConsumerWrapper {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerWrapper.class);

	private KafkaConsumer<String, String> consumer;
	private String name;
	private List<String> events = new ArrayList<>();
	private boolean shouldRun = true;
	private Instant lastConsumed;

	public ConsumerWrapper(String name) {
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(KafkaObjects.CONSUMER_PROPERTIES);
		consumer.subscribe(Arrays.asList(KafkaObjects.CONSUMER_PROPERTIES.getProperty("topic")));

		Runnable r = new Runnable() {
			@Override
			public void run() {
				while (shouldRun) {
					ConsumerRecords<String, String> val = consumer.poll(Duration.ofMillis(100));

					for (ConsumerRecord<String, String> record : val) {
						LOGGER.info("offset = {}, key = {}, value = {}", record.offset(), record.key(), record.value());
						events.add(record.value());
						lastConsumed = Instant.now();
					}
				}
			}
		};

		new Thread(r).start();

		this.consumer = consumer;
		this.name = name;
	}

	public ConsumerWrapper(String name, Consumer<ConsumerRecords<String, String>> eventConsumer) {
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(KafkaObjects.CONSUMER_PROPERTIES);
		consumer.subscribe(Arrays.asList(KafkaObjects.CONSUMER_PROPERTIES.getProperty("topic")));

		Runnable r = new Runnable() {
			@Override
			public void run() {
				while (shouldRun) {
					eventConsumer.accept(consumer.poll(Duration.ofMillis(100)));
				}
			}
		};

		new Thread(r).start();

		this.consumer = consumer;
		this.name = name;
	}

	public KafkaConsumer<String, String> getConsumer() {
		return consumer;
	}

	public void setConsumer(KafkaConsumer<String, String> consumer) {
		this.consumer = consumer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getEvents() {
		return events;
	}

	public void setEvents(List<String> events) {
		this.events = events;
	}

	public boolean getShouldRun() {
		return shouldRun;
	}

	public void setShouldRun(boolean shouldRun) {
		this.shouldRun = shouldRun;
	}

	public Instant getLastConsumed() {
		return lastConsumed;
	}

	public void setLastConsumed(Instant lastConsumed) {
		this.lastConsumed = lastConsumed;
	}

}
