package com.sliceshopserver.persistence;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

public class PizzaDao
{
	
	//public static final String CONFIG_PATH = "com/sliceshop/persistence/hibernate.properties";
	
	private DbTransactionManager transactionManager;
	
	public PizzaDao(DbTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
	}
	
	public static SessionFactory buildSessionFactory()
	{
		SessionFactory sessionFactory = null;
		try
		{
			
			Properties dbprops = new Properties();
			// Load properties file from the classpath
			try (InputStream inputStream = PizzaDao.class.getClassLoader().getResourceAsStream("hibernate.properties"))
			{
				if (inputStream == null)
				{
					throw new RuntimeException("Unable to find hibernate.properties");
				}
				dbprops.load(inputStream);
			}
			
			//dbprops.load(new FileInputStream(CONFIG_PATH));
			
			StandardServiceRegistry serviceRegistry =
					new StandardServiceRegistryBuilder().applySettings(dbprops).build();
			
			MetadataSources sources = new MetadataSources(serviceRegistry);
			sources.addAnnotatedClass(Pizza.class);
			
			sessionFactory = sources.buildMetadata().buildSessionFactory();
			
			System.out.println("Successfully connected to the database!");
		}
		catch (Throwable ex)
		{
			System.err.println("Initial SessionFactory creation failed. " + ex);
			throw new ExceptionInInitializerError(ex);
		}
		return sessionFactory;
		
	}
	
	public List<Pizza> getAllPizzas()
	{
		String hql = "FROM Pizza"; // Corrected HQL query
		Query<Pizza> query = transactionManager.getCurrentSession().createQuery(hql, Pizza.class);
		return query.list();
	}
}
