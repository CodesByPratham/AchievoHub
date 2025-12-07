package javaServlets;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.City;
import utilities.DatabaseUtil;

/**
 * @author PRATHAM
 */
public class GetCities extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DatabaseUtil dbUtil = new DatabaseUtil();
        List<City> cities = dbUtil.getCitiesByStateId(Integer.parseInt(request.getParameter("state_id")));

        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(cities));
    }
}
