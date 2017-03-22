package com.excilys.cdb.presentation.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
      this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }

}
