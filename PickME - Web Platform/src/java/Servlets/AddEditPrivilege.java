package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AddEditPrivilege", urlPatterns = {"/Admin/AddEditPrivilege"})
public class AddEditPrivilege extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            String id = request.getParameter("id");
            String uri = request.getParameter("uri");
            String name = request.getParameter("pname");

            if (id.equalsIgnoreCase("NEW")) {
                Model.Admin.addNewPrivilege(uri, name);
            } else {
                Model.Admin.updatePrivilege(id, uri, name);
            }

            out.print("OK");
        } catch (Exception e) {
            e.printStackTrace();
            out.print("ERROR");
        }
    }
}
