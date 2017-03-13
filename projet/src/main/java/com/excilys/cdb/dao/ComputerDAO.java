package main.java.com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.com.excilys.cdb.java.Computer;

public class ComputerDAO {

	private Connection connection = null;

	public ComputerDAO(Connection conn) { // la Connexion est indépendante de
											// notre DAO
		this.connection = conn;
	}

	public boolean create(Computer computer) {
		boolean create = false;
		try {
			String SQL = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
			java.sql.PreparedStatement statement = null;
			statement = connection.prepareStatement(SQL);
			statement.setString(1, computer.getName());
			statement.setDate(2, computer.getIntroduceDate());
			statement.setDate(3, computer.getDiscontinuedDate());
			statement.setInt(4, computer.getManufacturer());
			statement.executeUpdate();
			create = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return create;
	}

	public boolean delete(Computer obj) {
		return false;
	}

	public boolean update(Computer computer) {
		boolean update = false;
		try {
			String SQL = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
			java.sql.PreparedStatement statement = null;
			statement = connection.prepareStatement(SQL);
			statement.setString(1, computer.getName());
			statement.setDate(2, computer.getIntroduceDate());
			statement.setDate(3, computer.getDiscontinuedDate());
			statement.setInt(4, computer.getManufacturer());
			statement.executeUpdate();
			update = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return update;
	}

	public Computer read(int id) {
		Computer computer = new Computer(null, -1, null, null);
		try {
			ResultSet result = this.connection.createStatement().executeQuery(
					"SELECT * FROM computer WHERE id =" + id);
			if (result.first())
				computer = new Computer(result.getString("computer.name"),
						result.getInt("computer.company_id"),
						result.getDate("computer.introduced"),
						result.getDate("computer.discontinued"));
			computer.setId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computer;
	}
}
