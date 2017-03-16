package com.excilys.cdb.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Company;

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

public enum CompanyDAO {
	INSTANCE;

	/**
	 * logger
	 */
	final Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

	/**
	 * Méthode d'affichage de tous les fabriquants
	 * 
	 * @return List une arraylist contenant l'ensemble de nos fabriquants
	 */
	public List<Company> readAll(long debut, int nbItems) {
		Connection connection = ConnectionFactory.getConnection();
		List<Company> list = new ArrayList<Company>();
		try {
			for (int i = 0; i < nbItems; i++) {
				long id = debut + i;
				ResultSet result = connection.createStatement().executeQuery(
						"SELECT MAX(id) FROM company");
				result.next();
				long maxId = result.getInt("MAX(id)");
				result.close();
				if (id <= maxId) {
					result = connection.createStatement()
							.executeQuery(
									"SELECT * FROM company WHERE id =" + id);

					if (result.first()) {
						Company company = new Company.CompanyBuilder(result.getString("company.name")).id(result.getLong("company.id")).build();
						list.add(company);
					}
					result.close();
				}
			}
			logger.debug("liste de fabriquants terminée");
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			try {
				connection.close();
				connection=null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

}
