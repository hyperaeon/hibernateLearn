package com.org.hibernate.learn.service;

import com.org.hibernate.learn.model.Customer;
import com.org.hibernate.learn.model.Order;

import junit.framework.TestCase;


public class BusinessTest extends TestCase {

	private static BusinessService service;
	
	static {
		try {
			service = new BusinessService();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void testSaveCustomer() throws Exception {
		Customer customer = new Customer();
		customer.setName("liyong");
		customer.setAge(29);
		
		Order order = new Order();
		order.setCustomer(customer);
		order.setOrderNumber("123456");
		order.setPrice(200);
		
		customer.getOrders().add(order);
		service.saveCustomer(customer);
	}
}
