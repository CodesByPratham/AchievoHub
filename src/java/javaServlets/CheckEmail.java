package javaServlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import utilities.DatabaseUtil;

/**
 * @author PRATHAM
 */
public class CheckEmail extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        DatabaseUtil dbUtil = new DatabaseUtil();

        try (PrintWriter out = response.getWriter()) {
            if (dbUtil.doesEmailExists(request.getParameter("email"))) {
                out.print("exists");
            } else {
                out.print("available");
            }
        } catch (Exception e) {
            System.out.println("Exception in CheckEmail servlet: " + e.toString());
        }
    }
}
