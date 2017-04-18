package com.excilys.cdb.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.services.exceptions.DiscontinuedDateException;
import com.excilys.cdb.services.exceptions.IntroduceDateException;
import com.excilys.cdb.services.exceptions.NameException;

/**
 * Cette classe est une classe de service permettant l'ajout d'un ordinateur dans la base de données.
 * @author bertrand
 */
public class ComputerForm {
    /**
     * le champ nom de notre ordinateur, défini dans la JSP.
     */
    public static final String CHAMP_NOM = "computerName";

    /**
     * le champ id de notre ordinateur, défini dans la JSP.
     */
    public static final String CHAMP_ID = "id";

    /**
     * le champ company de notre ordinateur, défini dans la JSP.
     */
    public static final String CHAMP_COMPANY = "companyId";
    /**
     * le champ date d'introduction de notre ordinateur, défini dans la JSP.
     */
    public static final String CHAMP_INTRODUCE_DATE = "introduce";
    /**
     * le champ date de départ de notre ordinateur, défini dans la JSP.
     */
    public static final String CHAMP_DISCONTINUED_DATE = "discontinued";

    /**
     * le résultat de notre requête.
     * @see ComputerForm#getResultat()
     */
    private String resultat;
    /**
     * Il s'agit d'une hash map avec nos erreurs et les champs touchés.
     * @see ComputerForm#getErreurs()
     */
    private Map<String, String> erreurs = new HashMap<String, String>();
    /**
     * logger.
     */
    static final org.slf4j.Logger LOGGER = LoggerFactory
            .getLogger(ClientActions.class);

    /**
     * Récupération du résultat de la requête.
     * @see ComputerForm#resultat
     * @return resultat
     *                  le résultat de notre requête.
     */
    public String getResultat() {
        return resultat;
    }

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
     * @param request
     *              la requête de notre servlet
     * @return computer
     *               l'ordinateur créé en base de données
     */
    public Computer createComputer(HttpServletRequest request) {
        String name = request.getParameter(CHAMP_NOM);
        String company = request.getParameter(CHAMP_COMPANY);
        Company manufacturer = new Company.CompanyBuilder(null)
                .id(Long.parseLong(company)).build();

        LocalDate introduceDate = null;
        LocalDate discontinuedDate = null;

        LOGGER.debug(request.getParameter(CHAMP_INTRODUCE_DATE));
        LOGGER.debug(request.getParameter(CHAMP_DISCONTINUED_DATE));

        if (StringUtils.isNotEmpty(request.getParameter(CHAMP_INTRODUCE_DATE))) {
            introduceDate = toDate(request.getParameter(CHAMP_INTRODUCE_DATE));
        }
        if (StringUtils.isNotEmpty(request.getParameter(CHAMP_DISCONTINUED_DATE))) {
            discontinuedDate = toDate(request.getParameter(CHAMP_DISCONTINUED_DATE));
        }

        Computer computer = new Computer.ComputerBuilder(name)
                .introduceDate(introduceDate).discontinuedDate(discontinuedDate)
                .manufacturer(manufacturer).build();

        try {
            checkName(name);
            checkIntroduceDate(request.getParameter(CHAMP_INTRODUCE_DATE));
            checkDiscontinuedDate(request.getParameter(CHAMP_DISCONTINUED_DATE), introduceDate);
        } catch (NameException | IntroduceDateException | DiscontinuedDateException  e) {
            setErreur(e.getChamp(), e.getMessage());
        }
        if (erreurs.isEmpty()) {
            boolean fait = false;
            fait = ClientActions.createComputer(computer);
            if (fait) {
                resultat = "Succès de l'ajout.";
            } else {
                resultat = "Echec de l'ajout.";
            }
        } else {
            resultat = "Echec de l'ajout.";
        }
        return computer;
    }

    /**
     * Mise à jour de notre ordinateur dans la BDD en vérifiant les champs entrés par l'utilisateur.
     * Si un champ n'est pas valide, l'erreur est renvoyée et l'ordinateur n'est pas créé.
     * @see ComputerForm#checkName(String)
     * @see ComputerForm#checkDiscontinuedDate(String, LocalDate)
     * @see ComputerForm#checkIntroduceDate(String)
     * @see AddComputer.java
     * @see ComputerDAO.java
     * @param request
     *              la requête de notre servlet
     * @return computer
     *               l'ordinateur créé en base de données
     */
    public Computer updateComputer(HttpServletRequest request) {
        String name = request.getParameter(CHAMP_NOM);
        String company = request.getParameter(CHAMP_COMPANY);
        Company manufacturer = new Company.CompanyBuilder(null)
                .id(Long.parseLong(company)).build();

        LocalDate introduceDate = null;
        LocalDate discontinuedDate = null;

        LOGGER.debug(request.getParameter(CHAMP_INTRODUCE_DATE));
        LOGGER.debug(request.getParameter(CHAMP_DISCONTINUED_DATE));

        if (StringUtils.isNotEmpty(request.getParameter(CHAMP_INTRODUCE_DATE))) {
            introduceDate = toDate(request.getParameter(CHAMP_INTRODUCE_DATE));
        }
        if (StringUtils.isNotEmpty(request.getParameter(CHAMP_DISCONTINUED_DATE))) {
            discontinuedDate = toDate(request.getParameter(CHAMP_DISCONTINUED_DATE));
        }

        Computer computer = new Computer.ComputerBuilder(name)
                .id(Long.parseLong(request.getParameter(CHAMP_ID)))
                .introduceDate(introduceDate).discontinuedDate(discontinuedDate)
                .manufacturer(manufacturer).build();

        try {
            checkName(name);
            checkIntroduceDate(request.getParameter(CHAMP_INTRODUCE_DATE));
            checkDiscontinuedDate(request.getParameter(CHAMP_DISCONTINUED_DATE), introduceDate);
        } catch (NameException | IntroduceDateException | DiscontinuedDateException e) {
            setErreur(e.getChamp(), e.getMessage());
        }
        if (erreurs.isEmpty()) {
            boolean fait = false;
            fait = ClientActions.updateComputer(computer);
            if (fait) {
                resultat = "Succès de la mise à jour";
            } else {
                resultat = "Echec de la mise à jour";
            }
        } else {
            resultat = "Echec de la mise à jour";
        }
        return computer;
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
         LocalDate introduceDate) throws DiscontinuedDateException {
        if (StringUtils.isNotEmpty(discontinuedDate)) {
            try {
                toDate(discontinuedDate);
                if (introduceDate != null && toDate(discontinuedDate).isBefore(introduceDate)) {
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
