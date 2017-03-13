package main.java.com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import main.java.com.excilys.cdb.java.Computer;

public class ComputerDAO {

	private Connection connection = null;

	public Timestamp getTimestamp(java.util.Date date) { // fonction de
															// conversion de la
															// Date en timestamp
															// pour la BDD
		return date == null ? null : new java.sql.Timestamp(date.getTime());
	}

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
			Timestamp timestamp1 = getTimestamp(computer.getIntroduceDate());
			Timestamp timestamp2 = getTimestamp(computer.getDiscontinuedDate());
			statement.setTimestamp(2, timestamp1);
			statement.setTimestamp(3, timestamp2);
			statement.setInt(4, computer.getManufacturer());
			statement.executeUpdate();
			create = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return create;
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

	public List<Computer> readAll() {
		List<Computer> list = new ArrayList<Computer>();
		try {
			ResultSet result = this.connection.createStatement().executeQuery(
					"SELECT * FROM computer");

			while (result.next()) {
				Computer computer = new Computer(
						result.getString("computer.name"),
						result.getInt("computer.company_id"),
						result.getDate("computer.introduced"),
						result.getDate("computer.discontinued"));
				computer.setId(result.getInt("computer.id"));
				list.add(computer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public boolean update(Computer computer) {
		boolean update = false;
		try {
			String SQL = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ?  WHERE id = ?";
			java.sql.PreparedStatement statement = null;
			statement = connection.prepareStatement(SQL);
			statement.setString(1, computer.getName());
			Timestamp timestamp1 = getTimestamp(computer.getIntroduceDate());
			Timestamp timestamp2 = getTimestamp(computer.getDiscontinuedDate());
			statement.setTimestamp(2, timestamp1);
			statement.setTimestamp(3, timestamp2);
			statement.setInt(4, computer.getManufacturer());
			statement.setInt(5, computer.getId());
			statement.executeUpdate();
			update = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return update;
	}

	public boolean delete(int id) {
		boolean delete = false;
		try {
			this.connection.createStatement().executeUpdate(
					"DELETE FROM computer WHERE id =" + id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return delete;
	}
}
