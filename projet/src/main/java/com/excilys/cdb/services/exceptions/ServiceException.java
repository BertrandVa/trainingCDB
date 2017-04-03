package com.excilys.cdb.services.exceptions;

public abstract class ServiceException extends Exception {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public abstract String getMessage();
    public abstract String getChamp();

}
