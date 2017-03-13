package main.java.com.excilys.cdb.java;

import java.util.Date;

public class Computer {
	
	
	private String name;
	private int manufacturer;
	private Date introduceDate;
	private Date discontinuedDate;
	private int id;
	
	public Computer(String name, int manufacturer,
			Date introduceDate, Date discontinuedDate) {
		super();
		this.name = name;
		this.manufacturer = manufacturer;
		this.introduceDate = introduceDate;
		this.discontinuedDate = discontinuedDate;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(int manufacturer) {
		this.manufacturer = manufacturer;
	}
	public Date getIntroduceDate() {
		return introduceDate;
	}
	public void setIntroduceDate(Date introduceDate) {
		this.introduceDate = introduceDate;
	}
	public Date getDiscontinuedDate() {
		return discontinuedDate;
	}
	public void setDiscontinuedDate(Date discontinuedDate) {
		this.discontinuedDate = discontinuedDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) { //méthode délicate, laisser au maximum la BDD choisir l'ID
		this.id = id;
	}	
	
}
