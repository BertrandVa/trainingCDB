package com.excilys.cdb.model;

/**
 * On définit ici un fçournisseur, par son id et son nom.
 * @author bertrand
 */

public class Company {

    /**
     * L'ID de la compagnie Cet ID n'est pas modifiable par l'utilisateur.
     * @see Company#getId()
     */
    private long id;
    /**
     * Le nom de la compagnie Ce nom est modifiable par l'utilisateur.
     * @see Company#getName()
     * @see Company#Company(String)
     * @see Company#setName(String)
     */
    private String name;

    /**
     * Constructeur Company. Crée une nouvelle compagnie avec un nom défini.
     * @param builder
     *            Le builder de la compagnie
     * @see CompanyBuilder
     */
    private Company(CompanyBuilder builder) {
        this.name = builder.name;
        this.id = builder.id;
    }

    /**
     * Retourne l'ID de la compagnie.
     * @return {@link Company#id}
     */
    public long getId() {
        return id;
    }

    /**
     * Retourne le nom de la compagnie.
     * @return {@link Company#name}
     */
    public String getName() {
        return name;
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
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Company other = (Company) obj;
        if (id != other.id) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Company [id=" + id + ", name=" + name + "]";
    }

    public static class CompanyBuilder {
        private long id;
        private String name;

        /**
         * Builder de notre fabriquant.
         * @param name
         *              le nom du fabriquant
         */
        public CompanyBuilder(String name) {
            this.name = name;
        }

        /**
         * Builder de notre fabriquant.
         * @param id
         *              l'id du fabriquant
         * @return this
         *              l'id
         */
        public CompanyBuilder id(long id) {
            this.id = id;
            return this;
        }

        /**
         * Builder de notre fabriquant.
         * @return this
         *             le fabriquant
         */
        public Company build() {
            return new Company(this);
        }
    }
}
