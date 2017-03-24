package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public enum HikariConnectionFactory {
    INSTANCE;
    

    /**
     * logger.
     */
    static final Logger LOGGER= LoggerFactory.getLogger(HikariConnectionFactory.class);

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
                .setFileName("hikari.properties"));
        
        try {
            Configuration config = builder.getConfiguration();
            config = builder.getConfiguration();
            String url = config.getString("jdbcUrl");
            String username = config.getString("dataSource.user");
            String password = config.getString("dataSource.password");
            HikariConfig cfg = new HikariConfig();
            cfg.setJdbcUrl(url);
            cfg.setUsername(username);
            cfg.setPassword(password);
            cfg.addDataSourceProperty("cachePrepStmts", "true");
            cfg.addDataSourceProperty("prepStmtCacheSize", "250");
            cfg.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");            
            cfg.setConnectionTestQuery("show tables");
            HikariDataSource ds = new HikariDataSource(cfg);
            conn = ds.getConnection();
        } catch (ConfigurationException | SQLException e) {
             LOGGER.error(e.getMessage());
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
