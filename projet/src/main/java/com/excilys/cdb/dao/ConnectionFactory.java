package main.java.com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory { //c'est en fait un singleton car une seule connexion à la BDD doit être active
	String driverClassName = "com.mysql.jdbc.Driver";
	String connectionUrl = "jdbc:mysql://localhost/computer-database-db?zeroDateTimeBehavior=convertToNull";
	String dbUser = "admincdb";
	String dbPwd = "qwerty1234";

	private static ConnectionFactory connectionFactory = null;

	private ConnectionFactory() {
		try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() throws SQLException {
		Connection conn = null;
		conn = DriverManager.getConnection(connectionUrl, dbUser, dbPwd);
		return conn;
	}

	public static ConnectionFactory getInstance() {
		if (connectionFactory == null) {
			connectionFactory = new ConnectionFactory();
		}
		return connectionFactory;
	}
	

}
