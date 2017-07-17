package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "GetVehicleModels", urlPatterns = {"/GetVehicleModels"})
public class GetVehicleModels extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            int make = Integer.parseInt(request.getParameter("make"));
            String html =new  Model.Vehicle().getVehicleModels(make);
            out.print(html);
        } catch (Exception e) {
            e.printStackTrace();
            out.print("<option value='-1'>Any</option>");
        }
    }
}
