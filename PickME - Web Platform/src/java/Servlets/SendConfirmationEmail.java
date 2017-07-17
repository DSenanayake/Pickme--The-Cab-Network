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
public class SendConfirmationEmail extends HttpServlet {

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            try {
                String email = request.getParameter("email");

                Db.User user = Model.User.getClientByEmail(email);
                if (user != null) {
                    Controller.Services.sendConfirmationEmail(user.getLoginDetails().getLoginDetailsId() + "", user.getUserDetails().getFirstname(), email);
                    out.print("DONE");
                } else {
                    out.print("NOT_EXIST");
                }
            } catch (Exception e) {
                out.print("ERROR");
            }
        }
    }

}
