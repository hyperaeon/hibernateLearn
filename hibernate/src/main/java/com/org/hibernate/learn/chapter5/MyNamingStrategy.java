package com.org.hibernate.learn.chapter5;

import org.hibernate.SessionFactory;
import org.hibernate.annotations.common.util.StringHelper;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.ImprovedNamingStrategy;

public class MyNamingStrategy extends ImprovedNamingStrategy {

	public String classToTableName(String className) {
		return StringHelper.unqualify(className).toLowerCase() + "S";
	}

	public String propertyToColumnName(String propertyName) {
		return propertyName.toLowerCase();
	}

	public String tableName(String tableName) {
		return tableName;
	}

	public String columnName(String columnName) {
		return columnName;
	}

	public String propertyToTableName(String className, String tableName) {
		return classToTableName(className) + "_"
				+ propertyToColumnName(tableName);
	}

	public static void main(String[] args) {
		Configuration config = new Configuration().setNamingStrategy(
				new MyNamingStrategy()).configure();
		SessionFactory factory = config.buildSessionFactory();
	}
}
