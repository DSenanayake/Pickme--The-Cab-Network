package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "GetPrivileges", urlPatterns = {"/Admin/GetPrivileges"})
public class GetPrivileges extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String mode = request.getParameter("mode");
        if (mode.equalsIgnoreCase("select")) {
            try {
                String html = Model.Admin.getSelectivePrivileges();
                out.print(html);
            } catch (Exception e) {
                out.print("<option value='NOT' disabled>None</option>");
            }
        } else if (mode.equalsIgnoreCase("table")) {
            try {
                int start = Integer.parseInt(request.getParameter("start"));
                String html = Model.Admin.getPrivilegesTable(start);
                out.print(html);
            } catch (Exception e) {
                e.printStackTrace();
                out.print("<div class='alert alert-danger'>Error while Getting Privileges</div>");
            }
        }
    }
}
