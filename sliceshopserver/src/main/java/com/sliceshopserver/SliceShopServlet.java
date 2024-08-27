package com.sliceshopserver;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sliceshopserver.persistence.DbTransactionManager;
import com.sliceshopserver.persistence.PizzaDao;

@WebServlet(name = "SliceShop", urlPatterns = "/*", loadOnStartup = 1)
public class SliceShopServlet extends HttpServlet
{
	
	private static final long serialVersionUID = 1L;
	private PizzaDao pizzaDao;
	private DbTransactionManager transactionManager;
	
	@Override
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
		
		// Initialize SessionFactory and TransactionManager here
		try
		{
			transactionManager = new DbTransactionManager(PizzaDao.buildSessionFactory());
			pizzaDao = new PizzaDao(transactionManager);
			System.out.println("Database connection initialized successfully.");
			
			// Get base URL and print to console
			ServletContext context = config.getServletContext();
			
			String contextPath = context.getContextPath(); // e.g., /sliceshop
			
			System.out.println("Application Base URL: " + contextPath);
		}
		catch (Exception e)
		{
			throw new ServletException("Failed to initialize database connection.", e);
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		String method = getMethod(req.getRequestURI());
		
		try
		{
			transactionManager.beginTransaction();
			
			switch (method)
			{
				case "products":
					res.setContentType("text/html");
					res.getWriter().println("<html><body><h1>Products!</h1></body></html>");
					pizzaDao.getAllPizzas().forEach(pizza -> System.out.println(pizza.getName()));
					break;
				case "users":
					res.setContentType("text/html");
					res.getWriter().println("<html><body><h1>Users!</h1></body></html>");
					break;
				default:
					res.setStatus(HttpServletResponse.SC_NOT_FOUND);
					res.getWriter().println("<html><body><h1>404 Not Found</h1></body></html>");
					break;
			}
			
			transactionManager.commitTransaction();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			res.getWriter().println("<html><body><h1>Internal Server Error</h1></body></html>");
		}
		finally
		{
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
