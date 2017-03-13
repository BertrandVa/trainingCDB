package main.test.dbtest;

import static org.junit.Assert.*;
import main.java.com.excilys.cdb.dao.ConnectionFactory;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import main.java.com.excilys.cdb.dao.ComputerDAO;
import main.java.com.excilys.cdb.java.Computer;

import org.junit.Test;

public class ComputerDAOTest {

	// aucun besoin de créer les tests de connexion, celle-ci étant testée dans
	// ConnectionFactoryTest
	private ConnectionFactory connection = ConnectionFactory.getInstance();

	@Test
	public void testCreateComputer() { // test de la création d'un ordinateur
		boolean create = false;
		String string = "test";
		Computer computer = new Computer(string, 1, null, null);
		Connection connect;
		try {
			connect = connection.getConnection();
			ComputerDAO compDAO = new ComputerDAO(connect);
			create = compDAO.create(computer);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertTrue(create);
	}

	@Test
	public void testRead() { // test récupération d'un ordinateur dans la BDD
								// seuivant son id
		Computer computer = new Computer(null, 0, null, null);
		Connection connect;
		int maxId = 0;
		try {
			connect = connection.getConnection();
			ResultSet result = connect.createStatement().executeQuery(
					"SELECT MAX(id) FROM computer");
			result.next();
			maxId = result.getInt("MAX(id)");
			ComputerDAO compDAO = new ComputerDAO(connect);
			computer = compDAO.read(maxId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertEquals(computer.getName(), "test");
		assertEquals(computer.getId(), maxId);
		assertEquals(computer.getManufacturer(), 1);
		assertNull(computer.getDiscontinuedDate());
		assertNull(computer.getIntroduceDate());

	}

	@Test
	public void testReadAll() { // test récupération d'un ordinateur dans la BDD suivant son id
		Connection connect;
		List<Computer> liste = new ArrayList<Computer>();
		try {
			connect = connection.getConnection();
			ComputerDAO compDAO = new ComputerDAO(connect);
			liste=compDAO.readAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertEquals(liste.size(),574);
	}

	@Test
	public void testUpdateComputer() {

		Computer computer = new Computer("test", 2, null, null);
		computer.setId(576);
		Connection connect;
		try {
			connect = connection.getConnection();
			ComputerDAO compDAO = new ComputerDAO(connect);
			compDAO.update(computer);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertEquals(computer.getName(), "test");
		assertEquals(computer.getId(), 576);
		assertEquals(computer.getManufacturer(), 2);
		assertNull(computer.getDiscontinuedDate());
		assertNull(computer.getIntroduceDate());
	}

	@Test
	public void testDeleteComputer() {
		Connection connect;
		int maxId = 0;
		int newMaxId = 0;
		try {
			connect = connection.getConnection();
			ComputerDAO compDAO = new ComputerDAO(connect);
			ResultSet result = connect.createStatement().executeQuery(
					"SELECT MAX(id) FROM computer");
			result.next();
			maxId = result.getInt("MAX(id)");
			compDAO.delete(maxId);
			ResultSet result2 = connect.createStatement().executeQuery(
					"SELECT MAX(id) FROM computer");
			result2.next();
			newMaxId = result2.getInt("MAX(id)");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertNotEquals(maxId, newMaxId);
	}

}
