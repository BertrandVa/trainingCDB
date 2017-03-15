package main.java.com.excilys.cdb.model;



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
	private long id;
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
	public long getId() {
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
	public void setId(long id) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + "]";
	}


}
