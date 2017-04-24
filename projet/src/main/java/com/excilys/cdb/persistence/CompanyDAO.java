package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.model.Company;
import com.zaxxer.hikari.HikariDataSource;

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
    private JdbcTemplate jdbcTemplateObject;

    public void setDataSource(HikariDataSource dataSource) {
        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
     }

    /**
     * Méthode d'affichage de tous les fabriquants.
     * @return List une arraylist contenant l'ensemble de nos fabriquants
     * @param debut
     *            le premier id à afficher
     * @param nbItems
     *            le nombre d'items à afficher
     */
    public List<Company> readAll(long debut, int nbItems) {
        String sql = "SELECT * FROM company  LIMIT " + nbItems + " OFFSET " + debut;
        List<Company> companies = new ArrayList<Company>();
        companies = jdbcTemplateObject.query(sql, new CompanyMapper());
        return companies;
    }

    /**
     * Méthode count pour les compagnies.
     * @return nbEntrees le nombre d'entrées dans la BDD.
     */
    public int countCompanies() {
        String sql = "SELECT COUNT(*) FROM company";
        int nbCompanies =  jdbcTemplateObject.queryForObject(sql, Integer.class);
        return nbCompanies;
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
