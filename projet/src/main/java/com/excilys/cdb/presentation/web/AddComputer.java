package com.excilys.cdb.presentation.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

import com.excilys.cdb.controller.ComputerForm;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.services.ClientActions;

/**
 * Cette classe est notre servlet pour l'ajout d'un ordinateur.
 * @author bertrand
 */
@Controller
public class AddComputer extends HttpServlet {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * L'adresse de notre jsp.
     * @see AddComputer.jsp
     * @see AddComputer#doGet(HttpServletRequest, HttpServletResponse)
     * @see AddComputer#doPost(HttpServletRequest, HttpServletResponse)
     */
    public static final String VUE = "/WEB-INF/views/addComputer.jsp";
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
     * @see AddComputer#VUE
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
        request.setAttribute("companyList",  ClientActions.listCompanies(1, ClientActions.countCompanies()));
        this.getServletContext().getRequestDispatcher(VUE).forward(request,
                response);
    }

    /**
     * Envoi de notre formulaire.
     * @see AddComputer#FORM
     * @see AddComputer#COMPUTER
     * @see AddComputer#VUE
     * @param request
     *              La requête de notre servlet
     * @param response
     *              la réponse de notre servlet
     * @throws ServletException
     *                          Exception
     * @throws  IOException
     *                          Exception
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ComputerForm form = new ComputerForm();
        Computer computer = form.createComputer(request);
        request.setAttribute(FORM, form);
        request.setAttribute(COMPUTER, computer);
        this.getServletContext().getRequestDispatcher(VUE).forward(request,
                response);
    }
}
