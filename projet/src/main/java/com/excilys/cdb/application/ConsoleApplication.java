package main.java.com.excilys.cdb.application;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import main.java.com.excilys.cdb.java.Computer;
import main.java.com.excilys.cdb.services.ClientActions;

/**
 *Cette classe est notre application à proprement parler, 
 *qui gère l'interface en ligne de commande (entrées/sorties)
 * 
 * @author bertrand
 * 
 */

public class ConsoleApplication {

	/**
	 * @param args
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		boolean continu = true; // permet la sortie de l'application
		Scanner sc = new Scanner(System.in);
		while (continu) {
			ClientActions.menuPrincipal();
			int choice = 0;
			while (choice != 1 && choice != 2 && choice != 3 && choice != 4) { 
				/*
				 * On vérifie ici l'entrée de l'utilisateur
				 * Elle doit correspondre à un choix proposé
				 */
				System.out.println("Veuillez choisir une option");
				boolean erreur;
				do {
					/*
					 * On sécurise ici l'entrée de l'utilisateur 
					 * sur un choix du menu principal
					 */
					erreur = false;
					try {
						choice = sc.nextInt();
						sc.nextLine();
					} catch (InputMismatchException e) {
						erreur = true;
						sc.nextLine();
					}
				} while (erreur);
			}
			switch (choice) {
			/*
			 * On propose ici les actions correspondant au choix de l'utilisateur
			 * 1: liste les ordinateurs par pages
			 * 2: liste les fabriquants par pages
			 * 3: crée un ordinateur dans la BDD
			 * 4: quitte l'application
			 */
			case 1:
				ClientActions.listComputers(sc);
				boolean continu2 = true; //permet le retour au menu principal
				while (continu2) {
					ClientActions.menuOrdinateur();
					int choix = 0;
					while (choix != 1 && choix != 2 && choix != 3 && choix != 4) {
						/*
						 * On vérifie ici l'entrée de l'utilisateur
						 * Elle doit correspondre à un choix proposé
						 */
						System.out.println("Veuillez choisir une option");
						boolean erreur;
						do {
							/*
							 * On sécurise ici l'entrée de l'utilisateur 
							 * sur un choix du menu contextuel
							 */
							erreur = false;
							try {
								choix = sc.nextInt();
								sc.nextLine();
							} catch (InputMismatchException e) {
								erreur = true;
								sc.nextLine();
							}
						} while (erreur);
					}
					switch (choix) {
					/*
					 * On propose ici les actions correspondant au choix de l'utilisateur
					 * 1: mise à jour d'un ordinateur
					 * 2: suppression d'un ordinateur
					 * 3: affichage des détails d'un ordinateur
					 * 4: retour au menu principal
					 */
					case 1:
						int id;
						System.out
								.println("Veuillez entrer l'id du champ à modifier");
						boolean erreur;
						do {
							/*
							 * On sécurise ici l'entrée de l'utilisateur 
							 */
							erreur = false;
							try {
								id = sc.nextInt();
								sc.nextLine();
							} catch (InputMismatchException e) {
								erreur = true;
								sc.nextLine();
							}
						} while (erreur);
						System.out
								.println("Parfait ! entrez le nouveau nom de cet ordinateur");
						boolean create;
						Computer computer = new Computer(null, -1, null, null);
						do {
							/*
							 * On sécurise ici l'entrée de l'utilisateur 
							 */
							erreur = false;
							try {
								computer.setName(sc.next());
								sc.nextLine();
							} catch (InputMismatchException e) {
								erreur = true;
								sc.nextLine();
							}
						} while (erreur);
						System.out
						.println("Parfait ! entrez sa date d'acquisition yyyy (entrée) MM (entrée) dd (entrée) ou 0 pour ignorer");
				do {
					/*
					 * On sécurise ici l'entrée de l'utilisateur 
					 */
					erreur = false;
					try {
						int year = sc.nextInt();
						sc.nextLine();
						if (year != 0) {
							int month = sc.nextInt();
							sc.nextLine();
							int day = sc.nextInt();
							sc.nextLine();
							Date date = new Date();
							date.setDate(day);
							date.setMonth(month - 1);
							date.setYear(year);
							computer.setIntroduceDate(date);
							sc.nextLine();
							System.out.println(date);
						}
					} catch (InputMismatchException e) {
						erreur = true;
						sc.nextLine();
					}
				} while (erreur);
						System.out
						.println("Parfait ! entrez sa date de départ yyyy (entrée) MM (entrée) dd (entrée) ou 0 pour ignorer");
				do {
					/*
					 * On sécurise ici l'entrée de l'utilisateur 
					 */
					erreur = false;
					try {
						int year = sc.nextInt();
						sc.nextLine();
						if (year != 0) {
							int month = sc.nextInt();
							sc.nextLine();
							int day = sc.nextInt();
							sc.nextLine();
							Date date = new Date();
							date.setDate(day);
							date.setMonth(month - 1);
							date.setYear(year);
							computer.setDiscontinuedDate(date);
							sc.nextLine();
							System.out.println(date);
						}
					} catch (InputMismatchException e) {
						erreur = true;
						sc.nextLine();
					}
				} while (erreur);
						System.out
								.println("Parfait ! entrez l'id de son fournisseur");
						do {
							/*
							 * On sécurise ici l'entrée de l'utilisateur 
							 */
							erreur = false;
							try {
								computer.setManufacturer(sc.nextInt());
								sc.nextLine();
							} catch (InputMismatchException e) {
								erreur = true;
								sc.nextLine();
							}
						} while (erreur);
						create = ClientActions.updateComputer(computer);
						if (create) {
							System.out.println("L'ordinateur a été mis à jour");
						} else {
							System.out.println("Il y a eu un problème");
						}
					case 2:
						System.out
								.println("Veuillez entrer l'id du champ à supprimer");
						do {
							/*
							 * On sécurise ici l'entrée de l'utilisateur
							 */
							erreur = false;
							try {
								id = sc.nextInt();
								sc.nextLine();
								ClientActions.deleteComputer(id);
								System.out.println("Action effectuée");
							} catch (InputMismatchException e) {
								erreur = true;
								sc.nextLine();
							}
						} while (erreur);

					case 3:
						System.out
								.println("Veuillez entrer l'id du champ à afficher");
						do {
							/*
							 * On sécurise ici l'entrée de l'utilisateur
							 */
							erreur = false;
							try {
								id = sc.nextInt();
								sc.nextLine();
								ClientActions.showComputerDetails(id);
							} catch (InputMismatchException e) {
								erreur = true;
								sc.nextLine();
							}
						} while (erreur);

						break;
					default:
						continu2 = false;
					}
				}
				break;
			case 2:
				ClientActions.listCompanies(sc);
				break;
			case 3:
				System.out
						.println("Parfait ! entrez le nom de ce nouvel ordinateur");
				boolean erreur;
				boolean create;
				Computer computer = new Computer(null, -1, null, null);
				do {
					/*
					 * On sécurise ici l'entrée de l'utilisateur
					 */
					erreur = false;
					try {
						computer.setName(sc.next());
						sc.nextLine();
					} catch (InputMismatchException e) {
						erreur = true;
						sc.nextLine();
					}
				} while (erreur);
				System.out
						.println("Parfait ! entrez sa date d'acquisition yyyy (entrée) MM (entrée) dd (entrée) ou 0 pour ignorer");
				do {
					/*
					 * On sécurise ici l'entrée de l'utilisateur
					 */
					erreur = false;
					try {
						int year = sc.nextInt();
						sc.nextLine();
						if (year != 0) {
							int month = sc.nextInt();
							sc.nextLine();
							int day = sc.nextInt();
							sc.nextLine();
							Date date = new Date();
							date.setDate(day);
							date.setMonth(month - 1);
							date.setYear(year);
							computer.setIntroduceDate(date);
							sc.nextLine();
							System.out.println(date);
						}
					} catch (InputMismatchException e) {
						erreur = true;
						sc.nextLine();
					}
				} while (erreur);
				System.out
						.println("Parfait ! entrez sa date de départ yyyy (entrée) MM (entrée) dd (entrée) ou 0 pour ignorer");
				do {
					/*
					 * On sécurise ici l'entrée de l'utilisateur
					 */
					erreur = false;
					try {
						int year = sc.nextInt();
						sc.nextLine();
						if (year != 0) {
							int month = sc.nextInt();
							sc.nextLine();
							int day = sc.nextInt();
							sc.nextLine();
							Date date = new Date();
							date.setDate(day);
							date.setMonth(month - 1);
							date.setYear(year);
							computer.setDiscontinuedDate(date);
							sc.nextLine();
							System.out.println(date);
						}
					} catch (InputMismatchException e) {
						erreur = true;
						sc.nextLine();
					}
				} while (erreur);
				System.out.println("Parfait ! entrez l'id de son fournisseur");
				do {
					/*
					 * On sécurise ici l'entrée de l'utilisateur
					 */
					erreur = false;
					try {
						computer.setManufacturer(sc.nextInt());
						sc.nextLine();
					} catch (InputMismatchException e) {
						erreur = true;
						sc.nextLine();
					}
				} while (erreur);
				create = ClientActions.createComputer(computer);
				if (create) {
					System.out.println("L'ordinateur a été ajouté");
				} else {
					System.out.println("Il y a eu un problème");
				}
				break;
			default:
				continu = false;
			}
		}
		sc.close();
	}
}
