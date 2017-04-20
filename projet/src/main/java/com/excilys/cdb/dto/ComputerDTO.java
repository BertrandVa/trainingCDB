package com.excilys.cdb.dto;

public class ComputerDTO {

    /**
     * Le nom de l'ordinateur Ce nom est modifiable.
     * @see Computer#getName()
     * @see Computer#setName(String)
     * @see Computer#Computer(String, Company, Date, Date)
     */
    private String name;
    public void setName(String name) {
        this.name = name;
    }

    public void setManufacturerId(String manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public void setIntroduceDate(String introduceDate) {
        this.introduceDate = introduceDate;
    }

    public void setDiscontinuedDate(String discontinuedDate) {
        this.discontinuedDate = discontinuedDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * L'id du fabriquant de l'ordinateur Cet ID est modifiable.
     * @see Computer#getManufacturer()
     * @see Computer#setManufacturer(Company)
     * @see Computer#Computer(String, Company, Date, Date)
     */
    private String manufacturerId;

    /**
     * Le nom du fabriquant de l'ordinateur.
     * @see Computer#getManufacturer()
     * @see Computer#setManufacturer(Company)
     * @see Computer#Computer(String, Company, Date, Date)
     */
    private String manufacturerName;
    /**
     * La date d'introduction de l'ordinateur Cette date est modifiable.
     * @see Computer#getIntroduceDate()
     * @see Computer#setIntroduceDate(Date)
     * @see Computer#Computer(String, Company, Date, Date)
     */
    private String introduceDate;
    /**
     * La date de départ de l'ordinateur Cette date est modifiable.
     * @see Computer#getDiscontinuedDate()
     * @see Computer#setDiscontinuedDate(Date)
     * @see Computer#Computer(String, Company, Date, Date)
     */
    private String discontinuedDate;
    /**
     * L'ID de l'ordinateur Cet ID n'est pas modifiable directement par
     * l'utilisateur.
     * @see Computer#setId(int)
     * @see Computer#getId()
     */
    private String id;

    /**
     * Constructeur Computer.
     * Crée un nouvel ordinateur avec un nom défini Les autres champs sont
     * facultatifs
     * @param builder
     *            Le builder de l'ordinateur
     * @see ComputerBuilder
     */
    private ComputerDTO(String name, String manufacturerId, String manufacturerName, String introduce, String discontinued, String id) {
        this.name = name;
        this.manufacturerId = manufacturerId;
        this.manufacturerName = manufacturerName;
        this.introduceDate = introduce;
        this.discontinuedDate = discontinued;
        this.id = id;
    }

    /**
     * Retourne le nom de l'ordinateur.
     * @return {@link Computer#name}
     */
    public String getName() {
        return name;
    }

    /**
     * Retourne l'id du fabriquant.
     * @return {@link Computer#manufacturer}
     */
    public String getManufacturerId() {
        return manufacturerId;
    }

    /**
     * Retourne l'id du fabriquant.
     * @return {@link Computer#manufacturer}
     */
    public String getManufacturerName() {
        return manufacturerName;
    }

    /**
     * Retourne la date d'arrivée de l'ordinateur.
     * @return {@link Computer#introduceDate}
     */
    public String getIntroduceDate() {
        return introduceDate;
    }

    /**
     * Retourne la date de départ de l'ordinateur.
     * @return {@link Computer#discontinuedDate}
     */
    public String getDiscontinuedDate() {
        return discontinuedDate;
    }

    /**
     * Retourne l'id de l'ordinateur.
     * @return {@link Computer#id}
     */
    public String getId() {
        return id;
    }
}
