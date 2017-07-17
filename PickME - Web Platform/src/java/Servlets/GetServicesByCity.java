package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;

public class GetServicesByCity extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String place_id = request.getParameter("place_id");
        JSONArray array = new JSONArray();
        try {
            List<Db.ServiceProvider> providers = Model.Service.getServicesByCity(place_id);

            if (!providers.isEmpty()) {

                for (Db.ServiceProvider provider : providers) {
                    HashMap map = new HashMap();

                    map.put("id", provider.getServiceProviderId());
                    map.put("name", provider.getServiceProviderName());
                    map.put("cost_per_km", provider.getServiceDetails().getCostPerKm());
                    map.put("coverage_area", provider.getServiceDetails().getCoverageArea());
                    map.put("minimum_distance", provider.getServiceDetails().getMinimumDistance());
                    map.put("logo_url", provider.getLogo().getLogoUrl());
                    map.put("lattitude", provider.getServiceProviderLocationDetails().getLattitude());
                    map.put("longitude", provider.getServiceProviderLocationDetails().getLongitude());
                    map.put("city_id", provider.getServiceProviderLocationDetails().getCity().getGooglePlaceId());
                    map.put("status", "OK");

                    array.add(map);
                }

                System.out.println("[ SESS ] - " + request.getSession().getAttributeNames().nextElement());

            } else {
                HashMap map = new HashMap();
                map.put("status", "NA");
                array.add(map);
            }

        } catch (Exception e) {
            HashMap map = new HashMap();
            map.put("status", "ERROR");
            array.add(map);
            System.out.println("[ GET SER ] - Error:"+e);
        } finally {
            System.gc();
            out.print(array.toJSONString());
        }

    }

}
