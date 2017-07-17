package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;

public class GetDurationDetails extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            int dura = Integer.parseInt(request.getParameter("duration"));
            Db.MembershipDuration duration = Model.MembershipPlan.getDurationById(dura);

            JSONArray array = new JSONArray();

            Map m = new HashMap();

            m.put("id", duration.getMembershipDurationId());
            m.put("months", duration.getDurationMonths().getMonths());
            m.put("price", duration.getDurationFee());

            array.add(m);
            if (!array.isEmpty()) {
                out.print(array.toJSONString());
            } else {
                out.print("ZERO");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.print("ERROR");
        }
    }
}
