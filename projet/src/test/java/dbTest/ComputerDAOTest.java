package dbTest;

import static org.junit.Assert.*;
import java.io.File;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.ComputerDAO;

public class ComputerDAOTest {
	
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String JDBC_URL = "jdbc:mysql://localhost/dbUnit?zeroDateTimeBehavior=convertToNull";
	private static final String USER = "admindb";
	private static final String PASSWORD = "qwerty1234";
	
	
	@Before
	public void importDataSet() throws Exception {
		IDataSet dataSet = readDataSet();
		cleanlyInsert(dataSet);
	}
	
	private IDataSet readDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(new File("src/test/java/dbTest/dataset.xml"));
	}
	
	private void cleanlyInsert(IDataSet dataSet) {
		try {
		IDatabaseTester databaseTester = new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, USER, PASSWORD);
		databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
		databaseTester.setDataSet(dataSet);
		
			databaseTester.onSetup();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Test
	public void findsAndReadsExistingComputerById() throws Exception {
		Computer computer = new Computer.ComputerBuilder(null).build();
		ComputerDAO compDAO = ComputerDAO.INSTANCE;
		computer = compDAO.read(574);		
		assertEquals(computer.getId(),574);
		assertEquals(computer.getName(),("iPhone 4S"));
		assertEquals(computer.getManufacturer().getId(),(1));
	}

}
