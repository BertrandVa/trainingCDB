package com.excilys.cdb.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Dashboard extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public static final String VUE = "/WEB-INF/views/dashboard.jsp";
  
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    }
}
