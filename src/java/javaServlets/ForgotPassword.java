package javaServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import utilities.DatabaseUtil;
import utilities.EmailUtil;

/**
 * @author PRATHAM
 */
public class ForgotPassword extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String identifier = request.getParameter("identifier");
        DatabaseUtil dbUtil = new DatabaseUtil();

        // Check if user exists
        String email = dbUtil.getEmailByIdentifier(identifier);
        if (email == null) {
            request.setAttribute("message", "No user found with that email or username.");
        } else {
            // Generate a unique reset token
            String resetToken = UUID.randomUUID().toString();
            dbUtil.storeResetToken(email, resetToken);

            // Send email with reset link
            String resetLink = "http://localhost:8081/AchievoHub/resetPassword.jsp?token=" + resetToken;
            EmailUtil.sendResetPasswordEmail(email, resetLink);

            request.setAttribute("message", "A password reset link has been sent to your email.");
        }
    }
}
