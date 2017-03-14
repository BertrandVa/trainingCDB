package main.java.com.excilys.cdb.java;

/**
 * On définit ici un fçournisseur, par son id et son nom
 * 
 * @author bertrand
 * 
 */

public class Company {

	/**
     * L'ID de la compagnie
     * Cet ID n'est pas modifiable par l'utilisateur
     * @see Company#getId()
     */
	private int id;
	/**
     * Le nom de la compagnie
     * Ce nom est modifiable par l'utilisateur
     * @see Company#getName()
     * @see Company#Company(String)
     * @see Company#setName(String)
     */
	private String name;

	/**
     * Constructeur Company
     * 
     * Crée une nouvelle compagnie avec un nom défini
     *
     * @param name
     *            Le nom de la compagnie
     * 
     * @see Company#name
     */

	public Company(String name) {
		this.name = name;
	}

	 /**
     * Retourne l'ID de la compagnie.
     * 
     * @return {@link Company#id}
     */
	public int getId() {
		return id;
	}
	
	/**
     * Met à jour l'ID de la compagnie
     * Ne pas utiliser dans une entrée User
     * Vérifier cohérence avec la BDD
     * 
     * @param id
     *            Le nouvel ID du fabriquant
     */
	public void setId(int id) {
		this.id = id;
	}

	 /**
     * Retourne le nom de la compagnie
     * 
     * @return {@link Company#name} 
     */
	public String getName() {
		return name;
	}

	/**
     * Met à jour le nom de la compagnie
     * @param id
     *            Le nouveau nom de la compagnie
     */
	public void setName(String name) {
		this.name = name;
	}

}
