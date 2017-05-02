package com.excilys.cdb.presentation.application;

import java.util.InputMismatchException;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class SecureInput {
    /**
     * Cette classe gère la sécurisation des entrées de l'utilisateur.
     * @author bertrand
     */

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SecureInput.class);

    /**
     * Retourne un entier et rien d'autre.
     * @param sc
     *            le scanner utilisé pour la lecture
     * @return choice
     *            l'entier entré par l'utilisateur
     */
    public static int secureInt(Scanner sc) {
        int choice = 0;
        boolean erreur;
        do {
            erreur = false;
            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                erreur = true;
                sc.nextLine();
                LOGGER.error(e.getMessage());
            }
        } while (erreur);
        return choice;
    }
}
