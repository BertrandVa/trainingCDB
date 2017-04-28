package com.excilys.cdb.persistence;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {

    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();

    /**
     * @return
     *          La session Factory.
     */
    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * @return
     *          La session Factory.
     */
    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    /**
     * Void.
     */
    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }
}
