package com.angeld.kafkaapp.jersey;

import java.net.URI;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.angeld.kafkaapp.ProducerWrapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;

@Path("")
public class KafkaRestActions {
	private static final String PRODUCER = "PRODUCER";
	private static final String CONSUMER = "CONSUMER";

	public KafkaRestActions() {
		System.out.println("Instantiating Kafka Rest Actions");
	}

	@GET
	public String meow() {
		return "MEOW";
	}

	@GET
	@Path("/produce")
	public Response produce(@Context HttpServletRequest request) {
		ProducerWrapper pw = (ProducerWrapper) request.getSession().getAttribute(PRODUCER);

		Producer<String, String> producer = pw.getProducer();

		producer.send(
				new ProducerRecord<String, String>("quickstart-events", "key", request.getParameter("temperature")));

		return Response.temporaryRedirect(URI.create("/producer.jsp?event=true")).build();
	}
}
