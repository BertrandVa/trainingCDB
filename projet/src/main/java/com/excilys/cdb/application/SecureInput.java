package main.java.com.excilys.cdb.application;

import java.time.LocalDate;
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
	
	/**
     * Retourne un entier et rien d'autre
     * 
     * @param Scanner 
     * 			le scanner utilisé pour la lecture
     * 
     * @return int
     * 			l'entier entré par l'utilisateur
     */
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
	
	/**
     * Retourne une string et rien d'autre
     * 
     * @param Scanner 
     * 			le scanner utilisé pour la lecture
     * 
     * @return string
     * 			la string entrée par l'utilisateur
     */
	public static String secureString(Scanner sc){
		boolean erreur;
		String string = "";
		do {
			erreur = false;
			try {			
				string=sc.nextLine();
			} catch (InputMismatchException e) {
				logger.error(e.getMessage());
				erreur = true;
				sc.nextLine();
			}
		} while (erreur);
		return string;		
	}
	
	/**
     * Retourne une date valide et rien d'autre
     * 
     * @param Scanner 
     * 			le scanner utilisé pour la lecture
     * 
     * @return LocalDate
     * 			la date entrée par l'utilisateur
     */
	public static LocalDate secureDate(Scanner sc){
		boolean erreur;
		LocalDate date =null;
		do {
			erreur = false;
			try {
				int year = sc.nextInt();
				sc.nextLine();
				if (year != 0) {
					boolean invalidMonth=true;
					int month=0;
					while(invalidMonth){
						month = sc.nextInt();
						sc.nextLine();
						if(month>0 && month<=12){
							invalidMonth=false;
						}
					}
					boolean invalidDay=true;
					int day = 0;
					while(invalidDay){
						day = sc.nextInt();
						sc.nextLine();
						if(day>0 && day<=31){
							invalidDay=false;
						}
					}
					date=LocalDate.of(year, month, day);
					System.out.println(date);
				} else if (year == 0) {
					date=null;
				}
			} catch (InputMismatchException e) {
				logger.error(e.getMessage());
				erreur = true;
				sc.nextLine();
			}
		} while (erreur);
		return date;
		
	}
	

}
