<%-- 
    Document   : index
    Created on : Jan 30, 2015, 11:57:42 AM
    Author     : Deeptha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            session.setAttribute("Payment_Amount", "10");
        %>
        <form action='expresscheckout.jsp' METHOD='POST'>
            <input type='image' name='submit' src='https://www.paypal.com/en_US/i/btn/btn_xpressCheckout.gif' border='0' align='top' alt='Check out with PayPal'/>
        </form>
    </body>
</html>
