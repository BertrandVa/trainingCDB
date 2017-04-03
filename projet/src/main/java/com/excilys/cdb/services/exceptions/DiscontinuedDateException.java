package com.excilys.cdb.services.exceptions;

public class DiscontinuedDateException extends ServiceException {

    /**
     * SerialVersionID.
     */
    private static final long serialVersionUID = 1L;

    public String getMessage() {
        return ("La date de départ doit être valide.");
    }

    public String getChamp() {
        return ("CHAMP_DISCONTINUED_DATE");
    }
}
