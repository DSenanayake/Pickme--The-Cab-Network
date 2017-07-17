package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;

public class SearchCity extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        java.util.List<Db.City> list = new Model.City().searchCity(request.getParameter("input"));

        JSONArray json = new JSONArray();

        for (Db.City city : list) {
            java.util.Map map = new java.util.HashMap();

            map.put("id", city.getCityId());
            map.put("description", city.getGooglePlaceId());

            json.add(map);
        }
        System.out.println(":END");
        out.print(json.toJSONString());
    }
//<editor-fold defaultstate="collapsed" desc="http methods">

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
//</editor-fold>
}
