package com.excilys.cdb.services.exceptions;

public class IntroduceDateException extends ServiceException {
    /**
     * Serial Version Id.
     */
    private static final long serialVersionUID = 1L;

    public String getMessage() {
        return ("La date d'introduction doit être valide.");
    }

    public String getChamp() {
     return ("CHAMP_INTRODUCE_DATE");
    }
}
