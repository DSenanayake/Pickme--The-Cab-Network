package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;

@WebServlet(name = "getMembershipDurations", urlPatterns = {"/getMembershipDurations"})
public class getMembershipDurations extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        int plan_id = Integer.parseInt(request.getParameter("plan"));

        java.util.List<Db.MembershipDuration> durations = new Model.MembershipPlan().getDurationsById(plan_id);
        JSONArray json = new JSONArray();
        for (Db.MembershipDuration d : durations) {
            java.util.Map m = new HashMap();

            m.put("id", d.getMembershipDurationId());
            m.put("desc", d.getDurationMonths().getMonths() + " Month(s) - Rs : " + d.getDurationFee());

            json.add(m);
        }
        out.print(json.toJSONString());
    }

}
