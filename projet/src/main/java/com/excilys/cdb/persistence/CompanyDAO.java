package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Company;

/**
 * Cette classe de DAO implémente les méthodes nécessaires à l'accès aux données
 * de la table company. Le client ne demandant qu'un accès aux données, il n'y a
 * pas de suppression, de création ou d'update.
 * @author bertrand
 */

public enum CompanyDAO {
    INSTANCE;

    /**
     * logger.
     */
    final Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

    /**
     * Méthode d'affichage de tous les fabriquants.
     * @return List une arraylist contenant l'ensemble de nos fabriquants
     * @param debut
     *            le premier id à afficher
     * @param nbItems
     *            le nombre d'items à afficher
     */
    public List<Company> readAll(long debut, int nbItems) {
        List<Company> list = new ArrayList<Company>();
        try (Connection connection = HikariConnectionFactory.INSTANCE.getConnection();) {
            ResultSet result = connection.createStatement().executeQuery(
                    "SELECT * FROM company  LIMIT " + nbItems + " OFFSET " + debut);
                        while (result.next()) {
                        Company company = new Company.CompanyBuilder(
                                result.getString("company.name"))
                                        .id(result.getLong("company.id"))
                                        .build();
                        list.add(company);
                    }
                    result.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
           logger.debug("liste de fabriquants terminée");
        return list;
    }

    /**
     * Méthode count pour les compagnies.
     * @return nbEntrees le nombre d'entrées dans la BDD.
     */
    public int countCompanies() {
        int maxId = 0;
        try (Connection connection = HikariConnectionFactory.INSTANCE.getConnection();) {
            connection.setReadOnly(true);
            try (ResultSet result = connection.createStatement()
                    .executeQuery("SELECT COUNT(*) AS count FROM company");) {
                result.next();
                maxId = result.getInt("count");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return maxId;
    }

    /**
     * Méthode delete pour les compagnies, supprime les ordinateurs associés.
     * @param id
     *          l'id de la compagnie à supprimer
     * @return fait
     *          un boolean setté à true si l'action a été effectuée
     */
    public boolean deleteCompanyAndRelatedComputers(long id) {
        boolean fait = false;
        try (Connection connection = HikariConnectionFactory.INSTANCE.getConnection();) {
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
