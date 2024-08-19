package com.sliceshop;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SliceShopServlet extends HttpServlet
{
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
		System.out.println("testing ..");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		
		
		String method = "";
		String response = null;
		int responseCode = HttpServletResponse.SC_OK;
		
		try {
			method = getMethod(req.getRequestURI());
			
			switch(method) {
				case "products":
				{
					res.setContentType("text/html");
					res.setBufferSize(8192);
					res.getWriter().println("<html><body><h1>products!</h1></body></html>");
					System.out.println("hello world");
				}
				case "users":
				{
					res.setContentType("text/html");
					res.setBufferSize(8192);
					res.getWriter().println("<html><body><h1>users!</h1></body></html>");
					System.out.println("hello world");
				}
			}
		}
		 catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private String getMethod(String requestURI)
	{
		
		String[] parts = requestURI.split("/");
		if (parts.length > 2)
		{
			return parts[2];
		}
		return "";
	}
}
