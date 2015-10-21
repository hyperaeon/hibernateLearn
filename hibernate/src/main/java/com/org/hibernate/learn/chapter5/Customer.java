package com.org.hibernate.learn.chapter5;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;


public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String firstName;

	private String lastName;

	private char sex;

	private Set<Order> orders = new HashSet<>();

	private Double avgPrice;

	private Double totalPrice;

	private String description;

	public Customer(String firstName, String lastName, char sex,
			Set<Order> orders, String description) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.sex = sex;
		this.orders = orders;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public char getSex() {
		return sex;
	}

	public void setSex(char sex) {
		this.sex = sex;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private void setAvgPrice(Double avgPrice) {
		this.avgPrice = avgPrice;
	}

	public Double getAvgPrice() {
		return this.avgPrice;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
		calculatePrice();
	}

	public Set<Order> getOrders() {
		return this.orders;
	}

	private void calculatePrice() {
		double avgPrice = 0.0;
		double totalPrice = 0.0;
		int count = 0;

		if (getOrders() != null) {
			Iterator<Order> it = getOrders().iterator();
			while (it.hasNext()) {
				double orderPrice = it.next().getPrice();
				totalPrice += orderPrice;
				count++;
			}
			avgPrice = totalPrice / count;
			setAvgPrice(avgPrice);
		}
	}

	public String getName() {
		return firstName + lastName;
	}

	public void setName(String name) {
		StringTokenizer t = new StringTokenizer(name);
		firstName = t.nextToken();
		lastName = t.nextToken();
	}
}
