package com.sliceshopserver.persistence;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "pizza")
public class Pizza
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "price", nullable = false)
	private Double price;
	
	@ElementCollection
	@CollectionTable(name = "ingredients", joinColumns = @JoinColumn(name = "pizza_id"))
	@Column(name = "name", nullable = false, updatable = false)
	private List<String> ingredients;
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public Double getPrice()
	{
		return price;
	}
	
	public void setPrice(Double price)
	{
		this.price = price;
	}
	
	public List<String> getIngredients()
	{
		return ingredients;
	}
	
	public void setIngredients(List<String> ingredients)
	{
		this.ingredients = ingredients;
	}
	
	// Constructors
	public Pizza()
	{
	}
	
	public Pizza(String name, Double price, List<String> ingredients)
	{
		this.name = name;
		this.price = price;
		this.ingredients = ingredients;
	}
	
}
