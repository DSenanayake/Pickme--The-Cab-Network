package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SubscribeService extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String tel = request.getParameter("tel");
        int plan = Integer.parseInt(request.getParameter("plan_id"));
        int duration = Integer.parseInt(request.getParameter("dura_id"));

        try {
            int result = new Model.Service().SubscribeService(name, email, tel, plan, duration);
            if (result != Model.Service.NOT_SUBSCRIBED) {
                int C_PLAN = Model.MembershipPlan.getCurrentMembershipPlan(result);
                
                request.getSession().setAttribute("CURRENT_PLAN", C_PLAN);
                out.println("OK");
            } else {
                out.println("ERROR");
            }
        } catch (Exception e) {
            out.println("ERROR");
            e.printStackTrace();
        }
    }

}
