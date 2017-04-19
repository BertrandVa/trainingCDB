package com.excilys.cdb.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
    static ApplicationContext context =
            new ClassPathXmlApplicationContext("Spring-Modules.xml");
    static ComputerDAO computerDAO = (ComputerDAO) context.getBean("computerDAO");     
    static CompanyDAO companyDAO = (CompanyDAO) context.getBean("companyDAO"); 

    /**
     * Liste les ordinateurs en affichant leur nom et leur ID Par souci de
     * lisibilité, affichage par pages de 10.
     * @see ClientActions#menuPrincipal()
     * @see ComputerDAO#readAll()
     * @param debut
     *              le premier id à afficher
     * @param nbItems
     *              le nombre d'items à afficher
     * @param order
     *              le champ pour le tri
     * @param search
     *              les caractères à rechercher dans le nom
     * @return liste
     *              la liste des ordinateurs
     */
    public static List<Computer> listComputers(long debut, int nbItems, String search, String order) {
        List<Computer> liste = new ArrayList<Computer>();
        liste = computerDAO.readAll(debut, nbItems, search, order);
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
        liste = companyDAO.readAll(debut, nbItems);
        return liste;
    };

    /**
     * Affiche les informations détaillées d'un ordinateur.
     * @see ClientActions#menuOrdinateur()
     * @see ComputerDAO#read(int)
     * @param id
     *            l'id de l'ordinateur à détailler
     * @return computer
     *          l'ordinateur à afficher
     */
    public static Computer showComputerDetails(long id) {

        Computer computer = new Computer.ComputerBuilder(null).build();
        computer = computerDAO.read(id);
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
        if (StringUtils.isNotEmpty(computer.getName())) {
            computerDAO.create(computer);
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
        if (computer.getId() >= 0) {
            computerDAO.update(computer);
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
        if (id >= 0) {
            computerDAO.delete(id);
            fait = true;
        }
        return fait;
    };

    /**
     * Permet de récupérer le nombre d'ordinateurs dans la BDD.
     * @see ComputerDAO#update(Computer)
     * @return nbComputer
     *             le nombre d'ordinateurs dans la BDD
     * @param match
     *              la chaine de caractères à matcher
     */
    public static int countComputer(String match) {
        int nbComputer = computerDAO.countComputer(match);
            return nbComputer;
    };

    /**
     * Permet de récupérer le nombre de fabriquants dans la BDD.
     * @return nbCompanies
     *             le nombre de fabriquants dans la BDD
     */
    public static int countCompanies() {
        int nbCompanies = companyDAO.countCompanies();
            return nbCompanies;
    };

    /**
     * Permet de récupérer le nombre de pages dans la BDD.
     * @see ComputerDAO#update(Computer)
     * @return nbPages
     *             le nombre d'ordinateurs dans la BDD
     * @param nbId
     *              le nombre d'ordinateurs par page
     * @param match
     *              la chaine de caractères à match
     */
    public static int maxPages(int nbId, String match) {
        int nbPages = computerDAO.countPages(nbId, match);
            return nbPages;
    };

    /**
     * Permet de supprimer des compagnies et les ordinateurs associés.
     * @return fait
     *              boolean indiquant si l'action a été effectuée
     * @param id
     *              l'id de la compagnie à supprimer
     */
    public static boolean deleteCompany(long id) {
        boolean fait = companyDAO.deleteCompanyAndRelatedComputers(id);
            return fait;
    };
}
