package com.excilys.cdb.services;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.CompanyDAO;
import com.excilys.cdb.persistence.ComputerDAO;

/**
 * Cette classe définit les 6 actions possibles pour le client : "List
 * computers, list companies, show computer details, create a computer, update a
 * computer et delete a computer".
 * @author bertrand
 */

public class ClientActions {

    /**
     * logger.
     */
    static final Logger LOGGER = LoggerFactory.getLogger(ClientActions.class);

    /**
     * Liste les ordinateurs en affichant leur nom et leur ID Par souci de
     * lisibilité, affichage par pages de 10.
     * @see ClientActions#menuPrincipal()
     * @see ComputerDAO#readAll()
     * @param debut
     *              le premier id à afficher
     * @param nbItems
     *              le nombre d'items à afficher
     * @return liste
     *              la liste des ordinateurs
     */
    public static List<Computer> listComputers(long debut, int nbItems, String champ, String search) {
        List<Computer> liste = new ArrayList<Computer>();
        ComputerDAO compDAO = ComputerDAO.INSTANCE;
        liste = compDAO.readAll(debut, nbItems, champ, search);
        return liste;
    }

    /**
     * Liste les fabriquants en affichant leur nom et leur ID.
     * @see ClientActions#menuPrincipal()
     * @see CompanyDAO#readAll()
     * @param debut
     *              le premier id à afficher
     * @param nbItems
     *              le nombre d'items à afficher
     * @return liste
     *              le nombre d'items à afficher
     */
    public static List<Company> listCompanies(long debut, int nbItems) {
        List<Company> liste = new ArrayList<Company>();
        CompanyDAO compDAO = CompanyDAO.INSTANCE;
        liste = compDAO.readAll(debut, nbItems);
        return liste;
    };

    /**
     * Affiche les informations détaillées d'un ordinateur.
     * @see ClientActions#menuOrdinateur()
     * @see ComputerDAO#read(int)
     * @param id
     *            l'id de l'ordinateur à détailler
     */
    public static Computer showComputerDetails(long id) {

        Computer computer = new Computer.ComputerBuilder(null).build();
        ComputerDAO compDAO = ComputerDAO.INSTANCE;
        computer = compDAO.read(id);
        return computer;
    }

    /**
     * Permet la création d'un nouvel ordinateur dans la BDD.
     * @see ClientActions#menuOrdinateur()
     * @see ComputerDAO#create(Computer)
     * @param computer
     *            l'ordinateur à ajouter
     * @return fait
     *              true si la création a eu lieu
     */
    public static boolean createComputer(Computer computer) {
        boolean fait = false;
        ComputerDAO compDAO = ComputerDAO.INSTANCE;
        if (computer.getName() != null && computer.getName() != "") {
            compDAO.create(computer);
            fait = true;
        }
        return fait;
    }

    /**
     * Permet la mise à jour d'un ordinateur dans la BDD.
     * @see ClientActions#menuOrdinateur()
     * @see ComputerDAO#update(Computer)
     * @param computer
     *            l'ordinateur à mettre à jour
     * @return fait
     *              true si l'update a eu lieu
     */
    public static boolean updateComputer(Computer computer) {
        boolean fait = false;
        ComputerDAO compDAO = ComputerDAO.INSTANCE;
        if (computer.getId() >= 0) {
            compDAO.update(computer);
            fait = true;
        }
        return fait;
    };

    /**
     * Permet la suppression d'un ordinateur dans la BDD.
     * @see ComputerDAO#delete(int)
     * @param id
     *            l'id de l'ordinateur à supprimer
     * @return fait
     *              true si la suppression a eu lieu
     */
    public static boolean deleteComputer(int id) {
        boolean fait = false;
        ComputerDAO compDAO = ComputerDAO.INSTANCE;
        if (id >= 0) {
            compDAO.delete(id);
            fait = true;
        }
        return fait;
    };

    /**
     * Permet de récupérer le nombre d'ordinateurs dans la BDD.
     * @see ComputerDAO#update(Computer)
     * @return nbComputer
     *             le nombre d'ordinateurs dans la BDD
     */
    public static int countComputer() {
        ComputerDAO compDAO = ComputerDAO.INSTANCE;
        int nbComputer = compDAO.countComputer();
            return nbComputer;
    };

    /**
     * Permet de récupérer le nombre de fabriquants dans la BDD.
     * @return nbCompanies
     *             le nombre de fabriquants dans la BDD
     */
    public static int countCompanies() {
        CompanyDAO compDAO = CompanyDAO.INSTANCE;
        int nbCompanies = compDAO.countCompanies();
            return nbCompanies;
    };

    /**
     * Permet de récupérer le nombre de pages dans la BDD.
     * @see ComputerDAO#update(Computer)
     * @return nbPages
     *             le nombre d'ordinateurs dans la BDD
     * @param nbId
     *              le nombre d'ordinateurs par page
     */
    public static int maxPages(int nbId) {
        ComputerDAO compDAO = ComputerDAO.INSTANCE;
        int nbPages = compDAO.countPages(nbId);
            return nbPages;
    };
    
    /**
     * Permet de supprimer des compagnies et les ordinateurs associés.
     */
    public static boolean deleteCompany(long id) {
        CompanyDAO compDAO = CompanyDAO.INSTANCE;
        boolean fait = compDAO.deleteCompanyAndRelatedComputers(id);
            return fait;
    };
}
