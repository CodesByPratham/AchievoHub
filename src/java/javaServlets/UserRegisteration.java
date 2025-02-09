package javaServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import utilities.DatabaseUtil;
import operations.User;
import utilities.PasswordUtil;

/**
 * @author PRATHAM
 */
public class UserRegisteration extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = new User();

        // Creates the hash(using bcrypt) for the password provided by the user.
        String hashedPassword = PasswordUtil.hashPassword(request.getParameter("password"));

        user.setUsername(request.getParameter("username"));
        user.setFname(request.getParameter("fname"));
        user.setLname(request.getParameter("lname"));
        user.setContact(request.getParameter("contact"));
        user.setEmail(request.getParameter("email"));
        user.setPassword(hashedPassword);

        DatabaseUtil dbUtil = new DatabaseUtil();
        String result = dbUtil.registerUser(user);

        request.setAttribute("message", result);
    }
}
