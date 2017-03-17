package com.excilys.cdb.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * On définit ici un simple ordinateur. Celui-ci contient un id, un fournisseur,
 * une date d'arrivée et de départ et un id.
 * @author bertrand
 */

public class Computer {

    /**
     * Le nom de l'ordinateur Ce nom est modifiable.
     * @see Computer#getName()
     * @see Computer#setName(String)
     * @see Computer#Computer(String, Company, Date, Date)
     */
    private String name;
    /**
     * L'id du fabriquant de l'ordinateur Cet ID est modifiable.
     * @see Computer#getManufacturer()
     * @see Computer#setManufacturer(Company)
     * @see Computer#Computer(String, Company, Date, Date)
     */
    private Company manufacturer;
    /**
     * La date d'introduction de l'ordinateur Cette date est modifiable.
     * @see Computer#getIntroduceDate()
     * @see Computer#setIntroduceDate(Date)
     * @see Computer#Computer(String, Company, Date, Date)
     */
    private LocalDate introduceDate;
    /**
     * La date de départ de l'ordinateur Cette date est modifiable.
     * @see Computer#getDiscontinuedDate()
     * @see Computer#setDiscontinuedDate(Date)
     * @see Computer#Computer(String, Company, Date, Date)
     */
    private LocalDate discontinuedDate;
    /**
     * L'ID de l'ordinateur Cet ID n'est pas modifiable directement par
     * l'utilisateur.
     * @see Computer#setId(int)
     * @see Computer#getId()
     */
    private long id;

    /**
     * Constructeur Computer.
     * Crée un nouvel ordinateur avec un nom défini Les autres champs sont
     * facultatifs
     * @param builder
     *            Le builder de l'ordinateur
     * @see ComputerBuilder
     */
    private Computer(ComputerBuilder builder) {
        this.name = builder.name;
        this.manufacturer = builder.manufacturer;
        this.introduceDate = builder.introduceDate;
        this.discontinuedDate = builder.discontinuedDate;
        this.id = builder.id;
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
    public Company getManufacturer() {
        return manufacturer;
    }

    /**
     * Retourne la date d'arrivée de l'ordinateur.
     * @return {@link Computer#introduceDate}
     */
    public LocalDate getIntroduceDate() {
        return introduceDate;
    }

    /**
     * Retourne la date de départ de l'ordinateur.
     * @return {@link Computer#discontinuedDate}
     */
    public LocalDate getDiscontinuedDate() {
        return discontinuedDate;
    }

    /**
     * Retourne l'id de l'ordinateur.
     * @return {@link Computer#id}
     */
    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }
        if (!(o instanceof Computer)) {
            return false;
        }
        Computer computer = (Computer) o;
        return id == computer.id && Objects.equals(name, computer.name)
                && Objects.equals(introduceDate, computer.introduceDate)
                && Objects.equals(discontinuedDate, computer.discontinuedDate)
                && Objects.equals(manufacturer, computer.manufacturer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, manufacturer, introduceDate,
                discontinuedDate, id);

    }

    @Override
    public String toString() {
        return "Computer [name=" + name + ", manufacturer=" + manufacturer
                + ", introduceDate=" + introduceDate + ", discontinuedDate="
                + discontinuedDate + ", id=" + id + "]";
    }

    public static class ComputerBuilder {
        private String name;
        private Company manufacturer;
        private LocalDate introduceDate;
        private LocalDate discontinuedDate;
        private long id;

        /**
         * Builder de notre ordinateur.
         * @param name
         *              le nom de l'ordinateur
         */
        public ComputerBuilder(String name) {
            this.name = name;
        }

        /**
         * Builder de notre ordinateur.
         * @param id
         *              l'id de l'ordinateur
         * @return this
         *              l'ordinateur
         */
        public ComputerBuilder id(long id) {
            this.id = id;
            return this;
        }

        /**
         * Builder de notre ordinateur.
         * @param manufacturer
         *              le fabriquant de l'ordinateur
         * @return this
         *              l'ordinateur
         */
        public ComputerBuilder manufacturer(Company manufacturer) {
            this.manufacturer = manufacturer;
            return this;
        }

        /**
         * Builder de notre ordinateur.
         * @param introduceDate
         *              la date d'arrivée de l'ordinateur
         * @return this
         *              l'ordinateur
         */
        public ComputerBuilder introduceDate(LocalDate introduceDate) {
            this.introduceDate = introduceDate;
            return this;
        }

        /**
         * Builder de notre ordinateur.
         * @param discontinuedDate
         *              la date de départ de l'ordinateur
         * @return this
         *              l'ordinateur
         */
        public ComputerBuilder discontinuedDate(LocalDate discontinuedDate) {
            this.discontinuedDate = discontinuedDate;
            return this;
        }

        /**
         * Builder de notre ordinateur.
         * @return this
         *             l'ordinateur
         */
        public Computer build() {
            return new Computer(this);
        }
    }
}
