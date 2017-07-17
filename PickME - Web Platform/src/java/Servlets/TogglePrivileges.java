package Servlets;

import com.sun.xml.ws.message.Util;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "TogglePrivileges", urlPatterns = {"/Admin/TogglePrivileges"})
public class TogglePrivileges extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            boolean enable = Util.parseBool(request.getParameter("enable"));
            
            getServletContext().setAttribute("PRIVILEGES_FILTER", enable);
            
            out.print("OK");
        } catch (Exception e) {
            out.print("ERROR");
        }
    }
}
