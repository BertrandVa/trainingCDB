package main.java.com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.com.excilys.cdb.model.Company;

/**
 * Cette classe de DAO implémente les méthodes nécessaires à l'accès aux données
 * de la table company.
 * 
 * Le client ne demandant qu'un accès aux données, il n'y a pas de suppression,
 * de création ou d'update.
 * 
 * @author bertrand
 * 
 */

public class CompanyDAO {
	/**
	 * sert à récupérer notre instance de connexion
	 * 
	 * @see ConnectionFactory#getConnection()
	 * @see ConnectionFactory#getInstance()
	 * @see CompanyDAO#CompanyDAO(Connection)
	 */
	private Connection connection = null;
	
	/**
	 * logger
	 */
	final Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	
	
	/**
	 * Constructeur CompanyDAO La connexion est indépendante de notre DAO
	 * 
	 * @param conn
	 *            récupérer la connexion en cours
	 */
	public CompanyDAO(Connection conn) {
		this.connection = conn;
	}


	/**
     * Méthode d'affichage de tous les fabriquants
     * 
     * @return List
     *         une arraylist contenant l'ensemble de nos fabriquants
     */
	public List<Company> readAll() {
		List<Company> list = new ArrayList<Company>();
		try {
			ResultSet result = this.connection.createStatement().executeQuery(
					"SELECT * FROM company");
		
			while (result.next()) {
				Company company = new Company(result.getString("company.name"));
				company.setId(result.getLong("company.id"));
				list.add(company);
			}
			result.close();
			logger.debug("liste de fabriquants terminée");
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
		return list;
	}

}
