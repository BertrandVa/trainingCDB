package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

/**
 * Cette classe de DAO implémente les méthodes nécessaires à l'accès aux données
 * de la table computer. Le client demande ici un accès total, toutes les
 * méthodes du CRUD sont donc implémentées
 * @author bertrand
 */

public enum ComputerDAO {
    INSTANCE;

    /**
     * logger.
     */
    static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

    /**
     * Méthode create d'un ordinateur.
     * @param computer
     *            l'ordinateur à créer
     * @return boolean create true si tout s'est bien passé, false autrement
     */
    public long create(Computer computer) {
        long id = 0;
        long maxId = 0;
        if (computer.getName() != null && computer.getName() != "") {
            try (Connection connection = HikariConnectionFactory
                    .getConnection();) {
                try (ResultSet result = connection.createStatement()
                        .executeQuery("SELECT MAX(id) FROM company");) {
                    result.next();
                    maxId = result.getInt("MAX(id)");
                }
                LOGGER.debug("Id maximal du manufacturer" + maxId);
                String sql = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
                id = prepare(connection, computer, maxId, sql, 0);
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
            }
        }
        return id;
    }

    /**
     * Méthode d'affichage d'un ordinateur.
     * @param id
     *            l'ordinateur à afficher
     * @return computer l'ordinateur sélectionné
     */
    public Computer read(long id) {
        Computer computer = new Computer.ComputerBuilder(null).build();
        try (Connection connection = HikariConnectionFactory.getConnection();) {
            String sql = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id  WHERE computer.id = ?";
            try (java.sql.PreparedStatement statement = connection
                    .prepareStatement(sql);) {
                statement.setLong(1, id);
                try (ResultSet result = statement.executeQuery();) {
                    if (result.first()) {
                        Timestamp timestamp1 = result
                                .getTimestamp("computer.introduced");
                        Timestamp timestamp2 = result
                                .getTimestamp("computer.discontinued");
                        LocalDate date1 = null;
                        LocalDate date2 = null;
                        if (timestamp1 != null) {
                            date1 = timestamp1.toLocalDateTime().toLocalDate();
                        }
                        if (timestamp2 != null) {
                            date2 = timestamp2.toLocalDateTime().toLocalDate();

                        }
                        Company company = new Company.CompanyBuilder(
                                result.getString("company.name")).id(
                                        result.getLong("computer.company_id"))
                                        .build();
                        computer = new Computer.ComputerBuilder(
                                result.getString("computer.name"))
                                        .manufacturer(company)
                                        .introduceDate(date1)
                                        .discontinuedDate(date2).id(id).build();
                    }
                }
            }
            LOGGER.debug("récupération de l'ordinateur réussie");
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
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
     *             le champ pour trier les ordinateurs
     * @param match
     *             les caractères recherchés.
     */
    public List<Computer> readAll(long debut, int nbItems, String match, String order) {
        List<Computer> list = new ArrayList<Computer>();
        try (Connection connection = HikariConnectionFactory.getConnection();) {
            String sql = "SELECT * FROM `computer` LEFT JOIN company ON computer.company_id = company.id WHERE computer.name COLLATE latin1_GENERAL_CI like %s OR company.name COLLATE latin1_GENERAL_CI like %s ORDER BY %s LIMIT " + nbItems + " OFFSET " + debut;
            sql = String.format(sql, match, match, order);
            LOGGER.debug(sql);
            java.sql.PreparedStatement statement = connection
                    .prepareStatement(sql);
            LOGGER.debug(statement.toString());
            ResultSet result = statement.executeQuery();
                while (result.next()) {
                        Timestamp timestamp1 = result
                                .getTimestamp("computer.introduced");
                        Timestamp timestamp2 = result
                                .getTimestamp("computer.discontinued");
                        LocalDate date1 = null;
                        LocalDate date2 = null;
                        if (timestamp1 != null) {
                            date1 = timestamp1.toLocalDateTime().toLocalDate();
                        }
                        if (timestamp2 != null) {
                            date2 = timestamp2.toLocalDateTime().toLocalDate();
                        }
                        Company company = new Company.CompanyBuilder(
                                result.getString("company.name")).id(
                                        result.getLong("computer.company_id"))
                                        .build();
                        LOGGER.debug(company.toString());
                        Computer computer = new Computer.ComputerBuilder(
                                result.getString("computer.name"))
                                        .id(result.getLong("computer.id"))
                                        .introduceDate(date1)
                                        .discontinuedDate(date2)
                                        .manufacturer(company).build();
                        list.add(computer);
                        LOGGER.debug(computer.toString());
                    }
            LOGGER.debug("liste de fabriquants terminée");
            result.close();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return list;
    }

    /**
     * Méthode de mise à jour d'un ordinateur.
     * @param computer
     *            l'ordinateur à mettre à jour
     * @return boolean update true si tout s'est bien passé, false autrement
     */
    public boolean update(Computer computer) {
        boolean update = false;
        int maxId = 0;
        try (Connection connection = HikariConnectionFactory.getConnection();) {
            if (computer.getName() != null) {
                try (ResultSet result = connection.createStatement()
                        .executeQuery("SELECT MAX(id) FROM company");) {
                    result.next();
                    maxId = result.getInt("MAX(id)");
                }
                String sql = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ?  WHERE id = ?";
                prepare(connection, computer, maxId, sql, computer.getId());
                update = true;
                LOGGER.debug("mise à jour réussie");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return update;
    }

    /**
     * Méthode delete d'un ordinateur.
     * @param id
     *            l'id de l'ordinateur à supprimer
     * @return boolean delete true si tout s'est bien passé, false autrement
     */
    public boolean delete(long id) {
        boolean delete = false;
        try (Connection connection = HikariConnectionFactory.getConnection();) {
            int maxId = 0;
            try (ResultSet result = connection.createStatement()
                    .executeQuery("SELECT MAX(id) FROM computer");) {
                result.next();
                maxId = result.getInt("MAX(id)");
            }
            if (id < maxId) {
                connection.createStatement()
                        .executeUpdate("DELETE FROM computer WHERE id =" + id);
                LOGGER.debug("suppression réussie");
                delete = true;
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return delete;
    }

    /**
     * Méthode count pour les ordinateurs.
     * @return nbEntrees le nombre d'entrées dans la BDD.
     * @param match
     *              la chaine de caractères à matcher
     */
    public int countComputer(String match) {
        int maxId = 0;
        try (Connection connection = HikariConnectionFactory.getConnection();) {
            try (ResultSet result = connection.createStatement()
                    .executeQuery("SELECT COUNT(*) AS count FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name COLLATE latin1_GENERAL_CI like " + match + " OR company.name COLLATE latin1_GENERAL_CI like " + match)) {
                result.next();
                maxId = result.getInt("count");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return maxId;
    }

    /**
     * Méthode count pour les pages selon le nombre d'affichages.
     * @return nbPages le nombre de pages dans la BDD.
     * @param nbId
     *            le nombre d'ids affichés par pages
     * @param match
     *             la chaine de caractères à matcher
     */
    public int countPages(int nbId, String match) {
        int maxId = 0;
        int nbPages = 0;
        try (Connection connection = HikariConnectionFactory.getConnection();) {
            try (ResultSet result = connection.createStatement()
                    .executeQuery("SELECT COUNT(*) AS count FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name COLLATE latin1_GENERAL_CI like " + match + " OR company.name COLLATE latin1_GENERAL_CI like " + match)) {
                result.next();
                maxId = result.getInt("count");
            }
            if (nbId != 0) {
                if (maxId % nbId == 0) {
                    nbPages = maxId / nbId;
                } else {
                    nbPages = maxId / nbId + 1;
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return nbPages;
    }

    /**
     * Méthode prepare utilisée pour créer et mettre à jour un ordinateur.
     * @return id l'id de l'ordinateur.
     * @param connection
     *            la connection
     * @param computer
     *              l'ordinateur en question
     * @param maxId
     *              l'Id maximal de la compagnie
     * @param sql
     *              la requête d'insertion ou de mise à jour
     * @param idUpdate
     *              l'Id de l'ordinateur à mettre à jour en cas d'update
     */
    private long prepare(Connection connection, Computer computer, long maxId,
            String sql, long idUpdate) {
        long id = 0;
        try (PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, computer.getName());
            if (computer.getIntroduceDate() != null) {
                statement.setDate(2,
                        java.sql.Date.valueOf(computer.getIntroduceDate()));
            } else {
                statement.setDate(2, null);
            }
            if (computer.getDiscontinuedDate() != null) {
                if (computer.getIntroduceDate() == null
                        || computer.getIntroduceDate()
                                .isBefore(computer.getDiscontinuedDate())) {
                    statement.setDate(3, java.sql.Date
                            .valueOf(computer.getDiscontinuedDate()));
                } else {
                    statement.setDate(3, null);
                }
            } else {
                statement.setDate(3, null);
            }
            if (computer.getManufacturer() != null) {
                if (computer.getManufacturer().getId() > 0
                        && computer.getManufacturer().getId() <= maxId) {
                    statement.setLong(4, computer.getManufacturer().getId());
                    LOGGER.debug("manufacturer ajouté");
                } else {
                    statement.setNull(4, Types.INTEGER);
                    LOGGER.debug("pas de manufacturer valide");
                }
            } else {
                statement.setNull(4, Types.INTEGER);
            }
            if (idUpdate != 0) {
                statement.setLong(5, idUpdate);
            }
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return id;
    }
}
