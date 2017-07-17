<%-- 
    Document   : findServices
    Created on : Jan 27, 2015, 7:34:15 PM
    Author     : Deeptha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html>
    <head>
        <title>Find Services - Pickme.lk</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="_/components/css/bootstrap.css">
        <link rel="stylesheet" type="text/css" href="_/css/slider.css">
        <link rel="stylesheet" type="text/css" href="_/css/awesomplete.css">
        <link rel="stylesheet" type="text/css" href="_/css/styles.css">
        <link href="images/favicon/default.ico" rel="icon" type="image/x-icon">
    </head>
    <body>

        <!-- implement JavaScript -->
        <script src="_/js/jquery.min.js" type="text/javascript"></script>
        <script src="_/js/jquery.form.js" type="text/javascript"></script>
        <script src="_/components/js/bootstrap.js" type="text/javascript"></script>
        <script src="_/js/Chart.js" type="text/javascript"></script>
        <script src="_/js/bootbox.js" type="text/javascript"></script>
        <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=false&libraries=places"></script>
        <script src="_/js/awesomplete.min.js" type="text/javascript"></script>
        <script src="_/js/bootstrap-slider.js" type="text/javascript"></script>
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
                    <%@include file="_/components/jsp/find-a-service-view.jsp"%>
                </div>
                <div class="col-lg-4">
                    <%@include file="_/components/jsp/find-a-service-panel.jsp"%>
                </div>
            </div>
        </div><!-- main container -->

    </body>
    <%
        String s = request.getParameter("search");
        if (s != null) {
    %>
    <script type="text/javascript">
        keywords = "<%=s%>";
        $('#filter-keywords').val("<%=s%>");
    </script>
    <%}%>

    <%
        String c = request.getParameter("city");
        String cl = request.getParameter("city_label");

        if (c != null & cl != null) {
    %>
    <script type="text/javascript">
        $(function() {
            setTempCity('<%=c%>', '<%=cl%>');
        });
    </script>
    <%}%>
    <script type="text/javascript">
        $(function() {
            filterServices();
        });
    </script>
</html>