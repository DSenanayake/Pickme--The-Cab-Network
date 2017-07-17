package Servlets;

import Model.CabService;
import Model.Location;
import Model.CabOrder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SaveCurrentProcess extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            CabOrder order = new CabOrder();

            HttpSession hs = request.getSession();

            if (hs.getAttribute("CURRENT_CITY") != null) {
                order.setCity(hs.getAttribute("CURRENT_CITY").toString());
            }

            String location = request.getParameter("location");
            String selected = request.getParameter("selected");

            if (location != null) {
                location = location.replace('(', ' ');
                location = location.replace(')', ' ');
                String[] latLng = location.split(",");

                String lat = latLng[0];
                String lng = latLng[1];

                Location l = new Location();
                l.setLattitude(Double.parseDouble(lat));
                l.setLongitude(Double.parseDouble(lng));
                
                order.setLocation(l);

            }
            if (selected != null) {
                List<CabService> cabServices = new ArrayList<>();
                String[] split = selected.split(";");
                for (String string : split) {
                    String[] sp = string.split("-");

                    cabServices.add(new CabService(Integer.parseInt(sp[0]), Integer.parseInt(sp[1])));
                }
                order.setSelectedServices(cabServices);
            }

            hs.setAttribute("CURRENT_ORDER", order);
            
            System.out.println("[ SAVE PROC ] - Location :" + location);
            System.out.println("[ SAVE PROC ] - Selected :" + selected);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[ SAVE PROC ] - Error : " + e);
        }
    }
}
