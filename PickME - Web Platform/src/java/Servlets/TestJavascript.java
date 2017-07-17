package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "TestJavascript", urlPatterns = {"/TestJavascript"})
public class TestJavascript extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.print("<!DOCTYPE html>"
                + "<html>"
                + "  <head>"
                + "    <title>Simple Map</title>"
                + "    <meta name=\"viewport\" content=\"initial-scale=1.0, user-scalable=no\">"
                + "    <meta charset=\"utf-8\">"
                + "    <style>"
                + "      html, body, #map-canvas {"
                + "        height: 100%;"
                + "        margin: 0px;"
                + "        padding: 0px"
                + "      }"
                + "    </style>"
                + "    <script src=\"https://maps.googleapis.com/maps/api/js?v=3.exp\"></script>"
                + "    <script>"
                + "var map;"
                + "function initialize() {"
                + "  var mapOptions = {"
                + "    zoom: 18,"
                + "    center: new google.maps.LatLng(" + request.getParameter("lat") + ", " + request.getParameter("lng") + ")"
                + "  };"
                + "  map = new google.maps.Map(document.getElementById('map-canvas'),"
                + "      mapOptions);"
                + "}"
                + ""
                + "google.maps.event.addDomListener(window, 'load', initialize);"
                + ""
                + "    </script>"
                + "  </head>"
                + "  <body>"
                + "    <div id=\"map-canvas\"></div>"
                + "  </body>"
                + "</html>");
    }

}
