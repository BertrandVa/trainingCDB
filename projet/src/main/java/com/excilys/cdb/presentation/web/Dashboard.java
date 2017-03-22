package com.excilys.cdb.presentation.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.excilys.cdb.services.ClientActions;

public class Dashboard extends HttpServlet {

    /**
     * Notre UID de version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * L'adresse de notre jsp.
     * @see Dashboard.jsp
     * @see Dashboard#doGet(HttpServletRequest, HttpServletResponse)
     */
    public static final String VUE = "/WEB-INF/views/dashboard.jsp";
    /**
     * Le premier id d'ordinateur à afficher.
     * @see Dashboard.jsp
     * @see Dashboard#doGet(HttpServletRequest, HttpServletResponse)
     */
    private long debut = 1;
    /**
     * Le nombre d'ordinateurs à afficher.
     * @see Dashboard.jsp
     * @see Dashboard#doGet(HttpServletRequest, HttpServletResponse)
     */
    private int nbId = 10;

    /**
     * logger.
     */
    static final Logger LOGGER = LoggerFactory.getLogger(ClientActions.class);

    public long getDebut() {
        return debut;
    }

    public void setDebut(long debut) {
        this.debut = debut;
    }

    public int getNbId() {
        return nbId;
    }

    public void setNbId(int nbId) {
        this.nbId = nbId;
    }

    /**
     * Récupération de la jsp pour l'affichage.
     * @see Dashboard#VUE
     * @param request
     *            La requête de notre servlet
     * @param response
     *            la réponse de notre servlet
     * @throws ServletException
     *             Exception
     * @throws IOException
     *             Eception
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.debug(request.getParameter("submit"));
        if (request.getParameter("submit") != null) {
            setNbId(Integer.parseInt(request.getParameter("submit")));
        }
        if (request.getParameter("page") != null) {
            Long nbPage = (Long.parseLong(request.getParameter("page")));
            setDebut((nbPage - 1) * nbId + 1);
        }
        request.setAttribute("computerList",
                ClientActions.listComputers(debut, nbId));
        request.setAttribute("nbComputer", ClientActions.countComputer());
        if (request.getParameter("page") != null) {
            request.setAttribute("currentPage", request.getParameter("page"));
        } else {
            request.setAttribute("currentPage", 1);
        }
        request.setAttribute("maxPage", ClientActions.maxPages(nbId));
        this.getServletContext().getRequestDispatcher(VUE).forward(request,
                response);
    }
}
