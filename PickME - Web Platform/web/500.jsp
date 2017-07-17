<%-- 
    Document   : 404
    Created on : Jan 15, 2015, 4:16:07 PM
    Author     : Deeptha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>500 - Something Went Wrong !</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="_/components/css/bootstrap.css">
        <link rel="stylesheet" type="text/css" href="_/css/styles.css">
    </head>
    <body>

        <!-- navbar start -->
        <%@include file="_/components/jsp/navbar-top.jsp"%>
        <!-- navbar end -->

        <div class="container">
            <div class="error-msg well">
                <h1 class="text-center page-header">Something Went Wrong <small>500</small></h1>
                <a class="btn btn-success btn-block" href="index.jsp">Back</a>
            </div> 
        </div>
        <!-- implement JavaScript -->
        <script src="_/js/jquery.min.js" type="text/javascript"></script>
        <script src="_/js/jquery.form.js" type="text/javascript"></script>
        <script src="_/components/js/bootstrap.js" type="text/javascript"></script>
        <script src="_/js/functions.js" type="text/javascript"></script>
    </body>
</html>