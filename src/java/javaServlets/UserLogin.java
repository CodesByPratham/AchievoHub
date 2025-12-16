package javaServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import utilities.DatabaseUtil;

/**
 * @author PRATHAM
 */
public class UserLogin extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DatabaseUtil dbUtil = new DatabaseUtil();
        String[] result = dbUtil.loginUser(request.getParameter("identifier"), request.getParameter("password"));

        if (result[0].equals("success")) {
            request.getSession().setAttribute("username", result[1]);
            request.getSession().setAttribute("id", result[2]);
        }

        request.setAttribute("message", result[0]); // Pass correct message to the frontend
    }
}
