package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.excilys.cdb.model.Company;

/**
 * Cette classe de DAO implémente les méthodes nécessaires à l'accès aux données
 * de la table company. Le client ne demandant qu'un accès aux données, il n'y a
 * pas de suppression, de création ou d'update.
 * @author bertrand
 */

public class CompanyDAO {

    /**
     * logger.
     */
    private final Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

    /**
     * Méthode d'affichage de tous les fabriquants.
     * @return List une arraylist contenant l'ensemble de nos fabriquants
     * @param debut
     *            le premier id à afficher
     * @param nbItems
     *            le nombre d'items à afficher
     */
    public List<Company> readAll(int debut, int nbItems) {
        CriteriaBuilder cb = HibernateSessionFactory.getSessionFactory().createEntityManager().getCriteriaBuilder();
        CriteriaQuery<Company> cq = cb.createQuery(Company.class);
        Root<Company> rootEntry = cq.from(Company.class);
        CriteriaQuery<Company> all = cq.select(rootEntry);
        TypedQuery<Company> allQuery = HibernateSessionFactory.getSessionFactory().createEntityManager().createQuery(all).setFirstResult(debut);
        return allQuery.getResultList();
    }

    /**
     * Méthode count pour les compagnies.
     * @return nbEntrees le nombre d'entrées dans la BDD.
     */
    public int countCompanies() {
        CriteriaBuilder critBuilder = HibernateSessionFactory.getSessionFactory().createEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> critQuery = critBuilder.createQuery(Long.class);
        Root<Company> root = critQuery.from(Company.class);
        critQuery.select(critBuilder.countDistinct(root));
        int count = HibernateSessionFactory.getSessionFactory().createEntityManager().createQuery(critQuery).getSingleResult().intValue();
        return count;
    }

    /**
     * Méthode delete pour les compagnies, supprime les ordinateurs associés.
     * @param id
     *            l'id de la compagnie à supprimer
     * @return fait un boolean setté à true si l'action a été effectuée
     */
    public boolean deleteCompanyAndRelatedComputers(long id) {
        boolean fait = false;
        try (Connection connection = HikariConnectionFactory.getConnection();) {
            connection.setReadOnly(false);
            connection.setAutoCommit(false);
            String sql = "DELETE FROM computer WHERE company_id = ?";
            try (PreparedStatement statement = connection
                    .prepareStatement(sql)) {
                statement.setLong(1, id);
                statement.executeUpdate();
                String sql2 = "DELETE FROM company WHERE id = ?";
                try (PreparedStatement statement2 = connection
                        .prepareStatement(sql2)) {
                    statement2.setLong(1, id);
                    statement2.executeUpdate();
                }
                connection.commit();
                fait = true;
            } catch (SQLException e) {
                logger.error(e.getMessage());
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return fait;
    }
}
