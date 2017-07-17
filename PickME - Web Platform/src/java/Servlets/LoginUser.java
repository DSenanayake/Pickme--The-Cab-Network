package Servlets;

import Model.Task;
import Model.UserStatus;
import Model.UserType;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginUser extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String email = request.getParameter("uname");
            String pword = request.getParameter("pword");
            String stay = request.getParameter("stay");
            System.out.println("[ LOGIN ] - Stay Login " + stay);
            Db.LoginDetails loginDetails = Model.User.loginUser(email, pword);
            if (loginDetails != null) {
                int status = loginDetails.getLoginStatus().getLoginStatusId();
                if (status != UserStatus.NOT_CONFIRMED & status != UserStatus.DELETED) {
                    if (request.getSession().getAttribute("CURRENT_USER") != null) {
                        request.getSession().invalidate();
                    }
                    request.getSession().setAttribute("CURRENT_USER", loginDetails);

                    String fname = Model.User.getFirstNameByUser(loginDetails);

                    request.getSession().setAttribute("fname", fname);
                    System.out.println("[ FNAME ] - " + fname);

                    if (loginDetails.getUserType().getUserTypeId() == UserType.ADMINISTRATOR) {
                        request.getSession().setMaxInactiveInterval(300);
                        System.out.println("[ ADMIN LOGGED ] - " + new Date());
                        request.getSession().setAttribute("CURRENT_TASK", new Task("Dashboard"));
                    } else if (loginDetails.getUserType().getUserTypeId() == UserType.SERVICE_PROVIDER) {
                        request.getSession().setMaxInactiveInterval(120);
                    }

                    if (stay != null & Boolean.parseBoolean(stay)) {

                        if (loginDetails.getUserType().getUserTypeId() != Model.UserType.ADMINISTRATOR) {
                            String loginKey = Controller.Services.encrypt(String.valueOf(loginDetails.getLoginDetailsId()));
                            String loginValue = Controller.Services.encrypt(loginDetails.getPassword());

                            Cookie l_key = new Cookie("l_key", loginKey);
                            Cookie l_value = new Cookie("l_value", loginValue);

                            l_key.setMaxAge(2419200);
                            l_value.setMaxAge(2419200);

                            response.addCookie(l_key);
                            response.addCookie(l_value);

                            System.out.println("[ LOGIN ] - Setting cookie : " + l_key.getValue());
                            System.out.println("[ LOGIN ] - Setting cookie : " + l_value.getValue());
                        }

                    }
                }
                if (status == UserStatus.NOT_CONFIRMED) {
                    out.print("NOT_CONFIRMED");
                } else if (status == UserStatus.DELETED) {
                    out.print("DELETED");
                } else {
                    out.print("OK");
                }
            } else {
                out.print("NOT_EXIST");
            }
        }
    }

}
