package com.org.hibernate.learn.chapter5;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import com.org.hibernate.learn.model.Order;

public class Customer {

	private String firstName;

	private String lastName;

	private Set<Order> orders = new HashSet<>();

	private Double avgPrice;

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
