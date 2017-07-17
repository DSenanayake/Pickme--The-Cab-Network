<%-- 
    Document   : restricted
    Created on : Mar 4, 2015, 3:43:46 PM
    Author     : Deeptha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="u" uri="User" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
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
    </head>
    <body>
        <!-- implement JavaScript -->
        <script src="_/js/jquery.min.js" type="text/javascript"></script>
        <script src="_/js/jquery.form.js" type="text/javascript"></script>
        <script src="_/js/jquery.easing.1.3.js" type="text/javascript"></script>
        <script src="_/components/js/bootstrap.js" type="text/javascript"></script>
        <script src="_/js/Chart.js" type="text/javascript"></script>
        <script src="_/js/bootbox.js" type="text/javascript"></script>
         


        <!-- navbar start -->
        <%@include file="_/components/jsp/navbar-top.jsp"%>
        <!-- navbar end -->
        
        <%@include file="_/components/jsp/restricted.jsp" %>
        <script src="_/js/functions.js" type="text/javascript"></script>
    </body>
</html>
