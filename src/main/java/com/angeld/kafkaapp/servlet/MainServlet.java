package com.angeld.kafkaapp.servlet;

import static com.angeld.kafkaapp.KafkaObjects.CONSUMER;
import static com.angeld.kafkaapp.KafkaObjects.PRODUCER;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.angeld.kafkaapp.ConsumerWrapper;
import com.angeld.kafkaapp.KafkaObjects;
import com.angeld.kafkaapp.ProducerWrapper;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 4427019123484161199L;
	private static final Logger LOGGER = LoggerFactory.getLogger(MainServlet.class);

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		LOGGER.info("Initialized main servlet");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();

		String type = req.getParameter("type");
		String name = req.getParameter("name");

		if (type != null && !type.isBlank()) {
			LOGGER.info("Instantiating new Kafka object... {}-{}", type, name);
			switch (type) {
			case PRODUCER:
				ProducerWrapper pw = new ProducerWrapper(name);
				KafkaObjects.mapProducers.put(name, pw);
				session.setAttribute(PRODUCER, pw);
				break;

			case CONSUMER:
				ConsumerWrapper cw = new ConsumerWrapper(name);
				KafkaObjects.mapConsumers.put(name, cw);
				session.setAttribute(CONSUMER, cw);
				break;
			}
		} else {
			ProducerWrapper producer = (ProducerWrapper) session.getAttribute(PRODUCER);
			ConsumerWrapper consumer = (ConsumerWrapper) session.getAttribute(CONSUMER);

			if (producer != null) {
				producer.getProducer().close();

				type = PRODUCER;
				name = producer.getName();
			}

			if (consumer != null) {
				consumer.setShouldRun(false);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
				}
				consumer.getConsumer().close();

				type = CONSUMER;
				name = consumer.getName();
			}

			LOGGER.info("Deleting existing Kafka object... {}-{}", type, name);

			session.setAttribute(PRODUCER, null);
			session.setAttribute(CONSUMER, null);

			type = null;
			name = null;
		}

		session.setAttribute("type-client", type);
		resp.sendRedirect("/index.jsp");
	}
}
