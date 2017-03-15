package main.java.com.excilys.cdb.application;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecureInput {
	/**
	 *Cette classe gère la sécurisation des entrées de l'utilisateur 
	 * 
	 * @author bertrand
	 * 
	 */
	
	/**
	 * logger
	 */
	final static Logger logger = LoggerFactory.getLogger(SecureInput.class);
	
	public static int secureInt(Scanner sc){
		int choice=0;
		boolean erreur;
		do {
			erreur = false;
			try {
				choice = sc.nextInt();
				sc.nextLine();
			} catch (InputMismatchException e) {
				erreur = true;
				sc.nextLine();
				logger.error(e.getMessage());
			}
		} while (erreur);
		return choice;
	}
	
	public static String secureString(Scanner sc){
		boolean erreur;
		String string = "";
		do {
			erreur = false;
			try {			
				sc.nextLine();
			} catch (InputMismatchException e) {
				logger.error(e.getMessage());
				erreur = true;
				sc.nextLine();
			}
		} while (erreur);
		return string;
		
	}
	

}
