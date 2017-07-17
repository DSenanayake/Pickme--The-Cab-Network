package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "FilterServices", urlPatterns = {"/FilterServices"})
public class FilterServices extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String sort = request.getParameter("sort");
            String avail = request.getParameter("availability");
            String keywords = request.getParameter("keywords");
            String city = request.getParameter("city");
            String area_max = request.getParameter("area_max");
            String cost_min = request.getParameter("cost_min");
            String cost_max = request.getParameter("cost_max");
            String dist_min = request.getParameter("dist_min");
            String dist_max = request.getParameter("dist_max");
            String cabs_min = request.getParameter("cabs_min");
            String cabs_max = request.getParameter("cabs_max");
            String make = request.getParameter("make");
            String model = request.getParameter("model");
            int start = Integer.parseInt(request.getParameter("start") != null ? request.getParameter("start") : "0");
            
            String html = start + "/" + sort + "/" + avail + "/" + keywords + "/" + city + "/" + area_max + "/" + cost_min + "/" + cost_max + "/" + dist_min + "/" + dist_max + "/" + cabs_min + "/" + cabs_max + "/" + make + "/" + model + "/";
            System.out.println(html);
            
            String json = Model.Service.filterServices(start, sort, avail, keywords, city, area_max, cost_min, cost_max, dist_min, dist_max, cabs_min, cabs_max, make, model);
            
            out.print(json);
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
            out.print("[{"
                    + "\"result\":\"<div class='alert alert-warning'><span class='glyphicon glyphicon-warning'></span> Check your internet Connection. !</div>\","
                    + "\"count\":\"0\","
                    + "\"pagination\":\"No Result.\""
                    + "}]");
        } catch (Exception e) {
            e.printStackTrace();
            out.print("[{"
                    + "\"result\":\"<div class='alert alert-danger'><span class='glyphicon glyphicon-exclamation'></span> Error While Getting Services. !</div>\","
                    + "\"count\":\"0\","
                    + "\"pagination\":\"No Result.\""
                    + "}]");
        }finally{
            System.gc();
        }
    }

}
