package com.excilys.cdb.application;

import com.excilys.cdb.services.ClientActions;

public class Menus {

    /**
     * Classe implémentant nos deux menus.
     * @author bertrand
     */

    /**
     * Menu principal de notre application.
     * Affiche uniquement les possibilités.
     * @see ConsoleApplication
     */
    public static void menuPrincipal() {
        System.out
                .println("Bonjour, bienvenue dans ce système de gestion des ordinateurs");
        System.out.println("Pour lister les ordinateurs, tapez 1");
        System.out.println("Pour lister les fournisseurs, tapez 2");
        System.out.println("Pour créer un nouvel ordinateur, tapez 3");
        System.out.println("Pour quitter, tapez 4");
    }

    /**
     * Menu contextuel de notre application apparaît quand on liste les
     * ordinateurs permet d'effectuer les tâches spécifiques aux ordinateurs.
     * @see ClientActions#menuPrincipal()
     * @see ConsoleApplication
     */
    public static void menuOrdinateur() {
        System.out
                .println("Bonjour, bienvenue dans ce système de gestion des ordinateurs");
        System.out.println("Pour mettre à jour une entrée, tapez 1");
        System.out.println("Pour supprimer une entrée, tapez 2");
        System.out
                .println("Pour obtenir plus de détails sur une entrée, tapez 3");
        System.out.println("Pour revenir au menu principal, tapez 4");
    }

}
