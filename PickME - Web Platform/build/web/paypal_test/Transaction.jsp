<%-- 
    Document   : Transaction
    Created on : Jan 30, 2015, 11:58:57 AM
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
            if(request.getParameter("status")!=null){
                if(request.getParameter("status").equalsIgnoreCase("success")){
                    out.print("Success");
                }else{
                    out.print("Canceled !");
                }
            }else{
                out.print("Invalid Request - No Transaction Details");
            }
        %>
    </body>
</html>
