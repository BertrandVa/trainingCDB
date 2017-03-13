package main.test.dbtest;

import static org.junit.Assert.*;
import main.java.com.excilys.cdb.dao.ConnectionFactory;
import java.sql.Connection;
import java.sql.SQLException;

import main.java.com.excilys.cdb.dao.ComputerDAO;
import main.java.com.excilys.cdb.java.Computer;

import org.junit.Test;

public class ComputerDAOTest {

	//aucun besoin de créer les tests de connexion, celle-ci étant testée dans ConnectionFactoryTest
	private ConnectionFactory connection = ConnectionFactory.getInstance();
	
	@Test
	public void testCreateComputer() {
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
	public void testDeleteComputer() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateComputer() {
		fail("Not yet implemented");
	}

	@Test
	public void testReadInt() {
		fail("Not yet implemented");
	}

}
