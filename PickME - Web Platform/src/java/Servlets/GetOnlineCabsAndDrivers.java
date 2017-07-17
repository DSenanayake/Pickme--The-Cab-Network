package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "GetOnlineCabsAndDrivers", urlPatterns = {"/GetOnlineCabsAndDrivers"})
public class GetOnlineCabsAndDrivers extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession();
            Db.LoginDetails details = (Db.LoginDetails) session.getAttribute("CURRENT_USER");
            if (details != null) {
                String html = Model.Service.getAvailableCabsAndDriversHtml(details.getEmail());
                out.print(html);
                System.out.println("[ CABS & DRIVERS ] - HTML OK: " + html);
            } else {
                out.print("NOT_LOGGED");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("[ CABS & DRIVERS ] - Error : " + e);
            out.print("ERROR");
        }
    }
}
