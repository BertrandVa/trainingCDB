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
    static final Logger LOGGER = LoggerFactory
            .getLogger(HikariConnectionFactory.class);

    private static final ThreadLocal<Connection> THREAD_LOCAL = new ThreadLocal<Connection>();
    HikariDataSource ds;

    /**
     * Connexion à la base de données.
     * @return connection à la BDD
     * @see ConnectionFactory#getInstance()
     */
    private Connection createConnection() {
        Connection conn = null;
        try {
            conn = ds.getConnection();
        } catch (SQLException e) {
            LOGGER.debug(e.getMessage());
        }
        return conn;
    }

    /**
     * Renvoie la seule instance existante de notre connexion.
     * @return instance de ConnectionFactory
     * @see ConnectionFactory#getConnection()
     */
    public Connection getConnection() {
        try {
            if (THREAD_LOCAL.get() == null
                    || THREAD_LOCAL.get().isClosed()) {
                Connection connection = createConnection();
                THREAD_LOCAL.set(connection);
            }
        } catch (SQLException | NoClassDefFoundError e) {
            LOGGER.error(e.getMessage());
        }
        return THREAD_LOCAL.get();
    }

    /**
     * Constructeur de notre connexion Hikari.
     */
    HikariConnectionFactory() {
      //  final Logger lOGGER1 = LoggerFactory
       //         .getLogger(HikariConnectionFactory.class);
       // Parameters params = new Parameters();
       // FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
        //        PropertiesConfiguration.class).configure(
         //               params.properties().setFileName("hikari.properties"));
       // try {
         //   Configuration config = builder.getConfiguration();
          //  config = builder.getConfiguration();
          //  String driver = config.getString("dataSource.driverClass");
          //  Class.forName(driver);
           // String url = config.getString("jdbcUrl");
           // String username = config.getString("dataSource.user");
           // String password = config.getString("dataSource.password");
            HikariConfig cfg = new HikariConfig();
          //  cfg.setJdbcUrl(url);
           // cfg.setUsername(username);
           // cfg.setPassword(password);
            cfg.addDataSourceProperty("cachePrepStmts", "true");
            //cfg.addDataSourceProperty("prepStmtCacheSize", "500");
            //cfg.addDataSourceProperty("prepStmtCacheSqlLimit", "4096");
            cfg.setConnectionTestQuery("show tables");
            cfg.setMaximumPoolSize(80);
            setDataSource(new HikariDataSource(cfg));
       // } catch (ConfigurationException | ClassNotFoundException e) {
        //    lOGGER1.error(e.getMessage());
      //  }
    }
    
    /**
     * Setter de notre datasource.
     */
    private void setDataSource(HikariDataSource dataSource) {
        this.ds = dataSource;
    }
}
