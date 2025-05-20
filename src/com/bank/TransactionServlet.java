package com.bank;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class TransactionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Show form for transaction (deposit/withdraw)
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<h1>Make a Transaction</h1>");
        out.println("<form method='post' action='transaction'>");
        out.println("Account No: <input type='text' name='accNo'><br>");
        out.println("Amount: <input type='text' name='amount'><br>");
        out.println("Type: <select name='type'>");
        out.println("<option value='deposit'>Deposit</option>");
        out.println("<option value='withdraw'>Withdraw</option>");
        out.println("</select><br>");
        out.println("<input type='submit' value='Submit'>");
        out.println("</form>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accNo = request.getParameter("accNo");
        String amountStr = request.getParameter("amount");
        String type = request.getParameter("type");
        double amount;

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if (accNo == null || amountStr == null || type == null ||
                accNo.isEmpty() || amountStr.isEmpty() || type.isEmpty()) {
            out.println("<p style='color:red;'>All fields are required.</p>");
            return;
        }

        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            out.println("<p style='color:red;'>Invalid amount.</p>");
            return;
        }

        if (!AccountServlet.accounts.containsKey(accNo)) {
            out.println("<p style='color:red;'>Account does not exist.</p>");
            return;
        }

        double currentBalance = AccountServlet.accounts.get(accNo);
        boolean success = false;

        if ("withdraw".equals(type)) {
            if (amount > currentBalance) {
                out.println("<p style='color:red;'>Insufficient balance.</p>");
                return;
            }
            AccountServlet.accounts.put(accNo, currentBalance - amount);
            success = true;
            out.println("<h2>Withdrawal Successful</h2>");
        } else if ("deposit".equals(type)) {
            AccountServlet.accounts.put(accNo, currentBalance + amount);
            success = true;
            out.println("<h2>Deposit Successful</h2>");
        } else {
            out.println("<p style='color:red;'>Invalid transaction type.</p>");
            return;
        }

        if (success) {
            try {
                DatabaseUtil.insertTransaction(accNo, type, amount);
            } catch (SQLException e) {
                e.printStackTrace();
                out.println("<p style='color:red;'>Error saving transaction to database.</p>");
            }
        }

        out.println("<p>Account No: " + accNo + "</p>");
        out.println("<p>New Balance: " + AccountServlet.accounts.get(accNo) + "</p>");
        out.println("<a href='home'>Back to Home</a>");
    }
}
