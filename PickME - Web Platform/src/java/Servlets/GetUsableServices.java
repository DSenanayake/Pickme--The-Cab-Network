package Servlets;

import Model.CabOrder;
import Model.CabService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;

/**
 *
 * @author Deeptha
 */
public class GetUsableServices extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        JSONArray array = new JSONArray();
        String status, desc;
        try {
            HttpSession hs = request.getSession();
            CabOrder cabOrder = (CabOrder) hs.getAttribute("CURRENT_ORDER");
            if (cabOrder != null) {
                List<CabService> selectedServices = cabOrder.getSelectedServices();
                if (!selectedServices.isEmpty()) {
                    String html = "";
                    int count = 0;
                    for (CabService cabService : selectedServices) {
                        Db.ServiceProvider provider = Model.Service.getServiceById(cabService.getId());
                        double minimum = provider.getServiceDetails().getMinimumDistance();

                        boolean usable = (cabOrder.getDistance() >= minimum);
                        boolean online = (provider.getLoginDetails().getLoginStatus().getLoginStatusId() == Model.UserStatus.ONLINE);

                        int id = provider.getServiceProviderId();
                        html += "<label for=\"service" + id + "\" class=\"list-group-item " + (usable & online ? "" : "disabled unusable") + "\">"
                                + "<input " + (usable & online ? "" : "disabled") + " value=\"" + id + "\" " + (count >= 0 & usable ? "" : "") + " type=\"radio\" name=\"services\" class=\"css-checkbox\" id=\"service" + id + "\"/><label style='font-size:18px;' class=\"css-label\" for=\"service" + id + "\">" + provider.getServiceProviderName() + "</label>"
                                + "<p class=\"list-group-item-text\">Cost per KM : <b>" + provider.getServiceDetails().getCostPerKm() + "</b></p>"
                                + "<p class=\"list-group-item-text\">Status : <b class=\"text text-" + (online ? "success" : "danger") + "\">" + provider.getLoginDetails().getLoginStatus().getLoginStatus() + "</b></p>"
                                + "<p class=\"list-group-item-text\">Minimum Order Distance : <b class=\"text text-" + (usable ? "success" : "danger") + "\">" + minimum + " Km</b></p>"
                                + (usable ? "" : "<div style='border-top:1px solid gray;margin-top:5px;font-size:12px;'><i style='font-weight:normal;' class='text text-danger'><b>Note:</b> You can't select this Cab Service, Beacuse of the lack of order distance.</i></div>")
                                + "</label>";
                        count = usable & online ? count + 1 : count;
                    }
                    desc = html;
                    if (count <= 0) {
                        status = "CANNOT_USE";
                    } else {
                        status = "OK";
                    }
                } else {
                    desc = "<div class='alert alert-warning'><h4>Sorry, there is no cab services around you.</h4></div>";
                    status = "CANNOT_USE";
                }
            } else {
                desc = "<div class='alert alert-warning'><h4>Sorry, your session has expired !. Please <a href='index.jsp'>Refresh.</a></h4></div>";
                status = "NO_ORDER";
            }
        } catch (Exception e) {
            status = "ERROR";
            desc = "<h4 class=\"text text-danger\"><b>Sorry,</b> Error while Getting Services.</h4>";
            System.out.println("[ GET SERV ] - Error : " + e);
        }

        HashMap hashMap = new HashMap();

        hashMap.put("status", status);
        hashMap.put("description", desc);

        array.add(hashMap);
        out.print(array.toJSONString());
    }
}
