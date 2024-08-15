package com.ecommerce;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EcommerceServlet extends HttpServlet
{
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		response.setBufferSize(8192);
		response.getWriter().println("<html><body><h1>Hello, World!</h1></body></html>");
		System.out.println("hello world");
	}
}
