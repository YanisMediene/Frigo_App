package com.tse.app.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDBTest {

	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/spoonacular_test";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "testeur123";
	private static Connection connection;

	public static Connection getConnection() throws SQLException {
		if (connection == null || connection.isClosed())
			connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
		return connection;
	}

	public static void closeConnection() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
