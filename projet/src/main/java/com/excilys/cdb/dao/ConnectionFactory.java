package main.java.com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton créant la connexion à la BDD
 * Une seule instance de connexion disponible
 * 
 * @author bertrand
 * 
 */

public class ConnectionFactory { 
	/**
     * Le driver à utiliser pour la connection JDBC
     * @see ConnectionFactory#connectionFactory
     */
	String driverClassName = "com.mysql.jdbc.Driver";
	/**
     * L'URL de notre base de données
     * le ?zeroDateTimeBehavior=convertToNull permet de gérer les dates à 0 dans 
     * la BDD
     * @see ConnectionFactory#getConnection()
     */
	String connectionUrl = "jdbc:mysql://localhost/computer-database-db?zeroDateTimeBehavior=convertToNull";
	/**
     * L'utilisateur de la BDD
     * @see ConnectionFactory#getConnection()
     */
	String dbUser = "admincdb";
	/**
     * Le mot de passe de notre utilisateur pour la BDD
     * @see ConnectionFactory#getConnection()
     */
	String dbPwd = "qwerty1234";

	/**
     * Notre unique instance de ConnectionFactory
     * @see ConnectionFactory#getInstance()
     */
	private static ConnectionFactory connectionFactory = null;

	/**
     * Constructeur ConnectionFactory
     * privé car il ne doit y avoir qu'une seule instance
     * 
     * @see ConnectionFactory#getInstance()
     */
	private ConnectionFactory() {
		try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
     * Connexion à la base de données
     * 
     * @return connection à la BDD
     * @see ConnectionFactory#getInstance()
     */
	public Connection getConnection() throws SQLException {
		Connection conn = null;
		conn = DriverManager.getConnection(connectionUrl, dbUser, dbPwd);
		return conn;
	}

	/**
     * Renvoie la seule instance existante de notre connexion
     * 
     * @return instance de ConnectionFactory
     * @see ConnectionFactory#getConnection()
     */
	public static ConnectionFactory getInstance() {
		if (connectionFactory == null) {
			connectionFactory = new ConnectionFactory();
		}
		return connectionFactory;
	}
	

}
