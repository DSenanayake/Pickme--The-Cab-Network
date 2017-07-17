<%-- 
    Document   : OutOfMemory
    Created on : Mar 9, 2015, 11:51:49 PM
    Author     : Deeptha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html>
    <head>
    <u:KeepLogged request="${pageContext.request}" />
    <title>Home - Pickme.lk</title>
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
    <script src="_/js/functions.js" type="text/javascript"></script>


    <!-- navbar start -->
    <%@include file="_/components/jsp/navbar-top.jsp"%>
    <!-- navbar end -->
    <div class="container">
        <div class="error-msg well well-lg">
            <h1 class="text-center page-header">Out Of Memory <small>PermGem Space</small></h1>
            <a class="btn btn-success btn-block" onclick="goBack()">Back</a>
        </div> 
    </div>
</body>
</html>