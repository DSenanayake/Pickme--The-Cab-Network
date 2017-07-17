package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CompleteServiceProfile", urlPatterns = {"/CompleteServiceProfile"})
public class CompleteServiceProfile extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String validate = Model.Service.validateCompleteProfileForm(request);

            if (validate.equalsIgnoreCase("OK")) {
                String completeProfile = Model.Service.completeProfile(request);
                out.print(completeProfile);
            } else {
                out.print(validate);
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.print("ERROR");
        }
    }
}
