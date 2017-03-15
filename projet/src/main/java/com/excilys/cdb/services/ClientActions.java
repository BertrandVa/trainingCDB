package main.java.com.excilys.cdb.services;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.com.excilys.cdb.model.Company;
import main.java.com.excilys.cdb.model.Computer;
import main.java.com.excilys.cdb.persistence.CompanyDAO;
import main.java.com.excilys.cdb.persistence.ComputerDAO;
import main.java.com.excilys.cdb.persistence.ConnectionFactory;

/**
 * Cette classe définit les 6 actions possibles pour le client : "List
 * computers, list companies, show computer details, create a computer, update a
 * computer et delete a computer"
 * 
 * Cette classe gère également les deux menus (menu principal et menu de gestion
 * des utilisateurs) de l'interface utilisateur.
 * 
 * @author bertrand
 * 
 */

public class ClientActions {

	/**
	 * Récupération de l'instance de connexion à la BDD
	 * 
	 * 
	 * /** logger
	 */
	final static Logger logger = LoggerFactory.getLogger(ClientActions.class);

	/**
	 * La connexion à proprement parler
	 * 
	 * @see ConnectionFactory#getConnection()
	 */
	private static Connection connect = ConnectionFactory
			.getConnection();

	/**
	 * Liste les ordinateurs en affichant leur nom et leur ID Par souci de
	 * lisibilité, affichage par pages de 10
	 * 
	 * @see ClientActions#menuPrincipal()
	 * @see ComputerDAO#readAll()
	 * @param sc
	 *            le scanner permettant de récupérer l'entrée utilisateur
	 */
	public static void listComputers(Scanner sc) {
		List<Computer> liste = new ArrayList<Computer>();
		int page = 0;
		int pageMax;
		ComputerDAO compDAO = new ComputerDAO(connect);
		liste = compDAO.readAll();
		pageMax = liste.size() / 10 + 1;
		while (page > pageMax || page <= 0) {
			System.out.println("Veuillez choisir une page à afficher");
			boolean erreur;
			do {
				erreur = false;
				try {
					page = sc.nextInt();
					sc.nextLine();
				} catch (InputMismatchException e) {
					logger.error(e.getMessage());
					erreur = true;
					sc.nextLine();
				}
			} while (erreur);
		}
		for (int i = page * 10 - 10; i < page * 10; ++i) {
			if (i < liste.size()) {
				System.out.println("id: " + liste.get(i).getId() + " nom: "
						+ liste.get(i).getName());
			}
		}
	}

	/**
	 * Liste les fabriquants en affichant leur nom et leur ID Par souci de
	 * lisibilité, affichage par pages de 10
	 * 
	 * @see ClientActions#menuPrincipal()
	 * @see CompanyDAO#readAll()
	 * @param sc
	 *            le scanner permettant de récupérer l'entrée utilisateur
	 */
	public static void listCompanies(Scanner sc) {
		List<Company> liste = new ArrayList<Company>();
		int page = 0;
		int pageMax;
		CompanyDAO compDAO = new CompanyDAO(connect);
		liste = compDAO.readAll();
		pageMax = liste.size() / 10 + 1;
		while (page > pageMax || page <= 0) {
			System.out.println("Veuillez choisir une page à afficher");
			boolean erreur;
			do {
				erreur = false;
				try {
					page = sc.nextInt();
					sc.nextLine();
				} catch (InputMismatchException e) {
					logger.error(e.getMessage());
					erreur = true;
					sc.nextLine();
				}
			} while (erreur);
		}
		for (int i = page * 10 - 10; i < page * 10; ++i) {
			if (i < liste.size()) {
				System.out.println("id: " + liste.get(i).getId() + " nom: "
						+ liste.get(i).getName());
			}
		}
	};

	/**
	 * Affiche les informations détaillées d'un ordinateur
	 * 
	 * @see ClientActions#menuOrdinateur()
	 * @see ComputerDAO#read(int)
	 * @param id
	 *            l'id de l'ordinateur à détailler
	 */
	public static void showComputerDetails(int id) {

		Computer computer = new Computer(null, null, null, null);
		ComputerDAO compDAO = new ComputerDAO(connect);
		computer = compDAO.read(id);
		if (computer.getName() != null) {
			System.out
					.println("Vous avez demandé des informations sur l'ordinateur");
			System.out.println("");
			System.out.println("Son nom est " + computer.getName());
			System.out.println("Il a été fabriqué par "
					+ computer.getManufacturer().getName());
			System.out.println("Il est arrivé dans l'entreprise le "
					+ computer.getIntroduceDate());
			System.out.println("Il a quitté la compagnie le "
					+ computer.getDiscontinuedDate());
		} else {
			System.out.println("cet ID n'existe pas");
		}
	}

	/**
	 * Permet la création d'un nouvel ordinateur dans la BDD
	 * 
	 * @see ClientActions#menuOrdinateur()
	 * @see ComputerDAO#create(Computer)
	 * @param computer
	 *            l'ordinateur à ajouter
	 */
	public static boolean createComputer(Computer computer) {
		boolean fait = false;
		ComputerDAO compDAO = new ComputerDAO(connect);
		if (computer.getName() != null && computer.getName() != "") {
			compDAO.create(computer);
			fait = true;
		}
		return fait;
	}

	/**
	 * Permet la mise à jour d'un ordinateur dans la BDD
	 * 
	 * @see ClientActions#menuOrdinateur()
	 * @see ComputerDAO#update(Computer)
	 * @param computer
	 *            l'ordinateur à mettre à jour
	 */
	public static boolean updateComputer(Computer computer) {
		boolean fait = false;
		ComputerDAO compDAO = new ComputerDAO(connect);
		if (computer.getId() >= 0) {
			compDAO.update(computer);
			fait = true;
		}
		return fait;
	};

	/**
	 * Permet la suppression d'un ordinateur dans la BDD
	 * 
	 * @see ClientActions#menuOrdinateur()
	 * @see ComputerDAO#delete(int)
	 * @param id
	 *            l'id de l'ordinateur à supprimer
	 */
	public static boolean deleteComputer(int id) {
		boolean fait = false;
		ComputerDAO compDAO = new ComputerDAO(connect);
		if (id >= 0) {
			compDAO.delete(id);
			fait = true;
		}
		return fait;
	};
}
