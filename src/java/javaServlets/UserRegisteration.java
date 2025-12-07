package javaServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import model.User;
import utilities.PasswordUtil;
import utilities.EmailUtil;

/**
 * @author PRATHAM
 */
public class UserRegisteration extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = new User();

        // Hash the password using bcrypt
        String hashedPassword = PasswordUtil.hashPassword(request.getParameter("password"));

        user.setUsername(request.getParameter("username"));
        user.setFname(request.getParameter("fname"));
        user.setLname(request.getParameter("lname"));
        user.setContact(request.getParameter("contact"));
        user.setEmail(request.getParameter("email"));
        user.setPassword(hashedPassword);

        // Generate a unique verification code
        String verificationCode = UUID.randomUUID().toString();
        user.setVerificationCode(verificationCode);

        // Store user details temporarily in session
        request.getSession().setAttribute("tempUser", user);

        // Send verification email
        String verificationLink = "http://localhost:8080/AchievoHub/VerifyEmail?code=" + verificationCode;
        EmailUtil.sendVerificationEmail(user.getEmail(), verificationLink);

        request.setAttribute("message", "A verification email has been sent. Please verify your email to complete registration.");
    }
}
