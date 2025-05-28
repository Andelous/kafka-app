package com.angeld.kafkaapp.jersey;

import static com.angeld.kafkaapp.KafkaObjects.CONSUMER;
import static com.angeld.kafkaapp.KafkaObjects.PRODUCER;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.angeld.kafkaapp.ConsumerWrapper;
import com.angeld.kafkaapp.KafkaObjects;
import com.angeld.kafkaapp.ProducerWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("")
public class KafkaRestActions {
	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaRestActions.class);
	private static final ObjectMapper MAPPER = new ObjectMapper();

	public KafkaRestActions() {
//		LOGGER.info("Initialized Kafka REST Actions");
	}

	@GET
	public String meow() {
		return "MEOW";
	}

	@GET
	@Path("/produce")
	public Response produce(@Context HttpServletRequest request) {
		ProducerWrapper pw = (ProducerWrapper) request.getSession().getAttribute(PRODUCER);

		LOGGER.info("Received PRODUCE request for producer {}", pw.getName());

		Producer<String, String> producer = pw.getProducer();

		Map<String, String> mapJson = new HashMap<>();
		mapJson.put("temperature", request.getParameter("temperature"));
		mapJson.put(PRODUCER, pw.getName());

		String json;
		try {
			json = MAPPER.writeValueAsString(mapJson);
		} catch (JsonProcessingException e) {
			LOGGER.error("Something went wrong...", e);
			throw new RuntimeException(e);
		}

		producer.send(new ProducerRecord<String, String>(KafkaObjects.PRODUCER_PROPERTIES.getProperty("topic"),
				UUID.randomUUID().toString(), json));
		pw.setLastProduced(Instant.now());
		KafkaObjects.EVENTS.add(json);

		LOGGER.info("Produced event successfully by producer {}", pw.getName());

		return Response.temporaryRedirect(URI.create("/producer.jsp?event=true")).build();
	}

	@GET
	@Path("/events")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listEvents(@Context HttpServletRequest request) {
		ConsumerWrapper cw = (ConsumerWrapper) request.getSession().getAttribute(CONSUMER);

		List<String> events = cw.getEvents();

		return Response.ok().entity(events).build();
	}

	@GET
	@Path("/all-data")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listAllData() {
		Map<String, Object> data = new HashMap<>();

		data.put("producers", KafkaObjects.MAP_PRODUCERS);
		data.put("consumers", KafkaObjects.MAP_CONSUMERS);
		data.put("events", KafkaObjects.EVENTS);

		return Response.ok().entity(data).build();
	}
}
