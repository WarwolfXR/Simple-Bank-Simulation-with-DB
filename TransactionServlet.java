package com.bank;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/transaction")
public class TransactionServlet extends HttpServlet {

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

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accNo = request.getParameter("accNo");
        String amountStr = request.getParameter("amount");
        String type = request.getParameter("type");
        double amount = 0;

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if (accNo == null || amountStr == null || type == null ||
                accNo.isEmpty() || amountStr.isEmpty() || type.isEmpty()) {
            out.println("All fields are required.");
            return;
        }

        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            out.println("Invalid amount.");
            return;
        }

        if (!AccountServlet.accounts.containsKey(accNo)) {
            out.println("Account does not exist.");
            return;
        }

        double currentBalance = AccountServlet.accounts.get(accNo);

        if (type.equals("withdraw")) {
            if (amount > currentBalance) {
                out.println("Insufficient balance.");
                return;
            }
            AccountServlet.accounts.put(accNo, currentBalance - amount);
            out.println("<h2>Withdrawal Successful</h2>");
        } else if (type.equals("deposit")) {
            AccountServlet.accounts.put(accNo, currentBalance + amount);
            out.println("<h2>Deposit Successful</h2>");
        } else {
            out.println("Invalid transaction type.");
            return;
        }

        out.println("<p>Account No: " + accNo + "</p>");
        out.println("<p>New Balance: " + AccountServlet.accounts.get(accNo) + "</p>");
        out.println("<a href='home'>Back to Home</a>");
    }
}
