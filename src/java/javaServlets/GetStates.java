package javaServlets;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import utilities.DatabaseUtil;
import model.State;

/**
 * @author PRATHAM
 */
public class GetStates extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DatabaseUtil dbUtil = new DatabaseUtil();
        List<State> states = dbUtil.getAllStates();
        
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(states));
    }
}
