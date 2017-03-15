package main.java.com.excilys.cdb.persistence;

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

public enum ConnectionFactory { INSTANCE; 
	/**
     * Le driver à utiliser pour la connection JDBC
     * @see ConnectionFactory#connectionFactory
     */
	private static final String driverClassName = "com.mysql.jdbc.Driver";
	/**
     * L'URL de notre base de données
     * le ?zeroDateTimeBehavior=convertToNull permet de gérer les dates à 0 dans 
     * la BDD
     * @see ConnectionFactory#getConnection()
     */
	private static final String connectionUrl = "jdbc:mysql://localhost/computer-database-db-test?zeroDateTimeBehavior=convertToNull";
	/**
     * L'utilisateur de la BDD
     * @see ConnectionFactory#getConnection()
     */
	private static final String dbUser = "admincdb";
	/**
     * Le mot de passe de notre utilisateur pour la BDD
     * @see ConnectionFactory#getConnection()
     */
	private static final String dbPwd = "qwerty1234";

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
	private Connection createConnection(){
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(connectionUrl, dbUser, dbPwd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	/**
     * Renvoie la seule instance existante de notre connexion
     * 
     * @return instance de ConnectionFactory
     * @see ConnectionFactory#getConnection()
     */
	public static Connection getConnection() {
		return INSTANCE.createConnection();
	}
	

}
