package com.excilys.cdb.java;

import java.sql.Date;

public class Computer {
	
	private String name;
	private String manufacturer;
	private Date introduceDate;
	private Date discontinuedDate;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
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
	
	
	
}
