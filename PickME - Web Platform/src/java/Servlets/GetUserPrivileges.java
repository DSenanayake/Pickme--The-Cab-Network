package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "GetUserPrivileges", urlPatterns = {"/Admin/GetUserPrivileges"})
public class GetUserPrivileges extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String html = Model.Admin.getUserPrivileges();
            out.print(html);
        } catch (Exception e) {
            e.printStackTrace();
            out.print("<div class='alert alert-danger'>Sorry, Error while loading...</div>");
        }
    }
}
