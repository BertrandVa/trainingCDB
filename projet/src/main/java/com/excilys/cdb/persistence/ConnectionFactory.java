package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

/**
 * Singleton créant la connexion à la BDD Une seule instance de connexion.
 * disponible.
 * @author bertrand
 */

public enum ConnectionFactory {
    INSTANCE;

    /**
     * Connexion à la base de données.
     * @return connection à la BDD
     * @see ConnectionFactory#getInstance()
     */
    private Connection createConnection() {
        Connection conn = null;
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
                PropertiesConfiguration.class).configure(params.properties()
                .setFileName("database.properties"));
        try {
            Configuration config = builder.getConfiguration();
            String driver = config.getString("jdbc.driverClassName");
            String url = config.getString("jdbc.url");
            String username = config.getString("jdbc.username");
            String password = config.getString("jdbc.password");
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (ConfigurationException cex) {
            // loading of the configuration file failed
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return conn;
    }

    /**
     * Renvoie la seule instance existante de notre connexion.
     * @return instance de ConnectionFactory
     * @see ConnectionFactory#getConnection()
     */
    public static Connection getConnection() {
        return INSTANCE.createConnection();
    }

}
