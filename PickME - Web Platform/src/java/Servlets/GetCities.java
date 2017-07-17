package Servlets;

import Db.City;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;

public class GetCities extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        List<Db.City> cities = new Model.City().getAllCities();

        JSONArray ja = new JSONArray();
        for (City c : cities) {
            Map m = new HashMap();

            m.put("id", c.getCityId());
            m.put("city", c.getGooglePlaceId());

            ja.add(m);
        }

        out.print(ja.toJSONString());
    }
}
