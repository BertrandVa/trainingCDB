package com.excilys.cdb.services.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.excilys.cdb.core.dto.ComputerDTO;
import com.excilys.cdb.persistence.ComputerDAO;
import com.excilys.cdb.services.ClientActions;
import com.excilys.cdb.services.exceptions.DiscontinuedDateException;
import com.excilys.cdb.services.exceptions.IntroduceDateException;
import com.excilys.cdb.services.exceptions.NameException;

/**
 * Cette classe est une classe de service permettant l'ajout d'un ordinateur dans la base de données.
 * @author bertrand
 */
public class ComputerForm {
    /**
     * Il s'agit d'une hash map avec nos erreurs et les champs touchés.
     * @see ComputerForm#getErreurs()
     */
    private Map<String, String> erreurs = new HashMap<String, String>();

    /**
     * récpération de notre hash map d'erreurs.
     * @see ComputerForm#erreurs
     * @return erreurs
     *                 notre hash map d'erreurs.
     */
    public Map<String, String> getErreurs() {
        return erreurs;
    }

    /**
     * Création de notre ordinateur dans la BDD en vérifiant les champs entrés par l'utilisateur.
     * Si un champ n'est pas valide, l'erreur est renvoyée et l'ordinateur n'est pas créé.
     * @see ComputerForm#checkName(String)
     * @see ComputerForm#checkDiscontinuedDate(String, LocalDate)
     * @see ComputerForm#checkIntroduceDate(String)
     * @see AddComputer.java
     * @see ComputerDAO.java
     * @param computerDto
     *              notre objet DTO
     * @return computer
     *               l'ordinateur créé en base de données
     */
    public boolean createComputer(ComputerDTO computerDto) {
        String name = computerDto.getName();
        boolean fait = false;
        try {
            checkName(name);
            checkIntroduceDate(computerDto.getIntroduceDate());
            checkDiscontinuedDate(computerDto.getDiscontinuedDate(), computerDto.getIntroduceDate());
        } catch (NameException | IntroduceDateException | DiscontinuedDateException  e) {
            setErreur(e.getChamp(), e.getMessage());
        }
        if (erreurs.isEmpty()) {
            fait = ClientActions.createComputer(computerDto);
           }
        return fait;
    }

    /**
     * Mise à jour de notre ordinateur dans la BDD en vérifiant les champs entrés par l'utilisateur.
     * Si un champ n'est pas valide, l'erreur est renvoyée et l'ordinateur n'est pas créé.
     * @see ComputerForm#checkName(String)
     * @see ComputerForm#checkDiscontinuedDate(String, LocalDate)
     * @see ComputerForm#checkIntroduceDate(String)
     * @see AddComputer.java
     * @see ComputerDAO.java
     * @param computerDto
     *              notre objet DTO
     * @return computer
     *               l'ordinateur créé en base de données
     */
    public boolean updateComputer(ComputerDTO computerDto) {
        String name = computerDto.getName();
        boolean fait = false;
        try {
            checkName(name);
            checkIntroduceDate(computerDto.getIntroduceDate());
            checkDiscontinuedDate(computerDto.getDiscontinuedDate(), computerDto.getIntroduceDate());
        } catch (NameException | IntroduceDateException | DiscontinuedDateException e) {
            setErreur(e.getChamp(), e.getMessage());
        }
        if (erreurs.isEmpty()) {
            fait = ClientActions.updateComputer(computerDto);
        }
        return fait;
    }

    /**
     * Vérification du nom qui ne doit pas être vide, ni faire moins de 3 caractères.
     * @see ComputerForm#createComputer(HttpServletRequest)
     * @param name
     *              le nom de l'ordinateur à vérifier
     * @throws NameException
     *              l'erreur à retourner
     */
    private void checkName(String name) throws NameException {
        if (name == null || name.contains("<") || (name != null && name.trim().length() < 3)) {
            throw new NameException();
        }
    }

    /**
     * Vérification de la date d'introduction qui, si elle existe, doit être une date au bon format.
     * @see ComputerForm#createComputer(HttpServletRequest)
     * @param introduceDate
     *              la date à vérifier
     * @throws IntroduceDateException
     *              l'erreur à retourner
     */
    private void checkIntroduceDate(String introduceDate) throws IntroduceDateException {
        if (StringUtils.isNotEmpty(introduceDate)) {
            try {
                toDate(introduceDate);
            } catch (DateTimeParseException e) {
                throw new IntroduceDateException();
            }
        }
    }

    /**
     * Vérification de la date de départ qui, si elle existe, doit être une date au bon format.
     * De plus, si une date d'introduction a été précisée, la date de départ ne peut lui être antérieure.
     * @see ComputerForm#createComputer(HttpServletRequest)
     * @param discontinuedDate
     *              la date à vérifier
     * @param introduceDate
     *               la date d'introduction à vérifier
     * @throws DiscontinuedDateException
     *              l'erreur à retourner
     */
    private void checkDiscontinuedDate(String discontinuedDate,
         String introduceDate) throws DiscontinuedDateException {
        if (StringUtils.isNotEmpty(discontinuedDate)) {
            try {
                toDate(discontinuedDate);
                toDate(introduceDate);
                if (introduceDate != null && toDate(discontinuedDate).isBefore(toDate(introduceDate))) {
                    throw new DiscontinuedDateException();
                }
            } catch (DateTimeParseException e) {
                throw new DiscontinuedDateException();
            }
        }
    }

    /**
     * Prise en compte de l'erreur lors du remplissage du formulaire.
     * @see ComputerForm#erreurs
     * @param champ
     *              le champ impacté par l'erreur
     * @param message
     *               le message associé à l'erreur
     */
    private void setErreur(String champ, String message) {
        erreurs.put(champ, message);
    }
 
    /**
     * Convertit une string en LocalDate.
     * @see ComputerForm#createComputer(HttpServletRequest)
     * @param string
     *              la chaîne de caractères à parser
     * @return date
     *              la date au format LocalDate
     */
    private static LocalDate toDate(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatter = formatter.withLocale(Locale.FRANCE);
        LocalDate date = LocalDate.parse(string, formatter);
        return date;
    }

}
