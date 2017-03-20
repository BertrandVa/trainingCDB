package com.excilys.cdb.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebApplication extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public WebApplication() {
        super();
        }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("");
        out.println("Bonjour tout le monde");
        out.println("");
    }
}
