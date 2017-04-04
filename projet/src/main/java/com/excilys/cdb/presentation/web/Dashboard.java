package com.excilys.cdb.presentation.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Computer;
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
        if (request.getParameter("sort") != null) {
            switch (request.getParameter("sort")) {
            case "name":
                request.setAttribute("computerList", ClientActions
                        .listComputers(getDebut(), getNbId(), "computer.name", "'%'", "computer.name"));
                break;
            case "introduce":
                request.setAttribute("computerList", ClientActions
                        .listComputers(getDebut(), getNbId(), "computer.introduced", "'%'", "computer.introduced"));
                break;
            case "discontinued":
                request.setAttribute("computerList", ClientActions
                        .listComputers(getDebut(), getNbId(), "computer.discontinued", "'%'", "computer.discontinued"));
                break;
            case "company":
                request.setAttribute("computerList", ClientActions
                        .listComputers(getDebut(), getNbId(), "computer.company_id", "'%'", "computer.company_id"));
                break;
            default:
                request.setAttribute("computerList", ClientActions
                        .listComputers(getDebut(), getNbId(), "computer.id", "'%'", "computer.id"));
                break;
            }
        } else {
            request.setAttribute("computerList",
                    ClientActions.listComputers(debut, nbId, "computer.id", "'%'", "computer.id"));
        }
        if (request.getParameter("search") != null) {
            List<Computer> list = new ArrayList<Computer>();
            list = ClientActions.listComputers(getDebut(), getNbId(), "computer.name", String.format("'%%" + request.getParameter("search") + "%%'"), "computer.id");
            list.addAll(ClientActions.listComputers(getDebut(), getNbId(), "company.name", String.format("'%%" + request.getParameter("search") + "%%'"), "computer.id"));
            request.setAttribute("computerList", list);
        }
        request.setAttribute("nbComputer", ClientActions.countComputer());
        request.setAttribute("sort", request.getParameter("sort"));
        request.setAttribute("search", request.getParameter("search"));
        if (request.getParameter("page") != null) {
            request.setAttribute("currentPage", request.getParameter("page"));
        } else {
            request.setAttribute("currentPage", 1);
        }
        request.setAttribute("maxPage", ClientActions.maxPages(nbId));
        this.getServletContext().getRequestDispatcher(VUE).forward(request,
                response);
    }

    /**
     * Envoi de notre formulaire.
     * @see AddComputer#FORM
     * @see AddComputer#COMPUTER
     * @see AddComputer#VUE
     * @param request
     *            La requête de notre servlet
     * @param response
     *            la réponse de notre servlet
     * @throws ServletException
     *             Exception
     * @throws IOException
     *             Exception
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String ids = request.getParameter("selection");
        if (ids.contains(",")) {
            String[] strs = ids.split("[,]");
            for (int i = 0; i < strs.length; i++) {
                ClientActions.deleteComputer(Integer.parseInt(strs[i]));
            }
        } else {
            ClientActions.deleteComputer(Integer.parseInt(ids));
        }

        this.getServletContext().getRequestDispatcher(VUE).forward(request,
                response);
    }

}
