package com.org.hibernate.learn.chapter5;

public class Order {

	private Long id;

	private String orderNumber;

	private double price;

	private Customer customer;

	public Order(String orderNumber, double price, Customer customer) {
		this.orderNumber = orderNumber;
		this.price = price;
		this.customer = customer;
	}

	public Order() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}
