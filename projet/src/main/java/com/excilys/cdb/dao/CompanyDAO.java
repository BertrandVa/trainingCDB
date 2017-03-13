package main.java.com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.com.excilys.cdb.java.Company;

public class CompanyDAO {
	private Connection connection = null;

	public CompanyDAO(Connection conn) { // la Connexion est indépendante de
											// notre DAO

		this.connection = conn;
	}

	public List<Company> readAll() {
		List<Company> list = new ArrayList<Company>();
		try {
			ResultSet result = this.connection.createStatement().executeQuery(
					"SELECT * FROM company");
			
			while (result.next()) {
				Company company = new Company(result.getString("company.name"));
				company.setId(result.getInt("company.id"));
				list.add(company);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
