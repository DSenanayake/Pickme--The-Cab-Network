package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "GetMembershipRequest", urlPatterns = {"/Admin/GetMembershipRequest"})
public class GetMembershipRequest extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String html = Model.MembershipPlan.getNewMemberhsipRequests();
            out.print(html);
        } catch (Exception e) {
            out.print("<div class='alert alert-danger' style='text-align:center;'><h3>Sorry,Error while getting Memebership Request.</h3></div>");
        }
    }
}
