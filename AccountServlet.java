package com.bank;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@WebServlet("/account")
public class AccountServlet extends HttpServlet {

    // Static maps to simulate DB storage
    public static HashMap<String, Double> accounts = new HashMap<>();
    public static HashMap<String, String> names = new HashMap<>();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Show simple form to create account
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<h1>Create Account</h1>");
        out.println("<form method='post' action='account'>");
        out.println("Account No: <input type='text' name='accNo'><br>");
        out.println("Name: <input type='text' name='name'><br>");
        out.println("Initial Balance: <input type='text' name='balance'><br>");
        out.println("<input type='submit' value='Create'>");
        out.println("</form>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accNo = request.getParameter("accNo");
        String name = request.getParameter("name");
        String balanceStr = request.getParameter("balance");
        double balance = 0;

        if (accNo == null || name == null || balanceStr == null ||
                accNo.isEmpty() || name.isEmpty() || balanceStr.isEmpty()) {
            response.getWriter().println("All fields are required.");
            return;
        }

        try {
            balance = Double.parseDouble(balanceStr);
        } catch (NumberFormatException e) {
            response.getWriter().println("Invalid balance amount.");
            return;
        }

        if (accounts.containsKey(accNo)) {
            response.getWriter().println("Account already exists.");
            return;
        }

        accounts.put(accNo, balance);
        names.put(accNo, name);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h1>Account Created Successfully</h1>");
        out.println("<p>Account No: " + accNo + "</p>");
        out.println("<p>Name: " + name + "</p>");
        out.println("<p>Balance: " + balance + "</p>");
        out.println("<a href='home'>Back to Home</a>");
    }
}
