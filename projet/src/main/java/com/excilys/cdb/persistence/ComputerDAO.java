package com.excilys.cdb.persistence;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Computer;
import com.zaxxer.hikari.HikariDataSource;
/**
 * Cette classe de DAO implémente les méthodes nécessaires à l'accès aux données
 * de la table computer. Le client demande ici un accès total, toutes les
 * méthodes du CRUD sont donc implémentées
 * @author bertrand
 */

public class ComputerDAO {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);
    
    private JdbcTemplate jdbcTemplateObject;

    public void setDataSource(HikariDataSource dataSource) {
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    /**
     * Méthode create d'un ordinateur.
     * @param computer
     *            l'ordinateur à créer
     * @return boolean create true si tout s'est bien passé, false autrement
     */
    public long create(Computer computer) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        session.beginTransaction();
        if(computer.getName() != null){
            session.save(computer);
        }
        long id = computer.getId();
        session.getTransaction().commit();
        LOGGER.debug("" + id);
        return(computer.getId());
    }

    /**
     * Méthode d'affichage d'un ordinateur.
     * @param id
     *            l'ordinateur à afficher
     * @return computer l'ordinateur sélectionné
     */
    public Computer read(long id) {
        String sql = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id  WHERE computer.id = ?";
        Computer computer = null;
                try {
                    computer = jdbcTemplateObject.queryForObject(sql, new Object[] {id}, new ComputerMapper());
                } catch (EmptyResultDataAccessException e) {
                    computer = new Computer.ComputerBuilder(null).build();
                }
        return computer;
    }

    /**
     * Méthode d'affichage de tous les ordinateurs.
     * @return List une arraylist contenant l'ensemble de nos ordinateurs
     * @param debut
     *            le premier id à afficher
     * @param nbItems
     *            le nombre d'items à afficher
     * @param order
     *            le champ pour trier les ordinateurs
     * @param match
     *            les caractères recherchés.
     */
    public List<Computer> readAll(long debut, int nbItems, String match,
            String order) {        
        List<Computer> list = new ArrayList<Computer>();
        String sql = "SELECT computer.name, computer.id, REPLACE(computer.introduced, '0000-00-00 00:00:00', null) AS introduced, REPLACE(computer.discontinued, '0000-00-00 00:00:00', null) AS discontinued, company.id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name like %s OR company.name like %s ORDER BY %s LIMIT "
                + nbItems + " OFFSET " + debut;
        sql = String.format(sql, match, match, order);
        list = jdbcTemplateObject.query(sql, new ComputerMapper());
        return list;
    }

    /**
     * Méthode de mise à jour d'un ordinateur.
     * @param computer
     *            l'ordinateur à mettre à jour
     */
    public void update(Computer computer) {
        String sql = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ?  WHERE id = ?";
        long id = 1;
        Date introduce = null;
        Date discontinued = null;
        if (computer.getManufacturer() != null) {
            id = computer.getManufacturer().getId();
        }
        if (computer.getIntroduceDate() != null) {
           introduce = java.sql.Date.valueOf(computer.getIntroduceDate());
        }
        if (computer.getDiscontinuedDate() != null) {
           discontinued = java.sql.Date.valueOf(computer.getDiscontinuedDate());
        }
        jdbcTemplateObject.update(sql, computer.getName(),
                introduce, discontinued,
                id, computer.getId());
    }

    /**
     * Méthode delete d'un ordinateur.
     * @param id
     *            l'id de l'ordinateur à supprimer
     */
    public void delete(long id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx = null;
        try{
           tx = session.beginTransaction();
           Query q = session.createQuery("delete Computer where id = " + id);
           q.executeUpdate();
           tx.commit();
        }catch (HibernateException e) {
           if (tx!=null) tx.rollback();
           e.printStackTrace(); 
        }finally {
           session.close(); 
        }
    }

    /**
     * Méthode count pour les ordinateurs.
     * @return nbEntrees le nombre d'entrées dans la BDD.
     * @param match
     *            la chaine de caractères à matcher
     */
    public int countComputer(String match) {
        String sql = "SELECT COUNT(*) FROM computer";
        int nbComputers = jdbcTemplateObject.queryForObject(sql, Integer.class);
        return nbComputers;
    }

    /**
     * Méthode count pour les pages selon le nombre d'affichages.
     * @return nbPages le nombre de pages dans la BDD.
     * @param nbId
     *            le nombre d'ids affichés par pages
     * @param match
     *            la chaine de caractères à matcher
     */
    public int countPages(int nbId, String match) {
        String sql = "SELECT COUNT(*) AS count FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name like "
                + match + " OR company.name like " + match;
        int maxId = jdbcTemplateObject.queryForObject(sql, Integer.class);
        int nbPages = 0;
        if (nbId != 0) {
            if (maxId % nbId == 0) {
                nbPages = maxId / nbId;
            } else {
                nbPages = maxId / nbId + 1;
            }
        }
        return nbPages;
    }
}