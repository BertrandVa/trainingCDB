package com.excilys.cdb.model;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/**
 * On définit ici un simple ordinateur. Celui-ci contient un id, un fournisseur,
 * une date d'arrivée et de départ et un id.
 * @author bertrand
 */
@Entity
@Table(name = "computer")
public class Computer {

    /**
     * Le nom de l'ordinateur Ce nom est modifiable.
     * @see Computer#getName()
     * @see Computer#setName(String)
     * @see Computer#Computer(String, Company, Date, Date)
     */
    @Column(name = "name")
    private String name;
    /**
     * L'id du fabriquant de l'ordinateur Cet ID est modifiable.
     * @see Computer#getManufacturer()
     * @see Computer#setManufacturer(Company)
     * @see Computer#Computer(String, Company, Date, Date)
     */
    @ManyToOne
    private Company company;
    /**
     * La date d'introduction de l'ordinateur Cette date est modifiable.
     * @see Computer#getIntroduceDate()
     * @see Computer#setIntroduceDate(Date)
     * @see Computer#Computer(String, Company, Date, Date)
     */
    @Column(name = "introduced")
    private Timestamp introduceDate;
    /**
     * La date de départ de l'ordinateur Cette date est modifiable.
     * @see Computer#getDiscontinuedDate()
     * @see Computer#setDiscontinuedDate(Date)
     * @see Computer#Computer(String, Company, Date, Date)
     */
    @Column(name = "discontinued")
    private Timestamp discontinuedDate;
    /**
     * L'ID de l'ordinateur Cet ID n'est pas modifiable directement par
     * l'utilisateur.
     * @see Computer#setId(int)
     * @see Computer#getId()
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
        this.company = builder.manufacturer;
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
     * @return {@link Computer#company}
     */
    public Company getManufacturer() {
        return company;
    }

    /**
     * Retourne la date d'arrivée de l'ordinateur.
     * @return {@link Computer#introduceDate}
     */
    public LocalDate getIntroduceDate() {
        if (this.introduceDate != null) {
        return introduceDate.toLocalDateTime().toLocalDate();
        } else {
            return null;
        }
    }

    /**
     * Retourne la date de départ de l'ordinateur.
     * @return {@link Computer#discontinuedDate}
     */
    public LocalDate getDiscontinuedDate() {
        if (this.discontinuedDate != null) {
            return discontinuedDate.toLocalDateTime().toLocalDate();
            } else {
                return null;
            }
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
                && Objects.equals(company, computer.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, company, introduceDate,
                discontinuedDate, id);

    }

    @Override
    public String toString() {
        return "Computer [name=" + name + ", manufacturer=" + company
                + ", introduceDate=" + introduceDate + ", discontinuedDate="
                + discontinuedDate + ", id=" + id + "]";
    }

    public static class ComputerBuilder {
        private String name;
        private Company manufacturer;
        private Timestamp introduceDate;
        private Timestamp discontinuedDate;
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
            if (introduceDate != null) {
            this.introduceDate = Timestamp.valueOf(introduceDate.atStartOfDay());
            } else {
                this.introduceDate = null;
            }
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
            if (discontinuedDate != null) {
                this.discontinuedDate = Timestamp.valueOf(discontinuedDate.atStartOfDay());
                } else {
                    this.discontinuedDate = null;
                }
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
