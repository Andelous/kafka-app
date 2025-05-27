package com.angeld.kafkaapp.jersey;

import static com.angeld.kafkaapp.KafkaObjects.CONSUMER;
import static com.angeld.kafkaapp.KafkaObjects.PRODUCER;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.angeld.kafkaapp.ConsumerWrapper;
import com.angeld.kafkaapp.KafkaObjects;
import com.angeld.kafkaapp.ProducerWrapper;

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

	public KafkaRestActions() {
		LOGGER.info("Initialized Kafka REST Actions");
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

		producer.send(new ProducerRecord<String, String>(KafkaObjects.PRODUCER_PROPERTIES.getProperty("topic"),
				UUID.randomUUID().toString(), request.getParameter("temperature")));
		pw.setLastProduced(Instant.now());

		LOGGER.info("Produced event successfully by producer {}", pw.getName());

		return Response.temporaryRedirect(URI.create("/producer.jsp?event=true")).build();
	}

	@GET
	@Path("/events")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listEvents(@Context HttpServletRequest request) {
		ConsumerWrapper cw = (ConsumerWrapper) request.getSession().getAttribute(CONSUMER);

		List<String> events = cw.getEvents();

		return Response.ok().entity(events.toArray()).build();
	}
}
