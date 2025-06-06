package com.bank;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class HomeServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        // Initialize the SQLite database (creates db directory and file if needed)
        DatabaseUtil.initializeDatabase();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<h1>Welcome to the Bank Simulation Home Page</h1>");
        out.println("<a href='account'>Manage Account</a><br>");
        out.println("<a href='transaction'>Make Transaction</a><br>");
        out.println("<a href='summary'>View Summary</a>");
    }
}
