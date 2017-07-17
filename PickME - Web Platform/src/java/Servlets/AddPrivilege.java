package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AddPrivilege", urlPatterns = {"/Admin/AddPrivilege"})
public class AddPrivilege extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String uri = request.getParameter("privilegeUri");
            int usertype = Integer.parseInt(request.getParameter("userType"));
            boolean allow = request.getParameter("allow") != null;
            String allowed = request.getParameter("allowed");
            String disallowed = request.getParameter("disallowed");

            Model.Admin.addPrivilege(uri, usertype, allow, allowed, disallowed);

            out.print("OK");
        } catch (Exception e) {
            e.printStackTrace();
            out.print("ERROR");
        }
    }
}
