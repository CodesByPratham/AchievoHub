package javaServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import utilities.DatabaseUtil;
import utilities.PasswordUtil;

/**
 * @author PRATHAM
 */
public class ResetPassword extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        String newPassword = PasswordUtil.hashPassword(request.getParameter("password"));

        DatabaseUtil dbUtil = new DatabaseUtil();
        boolean success = dbUtil.resetPassword(token, newPassword);

        request.setAttribute("message", success ? "Password reset successfully! You can now log in." : "Invalid or expired reset link.");
    }
}
