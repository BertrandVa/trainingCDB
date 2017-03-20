package com.excilys.cdb.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddComputer extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public static final String VUE = "/WEB-INF/views/addComputer.jsp";
    public static final String CHAMP_NOM = "computerName";
    public static final String CHAMP_COMPANY = "companyId";
    public static final String CHAMP_INTRODUCE_DATE = "introduced";
    public static final String CHAMP_DISCONTINUED_DATE = "discontinued";
    public static final String ATT_ERREURS  = "erreurs";
    public static final String ATT_RESULTAT = "resultat";


    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher(VUE).forward(request,
                response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> erreurs = new HashMap<String, String>();
        String result;

        String name = request.getParameter(CHAMP_NOM);
        String company = request.getParameter(CHAMP_COMPANY);
        String introduceDate = request.getParameter(CHAMP_INTRODUCE_DATE);
        String discontinuedDate = request.getParameter(CHAMP_DISCONTINUED_DATE);

        try {
            checkName(name);

        } catch (Exception e) {
            erreurs.put(CHAMP_NOM, e.getMessage());
        }
        try {
            checkIntroduceDate(introduceDate);
        } catch (Exception e) {
            erreurs.put(CHAMP_INTRODUCE_DATE, e.getMessage());
        }
        try {
            checkDiscontinuedDate(discontinuedDate);
        } catch (Exception e) {
            erreurs.put(discontinuedDate, e.getMessage());
        }
        
        if ( erreurs.isEmpty() ) {
            result = "Succès de l'ajout.";
        } else {
            result = "Echec de l'ajout.";
        }        
        request.setAttribute( ATT_ERREURS, erreurs );
        request.setAttribute( ATT_RESULTAT, result );
        
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
        
    }

    private void checkName(String name) throws Exception {
        if (name==null || (name != null && name.trim().length() < 3)) {
            throw new Exception(
                    "Le nom d'utilisateur doit contenir au moins 3 caractères.");
        }
    }

    private void checkIntroduceDate(String introduceDate) {
    }

    private void checkDiscontinuedDate(String discontinuedDate) {
    }
}
