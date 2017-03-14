package main.java.com.excilys.cdb.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import main.java.com.excilys.cdb.dao.CompanyDAO;
import main.java.com.excilys.cdb.dao.ComputerDAO;

import main.java.com.excilys.cdb.dao.ConnectionFactory;
import main.java.com.excilys.cdb.java.Company;
import main.java.com.excilys.cdb.java.Computer;

public class ClientActions {

	private static ConnectionFactory connection = ConnectionFactory
			.getInstance();
	private static Connection connect;

	public static void menuPrincipal() {
		System.out
				.println("Bonjour, bienvenue dans ce système de gestion des ordinateurs");
		System.out.println("Pour lister les ordinateurs, tapez 1");
		System.out.println("Pour lister les fournisseurs, tapez 2");
		System.out.println("Pour créer un nouvel ordinateur, tapez 3");
		System.out.println("Pour quitter, tapez 4");
	}

	public static void menuOrdinateur() {
		System.out
				.println("Bonjour, bienvenue dans ce système de gestion des ordinateurs");
		System.out.println("Pour mettre à jour une entrée, tapez 1");
		System.out.println("Pour supprimer une entrée, tapez 2");
		System.out
				.println("Pour obtenir plus de détails sur une entrée, tapez 3");
		System.out.println("Pour revenir au menu principal, tapez 4");
	}

	public static void listComputers(Scanner sc) {
		List<Computer> liste = new ArrayList<Computer>();
		int page = 0;
		int pageMax;
		try {
			connect = connection.getConnection();
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

		} catch (SQLException e) {
			e.printStackTrace();
		}
	};

	public static void listCompanies(Scanner sc) {
		List<Company> liste = new ArrayList<Company>();
		int page = 0;
		int pageMax;
		try {
			connect = connection.getConnection();
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	};

	public static void showComputerDetails(int id) { // implémenter les cas
														// d'erreurs
		Computer computer = new Computer(null, -1, null, null);
		try {
			connect = connection.getConnection();
			ComputerDAO compDAO = new ComputerDAO(connect);
			computer = compDAO.read(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out
				.println("Vous avez demandé des informations sur l'ordinateur");
		System.out.println("");
		System.out.println("Son nom est " + computer.getName());
		System.out
				.println("Il a été fabriqué par" + computer.getManufacturer());
		System.out.println("Il est arrivé dans l'entreprise le "
				+ computer.getIntroduceDate());
		System.out.println("Il a quitté la compagnie le "
				+ computer.getDiscontinuedDate());
	};

	public static boolean createComputer(Computer computer) {// OK
		// ne peut être créé sans nom
		boolean fait = false;
		try {
			connect = connection.getConnection();
			ComputerDAO compDAO = new ComputerDAO(connect);
			if (computer.getName() != null && computer.getName() != "") {
				compDAO.create(computer);
				fait = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fait;
	};

	public static boolean updateComputer(Computer computer) { // OK
		boolean fait = false;
		try {
			connect = connection.getConnection();
			ComputerDAO compDAO = new ComputerDAO(connect);
			if (computer.getId() >= 0) {
				compDAO.update(computer);
				fait = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fait;
	};

	public static boolean deleteComputer(int id) { // OK
		boolean fait = false;
		try {
			connect = connection.getConnection();
			ComputerDAO compDAO = new ComputerDAO(connect);
			if (id >= 0) {
				compDAO.delete(id);
				fait = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fait;
	};
}
