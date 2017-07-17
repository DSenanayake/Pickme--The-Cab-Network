<%-- 
    Document   : shoppingCart
    Created on : Feb 20, 2015, 12:21:49 AM
    Author     : Deeptha
--%>

<%@page import="Model.CartItem"%>
<%@page import="Model.Cart"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html>
    <head>
        <title>Shopping Cart - Pickme.lk</title>
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
                <div class="col-md-9">
                    <h3 class="page-header">Shopping Cart</h3>

                    <div class="page-header">
                        <%@include file="_/components/jsp/cart.jsp"%>
                    </div>

                    <div>
                        <a class="btn btn-sm btn-default" href="http://localhost:8080/Pickme.lk">Keep Shopping</a>
                    </div>
                </div>
                <div class="col-md-3">
                    <h3 class="page-header">Cart Summary</h3>
                    <div class="page-header">
                        <%@include file="_/components/jsp/checkout.jsp"%>
                    </div>
                </div>
            </div>
        </div><!-- main container -->
        <script type="text/javascript">
            $(function() {
                viewCart();
            });
        </script>
    </body>

</html>