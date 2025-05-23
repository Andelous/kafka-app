package com.angeld.kafkaapp.servlet;

import java.io.IOException;

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

		System.out.println("MEOWW");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		
		session.setAttribute("tipo-cliente", req.getParameter("tipo"));
		
		resp.sendRedirect("/index.jsp");
	}
}
