<%-- 
    Document   : completeRegistration
    Created on : Jan 15, 2015, 4:17:48 PM
    Author     : Deeptha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="u" uri="User" %>
<!DOCTYPE html>
<html>
    <head>
        <u:KeepLogged request="${pageContext.request}" />
        <title>Project 4.0</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="_/components/css/bootstrap.css">
        <link rel="stylesheet" type="text/css" href="_/css/styles.css">
        <link href="images/favicon/default.ico" rel="icon" type="image/x-icon">
    </head>
    <body>
        <!-- implement JavaScript -->
        <script src="_/js/jquery.min.js" type="text/javascript"></script>
        <script src="_/js/jquery.form.js" type="text/javascript"></script>
        <script src="_/components/js/bootstrap.js" type="text/javascript"></script>
        <script src="_/js/Chart.js" type="text/javascript"></script>
        <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=false&libraries=places"></script>
        <script src="_/js/functions.js" type="text/javascript"></script>
        <!-- navbar start -->
        <%@include file="_/components/jsp/navbar-top.jsp"%>
        <!-- navbar end -->

        <div class="container-fluid custom-bg">
            <div class="container">
                <%@include file="_/components/jsp/completeServiceRegistration.jsp"%>
            </div>
        </div><!-- main container -->

    </body>
</html>