package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.HibernateException;

public class RegisterClient extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String fname = request.getParameter("clientFirstname");
        String lname = request.getParameter("clientLastname");
        String gender = request.getParameter("clientGender");
        String city = request.getParameter("clientCity");
        String mobile = request.getParameter("clientMobile");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        
        try {
            boolean isExist = Model.User.alreadyExist(email);
            if (isExist) {
                out.print("EXIST");
            } else {
                new Model.User().registerClient(fname, lname, Integer.parseInt(gender), city, mobile, email, password);
                out.print("OK");
            }
        } catch (UnsupportedEncodingException | NumberFormatException | MessagingException | HibernateException e) {
            out.print("ERROR");
        }
    }
}
