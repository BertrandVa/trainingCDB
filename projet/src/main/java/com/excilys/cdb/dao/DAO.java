package main.java.com.excilys.cdb.dao;

import java.sql.Connection;

public abstract class DAO<T> {
	
	protected Connection connect = null;
	
	public DAO(Connection conn){
		this.connect=conn;
	}	
	
	//méthodes pour le CRUD à implémenter
	
	public abstract boolean create(T obj); 
	public abstract boolean delete(T obj);
	public abstract boolean update(T obj);
	public abstract T read(int id);
	
	
}
