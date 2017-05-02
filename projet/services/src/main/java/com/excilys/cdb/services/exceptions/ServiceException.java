package com.excilys.cdb.services.exceptions;

public abstract class ServiceException extends Exception {

    /**
     * SerialVersionId.
     */
    private static final long serialVersionUID = 1L;

    /**
     * @return message
     * Le message de l'erreur.
     */
    public abstract String getMessage();

    /**
     * @return champ
     * Le champ du formulaire touché par l'erreur.
     */
    public abstract String getChamp();

}
