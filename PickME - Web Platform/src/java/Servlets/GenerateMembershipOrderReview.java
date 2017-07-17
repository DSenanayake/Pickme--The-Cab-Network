package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GenerateMembershipOrderReview extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        try {
            int plan = Integer.parseInt(request.getParameter("plan"));
            int dura = Integer.parseInt(request.getParameter("duration"));
            System.out.println("[GENER] - Getting parameters");

            Db.MembershipPlan membershipPlan = Model.MembershipPlan.getPlanById(plan);
            Db.MembershipDuration duration = Model.MembershipPlan.getDurationById(dura);
            System.out.println("[GENER] - Getting Plans");

            System.out.println("[GENER] - Attempting to Convert");
            double usd = Controller.CurrencyConverter.convertLKRtoUSD(duration.getDurationFee());
            System.out.println("[GENER] - Currency Converted");

            System.out.println("[GENER] - Setting Payment Amount - USD : "+usd);
            request.getSession().setAttribute("Payment_Amount", String.valueOf(usd));

            System.out.println(request.getSession().getAttribute("Payment_Amount"));
            
            System.out.println("[GENER] - Generating HTML");
            out.print("        <table class=\"table table-striped\">"
                    + "            <thead>"
                    + "            <th>Membership Plan</th>"
                    + "            <th>Duration  <small>(Months)</small></th>"
                    + "            <th>Amount <small>(Rs)</small></th>"
                    + "            </thead>"
                    + "            <tr class=\"warning\">"
                    + "                <td>" + membershipPlan.getMembershipPlanName() + "</td>"
                    + "                <td>" + duration.getDurationMonths().getMonths() + "</td>"
                    + "                <td>" + duration.getDurationFee() + "</td>"
                    + "            </tr>"
                    + "            <tr>"
                    + "                <td colspan=\"3\">"
                    + "                    <h3 class=\"pull-right text text-danger\">Total Amount : <b><small>LKR</small>" + duration.getDurationFee() + "</b></h3>"
                    + "                </td>"
                    + "            </tr>"
                    + "            <tr>"
                    + "            <td colspan=\"3\">"
                    + "                    <h4 class=\"pull-right text text-info\">Total Amount : <b><small>USD</small>" + usd + "</b></h4>"
                    + "            </td>"
                    + "            </tr>"
                    + "        </table>");
            System.out.println("[GENER] - Done !");
        } catch (Exception e) {
            e.printStackTrace();
            out.print("NONE");
        }
    }
}
