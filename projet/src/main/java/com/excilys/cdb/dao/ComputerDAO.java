package main.java.com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.com.excilys.cdb.java.Computer;

public class ComputerDAO extends DAO<Computer> {

	public ComputerDAO(Connection conn) {
		super(conn);
	}

	@Override
	public boolean create(Computer obj) {
		return false;
	}

	@Override
	public boolean delete(Computer obj) {
		return false;
	}

	@Override
	public boolean update(Computer obj) {
		return false;
	}

	@Override
	public Computer read(int id) {    
	    Computer computer =new Computer(-1,null,null,null,null);
	    try {
	      ResultSet result = this.connect.createStatement().executeQuery("SELECT * FROM computer WHERE  = id" + id);
	      if(result.first())
	       computer = new Computer(
	          id,
	          result.getString("computer.name"),
	          result.getString("computer.manufacturer"),
	          result.getDate("computer.introduced"),
	          result.getDate("computer.discontinued"));         
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	    return computer;
	  }
}
