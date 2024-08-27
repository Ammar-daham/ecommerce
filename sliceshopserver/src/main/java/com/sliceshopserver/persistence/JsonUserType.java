package com.sliceshopserver.persistence;


import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.DynamicParameterizedType;
import org.hibernate.usertype.UserType;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A generic user type that maps a Java object as a JSON column. The object type
 * is set via the {@link org.hibernate.annotations.Parameter} annotation.
 * 
 * NOTE: the object type must properly implement equals & hashCode.
 * 
 * Utilizes Jackson for de/serialization.
 * 
 */
public class JsonUserType implements UserType, DynamicParameterizedType
{
	private static final ObjectMapper mapper;
	
	static
	{
		mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
	}
	
	private Class<?> type;
	
	@Override
	public int[] sqlTypes()
	{
		return new int[] { Types.JAVA_OBJECT };
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Class returnedClass()
	{
		return type;
	}
	
	@Override
	public boolean equals(Object x, Object y) throws HibernateException
	{
		return Objects.equals(x, y);
	}
	
	@Override
	public int hashCode(Object x) throws HibernateException
	{
		return Objects.hashCode(x);
	}
	
	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
			throws HibernateException, SQLException
	{
		final String cellContent = rs.getString(names[0]);
		if (cellContent == null) return null;
		try
		{
			return mapper.readValue(cellContent, type);
		}
		catch (IOException e)
		{
			throw new HibernateException(e);
		}
	}
	
	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
			throws HibernateException, SQLException
	{
		if (value == null)
		{
			st.setNull(index, Types.OTHER);
			return;
		}
		try
		{
			st.setObject(index, mapper.writeValueAsString(value), Types.OTHER);
		}
		catch (IOException e)
		{
			throw new HibernateException(e);
		}
	}
	
	@Override
	public Object deepCopy(Object value) throws HibernateException
	{
		try
		{
			return mapper.readValue(mapper.writeValueAsString(value), type);
		}
		catch (IOException e)
		{
			throw new HibernateException(e);
		}
	}
	
	@Override
	public boolean isMutable()
	{
		return true;
	}
	
	@Override
	public Serializable disassemble(Object value) throws HibernateException
	{
		return (Serializable) deepCopy(value);
	}
	
	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException
	{
		return deepCopy(cached);
	}
	
	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException
	{
		return deepCopy(original);
	}
	
	@Override
	public void setParameterValues(Properties parameters)
	{
		try
		{
			type = Class.forName(parameters.getProperty("type"));
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}
	}
}
