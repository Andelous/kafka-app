package com.angeld.kafkaapp.servlet;

import java.io.IOException;

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

	private static final String PRODUCER = "PRODUCER";
	private static final String CONSUMER = "CONSUMER";

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		System.out.println("Initialized servlet.");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();

		String type = req.getParameter("type");
		String name = req.getParameter("name");

		if (type != null && !type.isBlank()) {
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

			if (producer != null)
				producer.getProducer().close();
			if (consumer != null)
				consumer.getConsumer().close();

			session.setAttribute(PRODUCER, null);
			session.setAttribute(CONSUMER, null);
		}

		session.setAttribute("type-client", type);
		resp.sendRedirect("/index.jsp");
	}
}
