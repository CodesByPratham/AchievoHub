package javaServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import utilities.DatabaseUtil;
import model.User;

/**
 * @author PRATHAM
 */
public class VerifyEmail extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String verificationCode = request.getParameter("code");

        // Retrieve user from session
        User tempUser = (User) request.getSession().getAttribute("tempUser");

        if (tempUser != null && tempUser.getVerificationCode().equals(verificationCode)) {
            DatabaseUtil dbUtil = new DatabaseUtil();

            // Register the user in the database now
            String result = dbUtil.registerUser(tempUser);

            if ("success".equals(result)) {
                request.getSession().removeAttribute("tempUser"); // Remove temporary user data
                request.setAttribute("message", "Email verified successfully! You can now log in.");
            } else {
                request.setAttribute("message", "Error registering user. Please try again.");
            }
        } else {
            request.setAttribute("message", "Invalid or expired verification link.");
        }

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
