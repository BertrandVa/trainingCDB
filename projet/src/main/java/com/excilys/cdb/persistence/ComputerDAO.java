package main.java.com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.com.excilys.cdb.model.Company;
import main.java.com.excilys.cdb.model.Computer;

/**
 * Cette classe de DAO implémente les méthodes nécessaires à l'accès aux données
 * de la table computer.
 * 
 * Le client demande ici un accès total, toutes les méthodes du CRUD sont donc
 * implémentées
 * 
 * @author bertrand
 * 
 */

public class ComputerDAO {

	/**
	 * sert à récupérer notre instance de connexion
	 * 
	 * @see ConnectionFactory#getConnection()
	 * @see ConnectionFactory#getInstance()
	 * @see ComputerDAO#ComputerDAO(Connection)
	 */
	private Connection connection = null;

	/**
	 * logger
	 */
	final Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

	/**
	 * Convertit notre Date en Timestamp lisible par la base de données
	 * l'expression ternaire renvoie null si besoin
	 * 
	 * @param date
	 *            date au format java.util.Date
	 * @return Timestamp date au format de la BDD
	 */
	public Timestamp getTimestamp(java.util.Date date) {
		return date == null ? null : new java.sql.Timestamp(date.getTime());
	}

	/**
	 * Constructeur ComputerDAO La connexion est indépendante de notre DAO
	 * 
	 * @param conn
	 *            récupérer la connexion en cours
	 */
	public ComputerDAO(Connection conn) {
		this.connection = conn;
	}

	/**
	 * Méthode create d'un ordinateur
	 * 
	 * @param computer
	 *            l'ordinateur à créer
	 * 
	 * @return boolean create true si tout s'est bien passé, false autrement
	 */
	public boolean create(Computer computer) {
		boolean create = false;
		long maxId = 0;
		try {
			ResultSet result = connection.createStatement().executeQuery(
					"SELECT MAX(id) FROM company");
			result.next();
			maxId = result.getInt("MAX(id)");
			result.close();
			logger.debug("Id maximal du manufacturer" + maxId);
			String SQL = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
			java.sql.PreparedStatement statement = null;
			statement = connection.prepareStatement(SQL);
			statement.setString(1, computer.getName());
			Timestamp timestamp1 = getTimestamp(computer.getIntroduceDate());
			Timestamp timestamp2 = getTimestamp(computer.getDiscontinuedDate());
			statement.setTimestamp(2, timestamp1);
			statement.setTimestamp(3, timestamp2);
			if (computer.getManufacturer().getId() > 0
					&& computer.getManufacturer().getId() <= maxId) {
				statement.setLong(4, computer.getManufacturer().getId());
				logger.debug("manufacturer ajouté");
			} else {
				statement.setNull(4, Types.INTEGER);
				logger.debug("pas de manufacturer valide");
			}
			statement.executeUpdate();
			statement.close();
			create = true;
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}finally{
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return create;
	}

	/**
	 * Méthode d'affichage d'un ordinateur
	 * 
	 * @param id
	 *            l'ordinateur à afficher
	 * 
	 * @return computer l'ordinateur sélectionné
	 */
	public Computer read(long id) {
		Computer computer = new Computer(null, null, null, null);
		Company company = new Company(null);
		try {
			String SQL = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id  AND computer.id = ?";
			java.sql.PreparedStatement statement = null;
			statement = connection.prepareStatement(SQL);
			statement.setLong(1, id);
			ResultSet result = statement.executeQuery();
			if (result.first())
				company.setId(result.getLong("computer.company_id"));
				company.setName(result.getString("company.name"));
				computer = new Computer(result.getString("computer.name"),
						company,
						result.getDate("computer.introduced"),
						result.getDate("computer.discontinued"));
			computer.setId(id);
			statement.close();
			result.close();	
			logger.debug("récupération de l'ordinateur réussie");
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}finally{
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
	 * Méthode d'affichage de tous les ordinateurs
	 * 
	 * @return List une arraylist contenant l'ensemble de nos ordinateurs
	 */
	public List<Computer> readAll() {
		List<Computer> list = new ArrayList<Computer>();
		try {
			ResultSet result = this.connection.createStatement().executeQuery(
					"SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id ORDER BY computer.id");
			while (result.next()) {
				Company company = new Company(null);
				Computer computer = new Computer(
						result.getString("computer.name"),
						null,
						result.getDate("computer.introduced"),
						result.getDate("computer.discontinued"));
				computer.setId(result.getInt("computer.id"));
				company.setId(result.getLong("computer.company_id"));
				company.setName(result.getString("company.name"));
				computer.setManufacturer(company);
				list.add(computer);
				
			}
			result.close();	
			logger.debug("liste d'ordinateurs terminée");
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}finally{
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
	 * Méthode de mise à jour d'un ordinateur
	 * 
	 * @param computer
	 *            l'ordinateur à mettre à jour
	 * 
	 * @return boolean update true si tout s'est bien passé, false autrement
	 */
	public boolean update(Computer computer) {
		boolean update = false;
		int maxId = 0;
		try {
			ResultSet result = connection.createStatement().executeQuery(
					"SELECT MAX(id) FROM company");
			result.next();
			maxId = result.getInt("MAX(id)");
			result.close();
			String SQL = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ?  WHERE id = ?";
			java.sql.PreparedStatement statement = null;
			statement = connection.prepareStatement(SQL);
			statement.setString(1, computer.getName());
			Timestamp timestamp1 = getTimestamp(computer.getIntroduceDate());
			Timestamp timestamp2 = getTimestamp(computer.getDiscontinuedDate());
			statement.setTimestamp(2, timestamp1);
			statement.setTimestamp(3, timestamp2);
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
		}finally{
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
	 * Méthode delete d'un ordinateur
	 * 
	 * @param id
	 *            l'id de l'ordinateur à supprimer
	 * 
	 * @return boolean delete true si tout s'est bien passé, false autrement
	 */
	public boolean delete(long id) {
		boolean delete = false;
		try {
			this.connection.createStatement().executeUpdate(
					"DELETE FROM computer WHERE id =" + id);
			logger.debug("suppression réussie");
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}finally{
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return delete;
	}
}
