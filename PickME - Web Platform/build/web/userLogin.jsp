<%-- 
    Document   : userLogin
    Created on : Feb 3, 2015, 8:33:06 PM
    Author     : Deeptha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="u" uri="User" %>
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
        <%
            java.lang.Boolean reset = false;
            if (request.getParameter("reset") == null) {
                Db.LoginDetails details = (Db.LoginDetails) session.getAttribute("CURRENT_USER");
                if (details != null) {
                    response.sendRedirect("CurrentTask");
                }
            } else {
                if (Boolean.parseBoolean(request.getParameter("reset"))) {
                    reset = true;
                }
            }

        %>
        <!-- implement JavaScript -->
        <script src="_/js/jquery.min.js" type="text/javascript"></script>
        <script src="_/js/jquery.form.js" type="text/javascript"></script>
        <script src="_/components/js/bootstrap.js" type="text/javascript"></script>
        <script src="_/js/Chart.js" type="text/javascript"></script>
        <script src="_/js/bootbox.js" type="text/javascript"></script>
        <script src="_/js/functions.js" type="text/javascript"></script>


        <!-- navbar start -->
        <%@include file="_/components/jsp/navbar-top.jsp"%>
        <!-- navbar end -->


        <div class="container-fluid custom-bg">
            <!-- desc -->
            <div id="formContainer" class="well well-lg" style="margin: 20px auto;max-width: 300px;display: none;">
                <div style="text-align:center;margin-bottom:15px">
                    <img style="max-width:250px;" src="images/logos/default.png" width="100%">
                </div>

                <div style="<%="display:" + (reset ? "none" : "block")%>">
                    <form id="userLoginForm" action="LoginUser" method="post" style="min-width: 200px;">

                        <div class="form-group">
                            <input type="email" autofocus class="form-control sign-in-uname" name="uname" placeholder="E-Mail" required />
                        </div>
                        <div class="form-group" style="margin-bottom: 35px;">
                            <input type="password" class="form-control" name="pword" placeholder="Password" required  />
                            <input type="checkbox" name="stay" id="stay" value="true" />
                            <label for="stay">Stay Logged in</label>
                            <a href="userLogin.jsp?reset=true" class="pull-right"><small>Forget Password ?</small></a>
                        </div>

                        <div class="form-group">
                            <div id="signInMsg" style="display:none" class="alert alert-info"></div>
                        </div>

                        <div class="form-group">
                            <button id="signInBtn" type="submit" class="btn btn-primary btn-block">Sign in <img style="display: none" id="signInLoader" src="images/other/loader4.gif"></button><!-- submit btn -->
                        </div>

                        <a href="#" data-toggle="modal" data-target="#registerModal" onclick="closePopover()">Not a Member yet ?</a>
                    </form>
                </div>

                <div id="resetForm" style="<%="display:" + (reset ? "block" : "none")%>">
                    <legend>Reset Password ?</legend>
                    <p>Please enter your Email. Then we'll send your password to Email.</p>
                    <div class="form-group">
                        <label class="control-label">E-Mail</label>
                        <input type="email" id="resetPassword" name="resetPassword" class="form-control"/>
                    </div>
                    <div class="form-group">
                        <button onclick="resetPassword($('#resetPassword').val())" class="btn btn-default">Send <%=Model.HTML.getIcon("chevron-right")%></button>
                    </div>
                </div>

            </div>
        </div>
    </div><!-- main container -->
</body>
<script type="text/javascript">
    $(function() {
        $('#formContainer').fadeIn(500);
    });
</script>
</html>