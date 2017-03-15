package test.dbtest;

import static org.junit.Assert.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.com.excilys.cdb.model.Company;
import main.java.com.excilys.cdb.persistence.CompanyDAO;
import main.java.com.excilys.cdb.persistence.ConnectionFactory;

import org.junit.Test;

/**
 *On teste ici toutes les méthodes de CompanyDAO
 * @author bertrand
 * 
 */
public class CompanyDAOTest {

	private ConnectionFactory connection = ConnectionFactory.getInstance();

	@Test
	public void testReadAll() {
		Connection connect;
		List<Company> liste = new ArrayList<Company>();
		try {
			connect = connection.getConnection();
			CompanyDAO compDAO = new CompanyDAO(connect);
			liste = compDAO.readAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		assertEquals(liste.size(), 42);
	}

}
