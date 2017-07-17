package Servlets;

import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResetPassword extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String email = request.getParameter("email");
        try {
            int result = Model.User.sendResetPassword(email);
            if (result == User.SUCCESS) {
                out.print("SENT");
            } else if (result == User.USER_NOT_EXIST) {
                out.print("NOT_EXIST");
            } else if (result == User.ERROR) {
                out.print("ERROR");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.print("ERROR");
        }
    }

}
