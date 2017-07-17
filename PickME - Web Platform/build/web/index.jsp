<%-- 
    Document   : index
    Created on : Jan 15, 2015, 4:02:51 PM
    Author     : Deeptha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="u" uri="User" %>
<!DOCTYPE html>
<html>
    <head>
        <u:KeepLogged request="${pageContext.request}" />
        <title>Home - Pickme.lk</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="_/components/css/bootstrap.css">
        <link rel="stylesheet" type="text/css" href="_/css/styles.css">
        <link href="images/favicon/default.ico" rel="icon" type="image/x-icon"> 
        <%
        out.print("<meta http-equiv=\"cache-control\" content=\"max-age=0\" />"
                    + "<meta http-equiv=\"cache-control\" content=\"no-cache\" />"
                    + "<meta http-equiv=\"expires\" content=\"0\" />"
                    + "<meta http-equiv=\"expires\" content=\"Tue, 01 Jan 1980 1:00:00 GMT\" />"
                    + "<meta http-equiv=\"pragma\" content=\"no-cache\" />");
        %>
    </head>
    <body>
        <!-- implement JavaScript -->
        <script src="_/js/jquery.min.js" type="text/javascript"></script>
        <script src="_/js/jquery.form.js" type="text/javascript"></script>
        <script src="_/js/jquery.easing.1.3.js" type="text/javascript"></script>
        <script src="_/components/js/bootstrap.js" type="text/javascript"></script>
        <script src="_/js/Chart.js" type="text/javascript"></script>
        <script src="_/js/bootbox.js" type="text/javascript"></script>
        <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=false&libraries=places"></script>
        <script src="_/js/functions.js" type="text/javascript"></script>


        <!-- navbar start -->
        <%@include file="_/components/jsp/navbar-top.jsp"%>
        <!-- navbar end -->

        <!-- header start -->
        <%@include file="_/components/jsp/header-top.jsp"%>
        <!-- header end -->

        <!-- middle panel start -->
        <%@include file="_/components/jsp/middle-panel.jsp"%>
        <!-- middle panel end -->

        <div class="container-fluid custom-bg">
            <!-- desc -->
            <div class="row">
                <div class="col-lg-8">
                    <%@include file="_/components/jsp/home-intro.jsp"%>
                    <%--@include file="_/components/jsp/home-description.jsp"%>
                    <%@include file="_/components/jsp/home-quick-orders.jsp"--%>
                </div>
                <div class="col-lg-4" id="home-side-panel">
                    <%@include file="_/components/jsp/home-stats-side.jsp"%>
                    <%--@include file="_/components/jsp/home-top-ten-services.jsp"--%>
                    <%@include file="_/components/jsp/home-find-by-city.jsp"%>
                    <%@include file="_/components/jsp/home-customer-feedback.jsp"%>
                </div>
            </div>
        </div><!-- main container -->

    </body>
</html>