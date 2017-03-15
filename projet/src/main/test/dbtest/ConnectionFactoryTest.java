package main.test.dbtest;

import static org.junit.Assert.*;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import main.java.com.excilys.cdb.persistence.ConnectionFactory;

/**
 *On teste ici toutes les méthodes de ConnectionFactory
 * 
 * @author bertrand
 * 
 */
public class ConnectionFactoryTest {
	
	@Test
	public void testGetInstance() {  //on vérifie la présence d'une instance de connexion
		ConnectionFactory connect =null;
		connect = ConnectionFactory.getInstance();
		assertNotNull(connect);
	}

	@Test
	public void testGetConnection() throws SQLException { //On vérifie que la connexion à la base de données se fasse bien		
		Connection conn = null;
		try{
		 conn = ConnectionFactory.getInstance().getConnection();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		assertNotNull(conn);	
	}

}
