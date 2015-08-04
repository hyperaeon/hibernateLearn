package com.org.hibernate.learn.model;

import java.util.HashSet;
import java.util.Set;

public class Customer {
	private Long id;

	private String name;

	private int age;

	private Set<Order> orders = new HashSet<Order>();

	public Customer(String name, int age, Set<Order> orders) {
		this.name = name;
		this.age = age;
		this.orders = orders;
	}

	public Customer() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
}
