package javaServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import utilities.DatabaseUtil;
import utilities.PasswordUtil;

/**
 * @author PRATHAM
 */
public class ChangePassword extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        int userId = Integer.parseInt(session.getAttribute("id").toString());

        String newPassword = PasswordUtil.hashPassword(request.getParameter("newPassword"));

        DatabaseUtil dbUtil = new DatabaseUtil();
        String result = dbUtil.updateUserPassword(userId, newPassword);

        request.setAttribute("message", result);
    }
}
