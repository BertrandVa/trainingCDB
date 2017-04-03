package com.excilys.cdb.presentation.application;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.services.ClientActions;

/**
 * Cette classe est notre application à proprement parler, qui gère l'interface
 * en ligne de commande (entrées/sorties).
 * 
 * @author bertrand
 */

public class ConsoleApplication {

    /**
     * @param args
     *            les arguments
     */
    public static void main(String[] args) {
        /**
         * logger
         */
        final Logger logger = LoggerFactory.getLogger(ConsoleApplication.class);
        boolean continu = true; // permet la sortie de l'application
        Scanner sc = new Scanner(System.in);
        while (continu) {
            Menus.menuPrincipal();
            int choice = 0;
            while (choice != 1 && choice != 2 && choice != 3 && choice != 4) {
                /*
                 * On vérifie ici l'entrée de l'utilisateur Elle doit
                 * correspondre à un choix proposé
                 */
                System.out.println("Veuillez choisir une option valide");
                choice = SecureInput.secureInt(sc);

            }
            switch (choice) {
            /*
             * On propose ici les actions correspondant au choix de
             * l'utilisateur 1: liste les ordinateurs par pages 2: liste les
             * fabriquants par pages 3: crée un ordinateur dans la BDD 4:
             * supprime une compagnie 5: quitte l'application
             */
            case 1:
                System.out.println(
                        "Sélectionnez le premier id que vous désirez voir");
                int idComputer = SecureInput.secureInt(sc);
                System.out.println(
                        "Parfait ! entrez le nombre d'éléments à afficher");
                int nbElements = SecureInput.secureInt(sc);
                List<Computer> list = ClientActions.listComputers(idComputer,
                        nbElements, "computer.id", "'%'");
                for (int i = 0; i < list.size(); i++) {
                    System.out.println(
                            list.get(i).getId() + " " + list.get(i).getName());
                }
                boolean continu2 = true; // permet le retour au menu principal
                while (continu2) {
                    Menus.menuOrdinateur();
                    int choix = 0;
                    while (choix != 1 && choix != 2 && choix != 3
                            && choix != 4) {
                        /*
                         * On vérifie ici l'entrée de l'utilisateur Elle doit
                         * correspondre à un choix proposé
                         */
                        System.out
                                .println("Veuillez choisir une option valide");
                        choix = SecureInput.secureInt(sc);
                    }
                    switch (choix) {
                    /*
                     * On propose ici les actions correspondant au choix de
                     * l'utilisateur 1: mise à jour d'un ordinateur 2:
                     * suppression d'un ordinateur 3: affichage des détails d'un
                     * ordinateur 4: retour au menu principal
                     */
                    case 1:
                        int id = 0;
                        LocalDate date1 = null;
                        LocalDate date2 = null;
                        String name = "";
                        System.out.println(
                                "Veuillez entrer l'id du champ à modifier");
                        Computer computer = new Computer.ComputerBuilder(null)
                                .build();
                        id = SecureInput.secureInt(sc);
                        System.out.println(
                                "Parfait ! entrez le nouveau nom de cet ordinateur");
                        name = SecureInput.secureString(sc);
                        boolean create;
                        System.out.println(
                                "Parfait ! entrez sa date d'acquisition yyyy (entrée) MM (entrée) dd (entrée) ou 0 pour ignorer");
                        date1 = SecureInput.secureDate(sc);
                        System.out.println(
                                "Parfait ! entrez sa date de départ yyyy (entrée) MM (entrée) dd (entrée) ou 0 pour ignorer");
                        date2 = SecureInput.secureDate(sc);
                        System.out.println(
                                "Parfait ! entrez l'id de son fabriquant ou 0 pour ignorer");
                        Company company = new Company.CompanyBuilder(null)
                                .id(SecureInput.secureInt(sc)).build();
                        computer = new Computer.ComputerBuilder(name).id(id)
                                .manufacturer(company).introduceDate(date1)
                                .discontinuedDate(date2).build();

                        create = ClientActions.updateComputer(computer);
                        if (create) {
                            System.out.println("L'ordinateur a été mis à jour");
                        } else {
                            System.out.println("Il y a eu un problème");
                        }

                    case 2:
                        System.out.println(
                                "Veuillez entrer l'id du champ à supprimer");
                        id = sc.nextInt(SecureInput.secureInt(sc));
                        ClientActions.deleteComputer(id);
                        System.out.println("Action effectuée");

                    case 3:
                        System.out.println(
                                "Veuillez entrer l'id du champ à afficher");
                        id = SecureInput.secureInt(sc);
                        ClientActions.showComputerDetails(id);
                        break;

                    default:
                        continu2 = false;
                    }
                }
                break;

            case 2:
                System.out.println(
                        "Sélectionnez le premier id que vous désirez voir");
                int id = SecureInput.secureInt(sc);
                System.out.println(
                        "Parfait ! entrez le nombre d'éléments à afficher");
                int nbElementsVoulus = SecureInput.secureInt(sc);
                List<Company> listeComp = ClientActions.listCompanies(id,
                        nbElementsVoulus);
                for (int i = 0; i < listeComp.size(); i++) {
                    System.out.println(listeComp.get(i).getId() + " "
                            + listeComp.get(i).getName());
                }
                break;

            case 3:
                System.out.println(
                        "Parfait ! entrez le nom de ce nouvel ordinateur");
                boolean create;
                String name = "";
                LocalDate date1 = null;
                LocalDate date2 = null;
                name = SecureInput.secureString(sc);
                logger.debug(name);
                System.out.println(
                        "Parfait ! entrez sa date d'acquisition yyyy (entrée) MM (entrée) dd (entrée) ou 0 pour ignorer");
                date1 = SecureInput.secureDate(sc);
                System.out.println(
                        "Parfait ! entrez sa date de départ yyyy (entrée) MM (entrée) dd (entrée) ou 0 pour ignorer");

                date2 = SecureInput.secureDate(sc);
                System.out.println(
                        "Parfait ! entrez l'id de son fabriquant ou 0 pour ignorer");
                Company company = new Company.CompanyBuilder(null)
                        .id(SecureInput.secureInt(sc)).build();
                Computer computer = new Computer.ComputerBuilder(name)
                        .discontinuedDate(date2).introduceDate(date1)
                        .manufacturer(company).build();
                create = ClientActions.createComputer(computer);
                logger.debug(computer.getName());
                if (create) {
                    System.out.println("L'ordinateur a été ajouté");
                } else {
                    System.out.println("Il y a eu un problème");
                }
                break;
             
            case 4:
                System.out.println(
                        "Parfait ! entrez l'id de la compagnie à supprimer");
                       long idCompany = SecureInput.secureInt(sc);
                boolean fait = ClientActions.deleteCompany(idCompany);
                if(fait){
                    System.out.println("action effectuée");
                }
                else{
                    System.out.println("il y a eu un problème");
                }
                break;
              
            default:
                continu = false;
            }
        }
        sc.close();
    }
}
