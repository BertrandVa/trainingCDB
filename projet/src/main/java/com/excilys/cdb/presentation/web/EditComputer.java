package com.excilys.cdb.presentation.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.services.ComputerForm;
import com.excilys.cdb.services.ClientActions;

public class EditComputer extends HttpServlet {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = 1L;
    /**
     * L'adresse de notre jsp.
     * @see EditComputer.jsp
     * @see EditComputer#doGet(HttpServletRequest, HttpServletResponse)
     * @see EditComputer#doPost(HttpServletRequest, HttpServletResponse)
     */
    public static final String VUE = "/WEB-INF/views/editComputer.jsp";
    
    /**
     * valeur de notre champ formulaire.
     * @see AddComputer#doPost(HttpServletRequest, HttpServletResponse)
     */
    public static final String FORM = "form";
    /**
     * Valeur de notre champ ordinateur.
     * @see AddComputer#doPost(HttpServletRequest, HttpServletResponse)
     */
    public static final String COMPUTER = "computer";
    
    /**
     * Récupération de la jsp pour l'affichage.
     * @see EditComputer#VUE
     * @param request
     *              La requête de notre servlet
     * @param response
     *              la réponse de notre servlet
     * @throws ServletException
     *                         Exception
     * @throws  IOException
     *                      Eception
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("id") != null) {
            request.setAttribute("companyList",  ClientActions.listCompanies(1, ClientActions.countCompanies()));
            request.setAttribute("computer", ClientActions.showComputerDetails(Long.parseLong(request.getParameter("id"))));
        }
      this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ComputerForm form = new ComputerForm();
        Computer computer = form.updateComputer(request);
        request.setAttribute(FORM, form);
        request.setAttribute(COMPUTER, computer);
        this.getServletContext().getRequestDispatcher(VUE).forward(request,
                response);
    }

}
