<%-- 
    Document   : clientProfile
    Created on : Feb 12, 2015, 7:44:44 PM
    Author     : Deeptha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Client Profile - Pickme.lk</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="_/components/css/bootstrap.css">
        <link rel="stylesheet" type="text/css" href="_/css/styles.css">
        <link href="images/favicon/default.ico" rel="icon" type="image/x-icon">
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
        <%--@include file="_/components/jsp/header-top.jsp"%>
        <!-- header end -->

        <!-- middle panel start -->
        <%@include file="_/components/jsp/middle-panel.jsp"--%>
        <!-- middle panel end -->

        <div class="container-fluid custom-bg">
            <!-- desc -->
            <div class="row">
                <div class="col-lg-9">
                    <%@include file="_/components/jsp/client-profile.jsp" %>
                </div>
                <div class="col-lg-3" id="home-side-panel">
                    <%@include file="_/components/jsp/client-profile-panel.jsp"%>
                </div>
            </div>
        </div><!-- main container -->
        
    </body>
    
</html>