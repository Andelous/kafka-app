package com.angeld.kafkaapp.servlet;

import java.io.IOException;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 4427019123484161199L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		System.out.println("Servlet inicializado.");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();

		String tipo = req.getParameter("tipo");

		switch (tipo) {
		case "PRODUCTOR":
			// Crear productor de Kafka
			Properties props = new Properties();
			props.put("bootstrap.servers", "192.168.1.100:9092");
			props.put("linger.ms", 1);
			props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
			props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

			Producer<String, String> producer = new KafkaProducer<>(props);
			for (int i = 0; i < 100; i++)
				producer.send(new ProducerRecord<String, String>("quickstart-events", Integer.toString(i), Integer.toString(i)));

			producer.close();

			break;

		case "CONSUMIDOR":
			// Crear consumidor de Kafka
			break;
		}

		session.setAttribute("tipo-cliente", tipo);

		resp.sendRedirect("/index.jsp");
	}
}
