package com.excilys.cdb.persistence;

import java.sql.Connection;
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
 * de la table computer.
 * Le client demande ici un accès total, toutes les méthodes du CRUD sont donc
 * implémentées
 * @author bertrand
 */

public enum ComputerDAO {
    INSTANCE;

    /**
     * logger.
     */
    final Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

    /**
     * Méthode create d'un ordinateur.
     * @param computer
     *            l'ordinateur à créer
     * @return boolean create true si tout s'est bien passé, false autrement
     */
    public long create(Computer computer) {
        Connection connection = ConnectionFactory.getConnection();
        long id = 0;
        long maxId = 0;
        if (computer.getName() != null && computer.getName() != "") {
            try {
                ResultSet result = connection.createStatement().executeQuery(
                        "SELECT MAX(id) FROM company");
                result.next();
                maxId = result.getInt("MAX(id)");
                result.close();
                logger.debug("Id maximal du manufacturer" + maxId);
                String sql = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
                java.sql.PreparedStatement statement = null;
                statement = connection.prepareStatement(sql);
                statement.setString(1, computer.getName());
                if (computer.getIntroduceDate() != null) {
                    statement.setDate(2,
                            java.sql.Date.valueOf(computer.getIntroduceDate()));
                } else {
                    statement.setDate(2, null);
                }
                if (computer.getDiscontinuedDate() != null) {
                    if (computer.getIntroduceDate() == null
                            || computer.getIntroduceDate().isBefore(
                                    computer.getDiscontinuedDate())) {
                        statement.setDate(3, java.sql.Date.valueOf(computer
                                .getDiscontinuedDate()));
                    } else {
                        statement.setDate(3, null);
                    }
                } else {
                    statement.setDate(3, null);
                }
                if (computer.getManufacturer() != null) {
                    if (computer.getManufacturer().getId() > 0
                            && computer.getManufacturer().getId() <= maxId) {
                        statement
                                .setLong(4, computer.getManufacturer().getId());
                        logger.debug("manufacturer ajouté");
                    } else {
                        statement.setNull(4, Types.INTEGER);
                        logger.debug("pas de manufacturer valide");
                    }
                } else {
                    statement.setNull(4, Types.INTEGER);
                }
               statement.executeUpdate();
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    id = generatedKeys.getLong(1);
                }
                statement.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
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
        Connection connection = ConnectionFactory.getConnection();
        try {
            String sql = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id  WHERE computer.id = ?";
            java.sql.PreparedStatement statement = null;
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
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
                        result.getLong("computer.company_id")).build();
                computer = new Computer.ComputerBuilder(
                        result.getString("computer.name"))
                        .manufacturer(company).introduceDate(date1)
                        .discontinuedDate(date2).id(id).build();
                statement.close();
            }
            result.close();
            logger.debug("récupération de l'ordinateur réussie");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return computer;
    }

    /**
     * Méthode d'affichage de tous les ordinateurs.
     * @return List une arraylist contenant l'ensemble de nos ordinateurs
     * @param debut
     *              le premier id à afficher
     * @param nbItems
     *              le nombre d'items à afficher
     */
    public List<Computer> readAll(long debut, int nbItems) {
        List<Computer> list = new ArrayList<Computer>();
        Connection connection = ConnectionFactory.getConnection();
        try {
            ResultSet result = connection.createStatement().executeQuery(
                    "SELECT MAX(id) FROM computer");
            result.next();
            long maxId = result.getInt("MAX(id)");
            result.close();
            for (int i = 0; i < nbItems; i++) {
                long id = debut + i;
                if (id <= maxId) {
                    String sql = "SELECT * FROM `computer` LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ?";
                    java.sql.PreparedStatement statement = null;
                    statement = connection.prepareStatement(sql);
                    statement.setLong(1, id);
                    System.out.println(statement);
                    result = statement.executeQuery();
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
                        logger.debug(result.getString("computer.name"));
                        Company company = new Company.CompanyBuilder(
                                result.getString("company.name")).id(
                                result.getLong("computer.company_id")).build();
                        Computer computer = new Computer.ComputerBuilder(
                                result.getString("computer.name")).id(id)
                                .introduceDate(date1).discontinuedDate(date2)
                                .manufacturer(company).build();
                        list.add(computer);
                        logger.debug(computer.toString());
                    }
                }
                result.close();
            }
            logger.debug("liste de fabriquants terminée");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
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
        Connection connection = ConnectionFactory.getConnection();
        boolean update = false;
        int maxId = 0;
        try {
            ResultSet result = connection.createStatement().executeQuery(
                    "SELECT MAX(id) FROM company");
            result.next();
            maxId = result.getInt("MAX(id)");
            result.close();
            String sql = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ?  WHERE id = ?";
            java.sql.PreparedStatement statement = null;
            statement = connection.prepareStatement(sql);
            statement.setString(1, computer.getName());
            statement.setDate(2,
                    java.sql.Date.valueOf(computer.getIntroduceDate()));
            statement.setDate(3,
                    java.sql.Date.valueOf(computer.getDiscontinuedDate()));
            if (computer.getManufacturer().getId() > 0
                    && computer.getManufacturer().getId() <= maxId) {
                statement.setLong(4, computer.getManufacturer().getId());
                logger.debug("manufacturer ajouté");
            } else {
                statement.setNull(4, Types.INTEGER);
                logger.debug("manufacturer invalide");
            }
            statement.setLong(5, computer.getId());
            statement.executeUpdate();
            statement.close();
            update = true;
            logger.debug("mise à jour réussie");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
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
        Connection connection = ConnectionFactory.getConnection();
        boolean delete = false;
        try {
            connection.createStatement().executeUpdate(
                    "DELETE FROM computer WHERE id =" + id);
            logger.debug("suppression réussie");
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return delete;
    }

    /**
     * Méthode count pour les ordinateurs.
     * @return nbEntrees
     *                  le nombre d'entrées dans la BDD.
     */
    public int countComputer() {
        Connection connection = ConnectionFactory.getConnection();
        int maxId = 0;
        try {
            ResultSet result = connection.createStatement().executeQuery(
                    "SELECT COUNT(*) AS count FROM computer");
            result.next();
            maxId = result.getInt("count");
            result.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return maxId;
    }

    /**
     * Méthode count pour les pages selon le nombre d'affichages.
     * @return nbPages
     *                  le nombre de pages dans la BDD.
     * @param nbId
     *                  le nombre d'ids affichés par pages
     */
    public int countPages(int nbId) {
        Connection connection = ConnectionFactory.getConnection();
        int maxId = 0;
        int nbPages = 0;
        try {
            ResultSet result = connection.createStatement().executeQuery(
                    "SELECT COUNT(*) AS count FROM computer");
            result.next();
            maxId = result.getInt("count");
            result.close();
            nbPages = maxId / nbId + 1;
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return nbPages;
    }
}
