package com.org.hibernate.learn.chapter3.sevlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.org.hibernate.learn.chapter3.service.BusinessService;

public class DBServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			response.setContentType("text/html;charset=GBK");
			new BusinessService().test(this.getServletContext(),
					response.getWriter());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
