package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ToggleAllow", urlPatterns = {"/Admin/ToggleAllow"})
public class ToggleAllow extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            int privilege = Integer.parseInt(request.getParameter("privilege"));
            Model.Admin.toggleAllow(privilege);
            out.print("OK");
        } catch (Exception e) {
            out.print("ERROR");
        }
    }
}
