package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.zaxxer.hikari.HikariDataSource;

public class HikariConnectionFactory {

    /**
     * logger.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(HikariConnectionFactory.class);
    private static ApplicationContext context =
            new ClassPathXmlApplicationContext("/Spring-Modules.xml");
    private static HikariDataSource ds;

    private static final ThreadLocal<Connection> THREAD_LOCAL = new ThreadLocal<Connection>();

    /**
     * Connexion à la base de données.
     * @return connection à la BDD
     * @see ConnectionFactory#getInstance()
     */
    private static Connection createConnection() {
        Connection conn = null;
        try {
            setDataSource();
            conn = ds.getConnection();
        } catch (SQLException | NullPointerException e) {
            LOGGER.debug(e.getMessage());
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
    public HikariConnectionFactory() {
    }

    /**
     * Setter de notre datasource.
     */
    private static void setDataSource() {
       HikariConnectionFactory.ds = (HikariDataSource) context.getBean("dataSource");
    }
}
