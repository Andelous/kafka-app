package com.angeld.kafkaapp;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;;

public class ConsumerWrapper {
	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final TypeReference<HashMap<String, String>> REF = new TypeReference<HashMap<String, String>>() {
	};

	private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerWrapper.class);

	private KafkaConsumer<String, String> consumer;
	private String name;
	private List<String> events = new ArrayList<>();
	private boolean shouldRun = true;
	private Long lastConsumed;

	private MongoClient mongoClient;
	private MongoDatabase database;
	private MongoCollection<Document> collection;

	public ConsumerWrapper(String name) {
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(KafkaObjects.CONSUMER_PROPERTIES);
		consumer.subscribe(Arrays.asList(KafkaObjects.CONSUMER_PROPERTIES.getProperty("topic")));

		mongoClient = MongoClients.create(KafkaObjects.CONSUMER_PROPERTIES.getProperty("mongo.uri"));
		database = mongoClient.getDatabase(KafkaObjects.CONSUMER_PROPERTIES.getProperty("mongo.database"));
		collection = database.getCollection(KafkaObjects.CONSUMER_PROPERTIES.getProperty("mongo.collection"));

		Runnable r = new Runnable() {
			@Override
			public void run() {
				try {
					while (shouldRun) {
						ConsumerRecords<String, String> val = consumer.poll(Duration.ofMillis(100));

						for (ConsumerRecord<String, String> record : val) {
							LOGGER.info("Processing new EVENT key = {}, value = {}", record.key(), record.value());
							events.add(record.value());

							Map<String, String> event = MAPPER.readValue(record.value(), REF);

							Document doc1 = new Document(event).append(KafkaObjects.CONSUMER,
									ConsumerWrapper.this.name);
							InsertOneResult result = collection.insertOne(doc1);

							LOGGER.info("Inserted a document with the following id: "
									+ result.getInsertedId().asObjectId().getValue());

							lastConsumed = Instant.now().toEpochMilli();
						}
					}
				} catch (Exception e) {
					LOGGER.error("Something went wrong...", e);
					throw new RuntimeException(e);
				}
			}
		};

		Thread t = new Thread(r);
		t.setName(KafkaObjects.CONSUMER + "-" + name);
		t.start();

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

	public Long getLastConsumed() {
		return lastConsumed;
	}

	public void setLastConsumed(Long lastConsumed) {
		this.lastConsumed = lastConsumed;
	}
}
