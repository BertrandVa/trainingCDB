package com.excilys.cdb.presentation.application;

import java.util.Scanner;
import com.excilys.cdb.services.controller.ClientActions;

/**
 * Cette classe est notre application à proprement parler, qui gère l'interface
 * en ligne de commande (entrées/sorties).
 * @author bertrand
 */

public class ConsoleApplication {

    /**
     * @param args
     *            les arguments
     */
    public static void main(String[] args) {
        boolean continu = true;
        Scanner sc = new Scanner(System.in);
        while (continu) {
            System.out.println("entrez l'id de la compagnie à supprimer");
            long idCompany = SecureInput.secureInt(sc);
            boolean fait = ClientActions.deleteCompany(idCompany);
            if (fait) {
                System.out.println("action effectuée");
                continu = false;
            } else {
                System.out.println("il y a eu un problème");
            }
            break;
        }
        sc.close();
    }
}
