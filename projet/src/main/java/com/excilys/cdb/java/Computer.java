package main.java.com.excilys.cdb.java;

import java.util.Date;

/**
 * On définit ici un simple ordinateur
 * Celui-ci contient un id, un fournisseur, une date d'arrivée et de départ et un id
 * 
 * @author bertrand
 * 
 */

public class Computer {

	/**
     * Le nom de l'ordinateur
     * Ce nom est modifiable 
     * @see Computer#getName()
     * @see Computer#setName(String)
     * @see Computer#Computer(String, int, Date, Date)
     */
	private String name;
	/**
     * L'id du fabriquant de l'ordinateur
     * Cet ID est modifiable 
     * @see Computer#getManufacturer()
     * @see Computer#setManufacturer(int)
     * @see Computer#Computer(String, int, Date, Date)
     */
	private int manufacturer;
	/**
     * La date d'introduction de l'ordinateur
     * Cette date est modifiable 
     * @see Computer#getIntroduceDate()
     * @see Computer#setIntroduceDate(Date)
     * @see Computer#Computer(String, int, Date, Date)
     */
	private Date introduceDate;
	/**
     * La date de départ de l'ordinateur
     * Cette date est modifiable 
     * @see Computer#getDiscontinuedDate()
     * @see Computer#setDiscontinuedDate(Date)
     * @see Computer#Computer(String, int, Date, Date)
     */
	private Date discontinuedDate;
	/**
     * L'ID de l'ordinateur
     * Cet ID n'est pas modifiable directement par l'utilisateur 
     * @see Computer#setId(int)
     * @see Computer#getId()
     */
	private int id;

	/**
     * Constructeur Computer
     * 
     * Crée un nouvel ordinateur avec un nom défini
     * Les autres champs sont facultatifs
     *
     * @param name
     *            Le nom de l'ordinateur
     * @param manufacturer
     *            L'id du fabriquant
     * @param introduceDate
     *            La date d'arrivée de l'ordinateur
     * @param name
     *            La date de départ de l'ordinateur
     *  
     * @see Computer#name
     * @see Computer#discontinuedDate
     * @see Computer#introduceDate
     * @see Computer#manufacturer
     */
	public Computer(String name, int manufacturer, Date introduceDate,
			Date discontinuedDate) {
		super();
		this.name = name;
		this.manufacturer = manufacturer;
		this.introduceDate = introduceDate;
		this.discontinuedDate = discontinuedDate;
	}

	/**
     * Retourne le nom de l'ordinateur
     * 
     * @return {@link Computer#name}
     */
	public String getName() {
		return name;
	}

	/**
     * Met à jour le nom de l'ordinateur
     * 
     * @param name
     *            Le nouveau nom de l'ordinateur
     */
	public void setName(String name) {
		this.name = name;
	}

	/**
     * Retourne l'id du fabriquant
     * 
     * @return {@link Computer#manufacturer}
     */
	public int getManufacturer() {
		return manufacturer;
	}

	/**
     * Met à jour le fabriquant de l'ordinateur
     * 
     * @param manufacturer
     *            Le nouvel ID du fabriquant
     */
	public void setManufacturer(int manufacturer) {
		this.manufacturer = manufacturer;
	}

	/**
     * Retourne la date d'arrivée de l'ordinateur
     * 
     * @return {@link Computer#introduceDate}
     */
	public Date getIntroduceDate() {
		return introduceDate;
	}

	/**
     * Met à jour la date d'arrivée de l'ordinateur
     * 
     * @param introduceDate
     *            La date d'introduction
     */
	public void setIntroduceDate(Date introduceDate) {
		this.introduceDate = introduceDate;
	}

	/**
     * Retourne la date de départ de l'ordinateur
     * 
     * @return {@link Computer#discontinuedDate} 
     */
	public Date getDiscontinuedDate() {
		return discontinuedDate;
	}

	/**
     * Met à jour la date de départ de l'ordinateur
     *
     * @param discontinuedDate
     *            La nouvelle date de départ
     */
	public void setDiscontinuedDate(Date discontinuedDate) {
		this.discontinuedDate = discontinuedDate;
	}

	/**
     * Retourne l'id de l'ordinateur
     * 
     * @return {@link Computer#id} 
     */
	public int getId() {
		return id;
	}

	/**
     * Met à jour l'ID de l'ordinateur
     * Ne pas utiliser dans une entrée User
     * Vérifier cohérence avec la BDD
     * 
     * @param id
     *            Le nouvel ID de l'ordinateur
     */
	public void setId(int id) { 
		this.id = id;
	}

}
