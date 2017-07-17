package Servlets;

import Controller.LiveMapUpdates;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "GetCabLocations", urlPatterns = {"/GetCabLocations"})
public class GetCabLocations extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            out.print(new LiveMapUpdates().getCabs(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
