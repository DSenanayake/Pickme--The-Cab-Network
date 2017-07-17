<%-- 
    Document   : Register
    Created on : Jan 15, 2015, 4:16:49 PM
    Author     : Deeptha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Register - Pickme.lk</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="_/components/css/bootstrap.css">
        <link rel="stylesheet" type="text/css" href="_/css/styles.css">
    </head>
    <body id="registerPage">
        <!-- implement JavaScript -->
        <script src="_/js/jquery.min.js" type="text/javascript"></script>
        <script src="_/js/jquery.form.js" type="text/javascript"></script>
        <script src="_/components/js/bootstrap.js" type="text/javascript"></script>
        <script src="_/js/Chart.js" type="text/javascript"></script>
        <script src="_/js/functions.js" type="text/javascript"></script>
        <script src="_/js/bootbox.js" type="text/javascript"></script>

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
                    <%
                        String isServiceReg = request.getParameter("serviceReg");
                        if (isServiceReg != null & Boolean.parseBoolean(isServiceReg)) {
                    %><%@include file="_/components/jsp/service-registration.jsp"%><%
                    } else {
                    %><%@include file="_/components/jsp/client-registration.jsp"%><%
                        }
                    %>
                </div>
                <div class="col-lg-4">
                    <%@include file="_/components/jsp/home-stats-side.jsp"%>
                    <%@include file="_/components/jsp/home-top-ten-services.jsp"%>
                    <%@include file="_/components/jsp/home-find-by-city.jsp"%>
                    <%@include file="_/components/jsp/home-customer-feedback.jsp"%>
                </div>
            </div>
        </div><!-- main container -->
        <script type="text/javascript">
            $(function() {
                var target = $('.middle-bg').offset().top;
                $('body').animate({scrollTop:target},1000);
            });
        </script>
    </body>
</html>