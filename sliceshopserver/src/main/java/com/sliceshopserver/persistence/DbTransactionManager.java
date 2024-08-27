package com.sliceshopserver.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class DbTransactionManager
{
	
	private SessionFactory sessionFactory;
	
	public DbTransactionManager(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}
	
	public Session getCurrentSession()
	{
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * Starts a new hibernate transaction
	 */
	public void beginTransaction()
	{
		sessionFactory.getCurrentSession().beginTransaction();
	}
	
	/**
	 * commits changes
	 */
	public void commitTransaction()
	{
		sessionFactory.getCurrentSession().getTransaction().commit();
	}
	
	public void shutdown() {
		sessionFactory.close();
	}

}
