package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Deeptha
 */
public class CheckExistUser extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String email = request.getParameter("email");
            boolean exist = Model.User.alreadyExist(email);
            System.out.println(exist);
            if (exist) {
                out.print("YES");
            } else {
                out.print("NO");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.print("ERROR");
        }
    }
}
