package com.org.hibernate.learn.chapter3.service;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;

import org.hibernate.Query;
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

	public void findAllCustomers(ServletContext context, PrintWriter out)
			throws Exception {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session
					.createQuery("from Customer as c order by c.name asc");
			List<Customer> customers = query.list();
			for (Iterator<Customer> it = customers.iterator(); it.hasNext();) {
				printCustomer(context, out, it.next());
			}
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
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Customer c = (Customer) session.get(Customer.class, customer_id);
			c.setAddress(address);
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

	public void deleteCustomer(Customer customer) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(customer);
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

	/**
	 * print customer
	 * 
	 * @param context
	 * @param out
	 * @param customer
	 * @throws Exception
	 */
	private void printCustomer(ServletContext context, PrintWriter out,
			Customer customer) throws Exception {
		if (context != null) {
			printCustomerInWeb(context, out, customer);
		} else {
			printCustomer(out, customer);
		}

	}

	/**
	 * print customer in normal way.
	 * 
	 * @param out
	 * @param customer
	 */
	private void printCustomer(PrintWriter out, Customer customer)
			throws Exception {
		byte[] buffer = customer.getImage();
		FileOutputStream fout = new FileOutputStream("photo_copy.gif");
		fout.write(buffer);
		fout.close();
		out.println("------以下是" + customer.getName() + "的个人信息-----");
		out.println("ID:" + customer.getId());
		out.println("口令：" + customer.getPassword());
	}

	/**
	 * print customer in web
	 * 
	 * @param context
	 * @param out
	 * @param customer
	 */
	private void printCustomerInWeb(ServletContext context, PrintWriter out,
			Customer customer) throws Exception {
		byte[] buffer = customer.getImage();
		String path = context.getRealPath("/");
		FileOutputStream fout = new FileOutputStream(path + "photo_copy.gif");
		fout.write(buffer);
		fout.close();
		out.println("------以下是" + customer.getName() + "的个人信息-----");
		out.println("ID:" + customer.getId());
		out.println("口令：" + customer.getPassword());
	}

	public void test(ServletContext context, PrintWriter out) throws Exception {
		Customer customer = new Customer();
		customer.setName("Tom");
		customer.setEmail("tom@yahoo.com");
		customer.setPassword("1234");
		customer.setPhone(123123123);
		customer.setAddress("Shanghai");
		customer.setSex('M');
		customer.setDescription("I am very honest");

		InputStream io = this.getClass().getResourceAsStream("photo.gif");
		byte[] buffer = new byte[io.available()];
		io.read(buffer);
		customer.setImage(buffer);

		customer.setBirthday(Date.valueOf("1980-05-06"));
		saveCustomer(customer);
		loadAndUpdateCustomer(customer.getId(), "Beijing");
		findAllCustomers(context, out);
	}

	public static void main(String[] args) throws Exception {
		new BusinessService().test(null, new PrintWriter(System.out, true));
		sessionFactory.close();
	}
}
