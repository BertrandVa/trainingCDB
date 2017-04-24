package com.excilys.cdb.dto;

public class CompanyDTO {

    /**
     * L'ID de la compagnie Cet ID n'est pas modifiable par l'utilisateur.
     * @see Company#getId()
     */
    private String id;
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Le nom de la compagnie Ce nom est modifiable par l'utilisateur.
     * @see Company#getName()
     * @see Company#Company(String)
     * @see Company#setName(String)
     */
    private String name;

    /**
     * Constructeur Company. Crée une nouvelle compagnie avec un nom défini.
     * @param name
     *            Le nom de la compagnie
     * @param id
     *            L'id de la compagnie
     * @see CompanyBuilder
     */
    public CompanyDTO(String name, String id) {
        this.name = name;
        this.id = id;
    }

    /**
     * Retourne l'ID de la compagnie.
     * @return {@link Company#id}
     */
    public String getId() {
        return id;
    }

    /**
     * Retourne le nom de la compagnie.
     * @return {@link Company#name}
     */
    public String getName() {
        return name;
    }
}
