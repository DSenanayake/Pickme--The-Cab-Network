<%-- 
    Document   : UserConfirmation
    Created on : Jan 29, 2015, 10:57:25 PM
    Author     : Deeptha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="u" uri="User"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Email Confirmation - Pickme.lk</title>
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
                    <h2 class="page-header">Verify your Account</h2>
                    <u:Confirm confirm="${param.confirm}">
                        <form method="get" class="form-inline" action="UserConfirmation.jsp">
                            <div class="form-group">
                                <label class="control-label">Confirmation ID :</label>
                                <input autofocus="true" id="c_id" size="60" required name="confirm" class="form-control">
                            </div>
                            <div class="form-group">
                                <button type="submit"class="btn btn-success">Confirm <span class="glyphicon glyphicon-flash" aria-hidden="true"></span></button>
                            </div>
                            <div class="form-group">
                                <a href="#" onclick="resendEmail()" class="btn btn-link">Resend Email</a>
                            </div>
                        </form>
                    </u:Confirm>
                </div>
                <div class="col-lg-4">
                    <%@include file="_/components/jsp/home-stats-side.jsp"%>
                    <%@include file="_/components/jsp/home-top-ten-services.jsp"%>
                    <%@include file="_/components/jsp/home-find-by-city.jsp"%>
                    <%@include file="_/components/jsp/home-customer-feedback.jsp"%>
                </div>
            </div>
        </div><!-- main container -->

    </body>
</html>