package com.excilys.cdb.services.exceptions;

public class NameException extends ServiceException {

    /**
     * Default serial version ID.
     */
    private static final long serialVersionUID = 1L;
    
    public String getMessage(){
        return("Le nom d'utilisateur doit contenir au moins 3 caractères et doit être valide.");        
    }
    
    public String getChamp(){
        return("CHAMP_NOM");        
    }

}
