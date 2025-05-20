package com.bank;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

//@WebServlet("/summary")
public class SummaryServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<h1>Bank Account Summary</h1>");

        Set<String> accountNumbers = AccountServlet.accounts.keySet();

        if (accountNumbers.isEmpty()) {
            out.println("<p>No accounts found.</p>");
        } else {
            out.println("<table border='1'>");
            out.println("<tr><th>Account No</th><th>Name</th><th>Balance</th></tr>");
            for (String accNo : accountNumbers) {
                String name = AccountServlet.names.get(accNo);
                double balance = AccountServlet.accounts.get(accNo);
                out.println("<tr><td>" + accNo + "</td><td>" + name + "</td><td>" + balance + "</td></tr>");
            }
            out.println("</table>");
        }

        out.println("<br><a href='home'>Back to Home</a>");
    }
}
