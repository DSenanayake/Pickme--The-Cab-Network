package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DeleteUserPrivilege", urlPatterns = {"/Admin/DeleteUserPrivilege"})
public class DeleteUserPrivilege extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            Model.Admin.deleteUserPrivileges(request.getParameter("delete"));
            out.print("OK");
        } catch (org.hibernate.exception.ConstraintViolationException e) {
            System.out.println("[ DEL USR PREV ] - Using");
            out.print("USING");
        } catch (Exception e) {
            out.print("ERROR");
        }
    }
}
