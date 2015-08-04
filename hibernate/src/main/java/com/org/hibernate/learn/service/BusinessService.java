package com.org.hibernate.learn.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.org.hibernate.learn.exception.BusinessException;
import com.org.hibernate.learn.model.Customer;
import com.org.hibernate.learn.model.Order;

public class BusinessService {

	private String dbUrl = "jdbc:mysql://localhost:3306/SAMPLEDB";
	private String dbUser = "root";
	private String dbPwd = "root";

	public BusinessService() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
	}

	public Connection getConnection() throws Exception {
		return DriverManager.getConnection(dbUrl, dbUser, dbPwd);
	}

	public void saveCustomer(Customer customer) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			if (customer.getName() == null) {
				throw new BusinessException("客户姓名不允许为空");
			}
			long customerId = getNextId(conn, "CUSTOMERS");
			stmt = conn
					.prepareStatement("insert into CUSTOMERS(ID,NAME,AGE) values (?,?,?)");
			stmt.setLong(1, customerId);
			stmt.setString(2, customer.getName());
			stmt.setInt(3, customer.getAge());
			stmt.execute();

			Iterator<Order> iterator = customer.getOrders().iterator();
			while (iterator.hasNext()) {
				Order order = iterator.next();
				if (order.getOrderNumber() == null) {
					throw new BusinessException("订单编号不允许为空");
				}
				long orderId = getNextId(conn, "ORDERS");
				stmt = conn
						.prepareStatement("insert into ORDERS(ID,ORDER_NUMBER,PRICE,CUSTOMER_ID) values(?,?,?,?)");
				stmt.setLong(1, orderId);
				stmt.setString(2, order.getOrderNumber());
				stmt.setDouble(3, order.getPrice());
				stmt.setLong(4, customerId);
				stmt.execute();
			}
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException ex) {
				ex.printStackTrace(System.out);
			}
			throw e;
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void updateCustomer(Customer customer) throws Exception {

	}

	public void deleteCustomer(Customer customer) throws Exception {

	}

	public Customer loadCustomer(long customerId) throws Exception {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			stmt = con
					.prepareStatement("select ID,NAME,AGE from CUSTOMERS where ID=?");
			stmt.setLong(1, customerId);
			rs = stmt.executeQuery();
			if (rs.next()) {
				Customer customer = new Customer();
				customer.setId(Long.valueOf(rs.getLong(1)));
				customer.setName(rs.getString(2));
				customer.setAge(rs.getInt(3));
				return customer;
			} else {
				throw new BusinessException("OID为" + customerId
						+ "的Customer对象不存在");
			}
		} finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public List<Customer> findCustomerByName(String name) throws Exception {
		HashMap<Long, Customer> map = new HashMap<Long, Customer>();
		List<Customer> result = new ArrayList<Customer>();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = getConnection();
			String sqlString = "select c.ID CUSTOMER_ID, c.NAME,c,AGE,o.ID ORDER_ID,o.ORDER_NUMBER,o.PRICE "
					+ " from CUSTOMERS c left join ORDERS o "
					+ " on c.ID = o.Customer_ID where c.NAME=?";
			stmt = con.prepareStatement(sqlString);
			stmt.setString(1, name);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Long customerId = Long.valueOf(rs.getLong(1));
				String cutomerName = rs.getString(2);
				int customerAge = rs.getInt(3);
				Long orderId = Long.valueOf(rs.getLong(4));
				String orderNumber = rs.getString(5);
				double price = rs.getDouble(6);
				Customer customer = null;
				if (map.containsKey(customerId)) {
					customer = map.get(customerId);
				} else {
					customer = new Customer();
					customer.setId(customerId);
					customer.setName(cutomerName);
					customer.setAge(customerAge);
					map.put(customerId, customer);
				}
				Order order = new Order();
				order.setId(orderId);
				order.setOrderNumber(orderNumber);
				order.setPrice(price);

				customer.getOrders().add(order);
				order.setCustomer(customer);
			}
			Iterator<Customer> it = map.values().iterator();
			while (it.hasNext()) {
				result.add(it.next());
			}
			return result;
		} finally {
			try {
				rs.close();
				stmt.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * get next id of the given table.
	 * 
	 * @param conn
	 * @param string
	 * @return
	 */
	private long getNextId(Connection conn, String tableName) throws Exception {
		long nextId = 0;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.prepareStatement("select max(ID) from " + tableName);
			rs = stmt.executeQuery();
			if (rs.next()) {
				nextId = rs.getLong(1) + 1;
				if (rs.wasNull()) {
					nextId = 1;
				}
			} else {
				nextId = 1;
			}
			return nextId;
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
