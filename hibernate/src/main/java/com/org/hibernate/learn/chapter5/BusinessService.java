package com.org.hibernate.learn.chapter5;

import java.util.HashSet;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class BusinessService {

	public static SessionFactory sessionFactory;

	static {
		try {
			Configuration config = new Configuration();
			config.setNamingStrategy(new MyNamingStrategy()).configure();
			sessionFactory = config.buildSessionFactory();
		} catch (RuntimeException re) {
			re.printStackTrace();
			throw re;
		}
	}

	public Customer loadCustomer(long customer_id) {
		return null;
	}

	public void saveCustomer(Customer customer) {
	}

	public void loadAndUpdateCustomer(long customer_id) {

	}

	public void updateCustomer(Customer customer) {

	}

	public void saveDictionary(Dictionary dictionary) {

	}

	public void updateDictionary(Dictionary dictionary) {

	}

	public Dictionary loadDictionary(long dictionary_id) {
		return null;
	}

	public void printCustomer(Customer customer) {

	}

	public void printDictionary(Dictionary dictionary) {

	}

	public void test() {
		Customer customer = new Customer("San", "Zhang", 'M',
				new HashSet<Order>(), "A good citzen!");
		Order order1 = new Order("Order0001", new Double(100), customer);
		Order order2 = new Order("Order0002", new Double(200), customer);
		customer.getOrders().add(order1);
		customer.getOrders().add(order2);
		saveCustomer(customer);
	}

	public static void main(String[] args) {
		new BusinessService().test();
		sessionFactory.close();
	}
}
