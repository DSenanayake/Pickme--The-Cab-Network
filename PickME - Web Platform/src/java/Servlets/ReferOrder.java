package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ReferOrder", urlPatterns = {"/ReferOrder"})
public class ReferOrder extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            int order = Integer.parseInt(request.getParameter("order"));
            int driver = Integer.parseInt(request.getParameter("driver"));
            String cab = request.getParameter("cab");

            String status = Model.ServiceOrder.referOrder(order,driver,cab);

            out.print(status);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("[ REFR ORDR ] - Error : " + e);
            out.print("ERROR");
        }
    }

}
