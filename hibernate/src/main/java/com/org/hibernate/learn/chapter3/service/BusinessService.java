package com.org.hibernate.learn.chapter3.service;

import java.io.PrintWriter;

import javax.servlet.ServletContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.org.hibernate.learn.chapter3.model.Customer;

public class BusinessService {

	public static SessionFactory sessionFactory;

	static {
		try {
			Configuration config = new Configuration();
			config.addClass(Customer.class);
			sessionFactory = config.buildSessionFactory();
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void finallAllCUstomers(ServletContext context, PrintWriter out)
			throws Exception {

	}

	public void saveCustomer(Customer customer) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(customer);
			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw e;
		} finally {
			session.close();
		}
	}
	
	public void loadAndUpdateCustomer(Long customer_id, String address) {
		
	}
	
}
