package main.java.com.excilys.cdb.services;

import java.sql.Connection;
import java.sql.SQLException;
import main.java.com.excilys.cdb.dao.ComputerDAO;
import main.java.com.excilys.cdb.dao.ConnectionFactory;
import main.java.com.excilys.cdb.java.Computer;

public class ClientActions {

	private ConnectionFactory connection = ConnectionFactory.getInstance();
	private Connection connect;

	public void ListComputers() {
	};

	public void ListCompanies() {
	};

	public void ShowComputerDetails(int id) { // implémenter les cas d'erreurs
		Computer computer = new Computer(null, -1, null, null);
		try {
			connect = connection.getConnection();
			ComputerDAO compDAO = new ComputerDAO(connect);
			computer = compDAO.read(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out
				.println("Vous avez demandé des informations sur l'ordinateur");
		System.out.println("");
		System.out.println("Son nom est " + computer.getName());
		System.out
				.println("Il a été fabriqué par" + computer.getManufacturer());
		System.out.println("Il est arrivé dans l'entreprise le "
				+ computer.getIntroduceDate());
		System.out.println("Il a quitté la compagnie le "
				+ computer.getDiscontinuedDate());
	};

	public boolean CreateComputer(Computer computer) {// OK
	// ne peut être créé sans nom
		boolean fait = false;
		try {
			connect = connection.getConnection();
			ComputerDAO compDAO = new ComputerDAO(connect);
			if (computer.getName() != null) {
				compDAO.create(computer);
				fait = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fait;
	};

	public boolean UpdateComputer(Computer computer) { //OK
		boolean fait = false;
		try {
			connect = connection.getConnection();
			ComputerDAO compDAO = new ComputerDAO(connect);
			if (computer.getId() >= 0) {
				compDAO.update(computer);
				fait = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fait;
	};

	public boolean DeleteComputer(int id) {
		boolean fait = false;
		try {
			connect = connection.getConnection();
			ComputerDAO compDAO = new ComputerDAO(connect);
			if (id >= 0) {
				compDAO.delete(id);
				fait = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fait;
	};
}
