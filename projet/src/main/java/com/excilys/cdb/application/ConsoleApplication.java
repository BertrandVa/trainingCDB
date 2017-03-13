package main.java.com.excilys.cdb.application;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import main.java.com.excilys.cdb.java.Computer;
import main.java.com.excilys.cdb.services.ClientActions;

public class ConsoleApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		boolean continu = true; // pour continuer
		while (continu) {
			ClientActions.menuPrincipal();
			Scanner sc = new Scanner(System.in);
			int choice = 0;
			while (choice != 1 && choice != 2 && choice != 3 && choice != 4) {
				System.out.println("Veuillez choisir une option");
				boolean erreur;
				do {
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
			case 1:
				ClientActions.listComputers();
				boolean continu2 =true;
				while(continu2){
					ClientActions.menuOrdinateur();
					Scanner myScan = new Scanner(System.in);
					int choix = 0;
					while (choix != 1 && choix != 2 && choix != 3 && choix != 4) {
						System.out.println("Veuillez choisir une option");
						boolean erreur;
						do {
							erreur = false;
							try {
								choix = myScan.nextInt();
								sc.nextLine();
							} catch (InputMismatchException e) {
								erreur = true;
								sc.nextLine();
							}
						} while (erreur);
					}
					switch (choix)
					{
					  case 1:
						  int id;
						  System.out.println("Veuillez entrer l'id du champ à modifier");	
						  boolean erreur;
						  do {
							  erreur = false;
								try {
									id = sc.nextInt();
									sc.nextLine();
								} catch (InputMismatchException e) {
									erreur = true;
									sc.nextLine();
								}
							} while (erreur);						  
						  System.out.println("Parfait ! entrez le nouveau nom de cet ordinateur");
							boolean create;
							Computer computer = new Computer(null, -1, null, null);
							do {
								erreur = false;
								try {
									computer.setName(sc.next());
									sc.nextLine();
								} catch (InputMismatchException e) {
									erreur = true;
									sc.nextLine();
								}
							} while (erreur);
							System.out.println("Parfait ! entrez sa date d'acquisition yyyy MM dd ou 0 pour ignorer");
							do {
								erreur = false;
								try {
									 String dateString = sc.next();
									 DateFormat formatter = new SimpleDateFormat("yyyy MM dd");
									 Date date = formatter.parse(dateString);
									computer.setDiscontinuedDate(date);
									sc.nextLine();
								} catch (InputMismatchException e) {
									erreur = true;
									sc.nextLine();
								} catch (ParseException e) {
									erreur = true;
									sc.nextLine();
								}
							} while (erreur);
							System.out.println("Parfait ! entrez sa date de départ ou 0 pour ignorer");
							do {
								erreur = false;
								try {
									 String dateString = sc.next();
									 DateFormat formatter = new SimpleDateFormat("yyyy MM dd");
									 Date date = formatter.parse(dateString);
									computer.setIntroduceDate(date);
									sc.nextLine();
								} catch (InputMismatchException e) {
									erreur = true;
									sc.nextLine();
								} catch (ParseException e) {
									erreur = true;
									sc.nextLine();
								}
							} while (erreur);
							System.out.println("Parfait ! entrez l'id de son fournisseur");
							do {
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
						  System.out.println("Veuillez entrer l'id du champ à supprimer");	
						  do {
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
						  System.out.println("Veuillez entrer l'id du champ à afficher");	
						  do {
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
					    continu2=false;             
					}	
					myScan.close();
				}
				break;
			case 2:
				ClientActions.listCompanies();
				break;
			case 3:
				System.out.println("Parfait ! entrez le nom de ce nouvel ordinateur");
				boolean erreur;
				boolean create;
				Computer computer = new Computer(null, -1, null, null);
				do {
					erreur = false;
					try {
						computer.setName(sc.next());
						sc.nextLine();
					} catch (InputMismatchException e) {
						erreur = true;
						sc.nextLine();
					}
				} while (erreur);
				System.out.println("Parfait ! entrez sa date d'acquisition yyyy MM dd ou 0 pour ignorer");
				do {
					erreur = false;
					try {
						 String dateString = sc.next();
						 DateFormat formatter = new SimpleDateFormat("yyyy MM dd");
						 Date date = formatter.parse(dateString);
						computer.setDiscontinuedDate(date);
						sc.nextLine();
					} catch (InputMismatchException e) {
						erreur = true;
						sc.nextLine();
					} catch (ParseException e) {
						erreur = true;
						sc.nextLine();
					}
				} while (erreur);
				System.out.println("Parfait ! entrez sa date de départ ou 0 pour ignorer");
				do {
					erreur = false;
					try {
						 String dateString = sc.next();
						 DateFormat formatter = new SimpleDateFormat("yyyy MM dd");
						 Date date = formatter.parse(dateString);
						computer.setIntroduceDate(date);
						sc.nextLine();
					} catch (InputMismatchException e) {
						erreur = true;
						sc.nextLine();
					} catch (ParseException e) {
						erreur = true;
						sc.nextLine();
					}
				} while (erreur);
				System.out.println("Parfait ! entrez l'id de son fournisseur");
				do {
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
			sc.close();
		}

	}

}
