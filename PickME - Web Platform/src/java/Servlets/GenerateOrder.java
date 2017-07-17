package Servlets;

import Model.CabOrder;
import Model.Destination;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Deeptha
 */
public class GenerateOrder extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String destination = request.getParameter("destination");
            String distance = request.getParameter("distance");
            String duration = request.getParameter("duration");

            HttpSession hs = request.getSession();
            CabOrder order = (CabOrder) hs.getAttribute("CURRENT_ORDER");
            if (order != null) {
                destination = destination.replace('(', ' ');
                destination = destination.replace(')', ' ');
                String dest[] = destination.split(",");

                order.setDestination(new Destination(Double.parseDouble(dest[0]), Double.parseDouble(dest[1])));
                order.setDistance(Double.parseDouble(distance) / 1000);
                order.setDuration(duration);

                hs.setAttribute("CURRENT_ORDER", order);

                String html = ""
                        + "<script type='text/javascript'>"
                        + "var directionsDisplay;"
                        + "var directionsService = new google.maps.DirectionsService();"
                        + "var map;"
                        + ""
                        + "function initialize() {"
                        + "  directionsDisplay = new google.maps.DirectionsRenderer();"
                        + "  var nowhere = new google.maps.LatLng(41.850033, -87.6500523);"
                        + "  var mapOptions = {"
                        + "    zoom:15,"
                        + "    center: nowhere"
                        + "  };"
                        + "  map = new google.maps.Map(document.getElementById('map-container'), mapOptions);"
                        + "  directionsDisplay.setMap(map);"
                        + "}"
                        + ""
                        + "function calcRoute() {"
                        + "  var start = new google.maps.LatLng(" + order.getLocation().getLattitude() + ", " + order.getLocation().getLongitude() + ");"
                        + "  var end = new google.maps.LatLng(" + order.getDestination().getLattitude() + "," + order.getDestination().getLongitude() + ");"
                        + "  var request = {"
                        + "      origin:start,"
                        + "      destination:end,"
                        + "      travelMode: google.maps.TravelMode.DRIVING"
                        + "  };"
                        + "  directionsService.route(request, function(response, status) {"
                        + "    if (status == google.maps.DirectionsStatus.OK) {"
                        + "      directionsDisplay.setDirections(response);"
                        + "    }"
                        + "  });"
                        + "}"
                        + ""
                        + "//google.maps.event.addDomListener(window, 'load', initialize);"
                        + "initialize();"
                        + "calcRoute();"
                        + "</script>"
                        + "<div class='col-md-8'>"
                        + " <div class='panel panel-default'>"
                        + "     <div class='panel-header'>"
                        + "         <h3 class='panel-title'>Cab Order</h3>"
                        + "     </div>"
                        + "     <div class='panel-body'>"
                        + "         <div id='map-container' style='height:500px'></div>"
                        + "     <div>"
                        + "     <ul class='list-group'>"
                        + "         <li><b>Distance :</b>" + order.getDistance() + " Km</li>"
                        + "         <li><b>Duration :</b>" + order.getDuration() + "</li>"
                        + "     </ul>"
                        + " </div>"
                        + "</div>"
                        + "<div class='col-md-4'>"
                        + ""
                        + "</div>";

                out.print(html);

            } else {
                out.print("<div class='alert alert-warning'>"
                        + "<b>Sorry,</b>"
                        + " We have lost your current order data. Because your session has expired !"
                        + "</div>");
            }
        } catch (Exception e) {
            out.print("<div class='alert alert-danger'>"
                    + "<b>Sorry,</b>"
                    + " Error while generating Order Details."
                    + "</div>");
            System.err.println("[ GENER ORDR ] - Error : " + e);
        }
    }
}
