<%@page import="java.net.URLEncoder"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%try {
        pageContext.setAttribute("input", URLEncoder.encode(request.getParameter("input") != null ? request.getParameter("input") : request.getParameter("term"), "UTF-8"));
%>
<c:import url="https://maps.googleapis.com/maps/api/place/autocomplete/json?input=${input}&components=country:lk&types=(cities)&key=AIzaSyCI0H9IN9W9P2JfSw2xH22JPdX34wi2TVA" />
<%} catch (Exception e) {
        e.printStackTrace();
        out.print("{\"status\":\"ERROR\"}");
    }%>