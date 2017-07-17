<%-- 
    Document   : serviceDashboard
    Created on : Jan 15, 2015, 5:21:23 PM
    Author     : Deeptha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Dashboard - Pickme.lk</title>
        <%Model.Service.redirect(request, response);%>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="_/components/css/bootstrap.css">
        <link href="_/css/chosen.css" type="text/css" rel="stylesheet">
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
        <script type="text/javascript" src="_/js/chosen.jquery.min.js"></script>
        <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=false&libraries=places"></script>
        <script src="_/js/functions.js" type="text/javascript"></script>
        <%=Model.Service.setMembershipMsg(session)%>
        <div id="wrapper">
            <!--PANEL START-->
            <div id="sidebar-wrapper">
                <ul class="sidebar-nav">
                    <li class="service-name">
                        <h3 id="service-name"><%=Model.User.getFirstNameByUser((Db.LoginDetails) session.getAttribute("CURRENT_USER"))%></h3>
                    </li>
                    <!--                    <li class="sidebar-brand">
                                            <img class="img-thumbnail" src="images/temp/Car-icon.png">
                                        </li>-->
                    <li>
                        <a href="#overview"><span class="glyphicon glyphicon-stats" aria-hidden="true"></span> Overview </a>
                    </li>
                    <li>
                        <a href="#where" onclick="live_cabs()"><span class="glyphicon glyphicon-stats" aria-hidden="true"></span> Where My Cabs Are ? </a>
                    </li>
                    <li>
                        <a href="#messages" onclick="messages()"><span class="glyphicon glyphicon-envelope" aria-hidden="true"></span> Messages </a>
                    </li>
                    <li>
                        <a href="#services"><span class="glyphicon glyphicon-sort" aria-hidden="true"></span> Services</a>
                    </li>
                    <li>
                        <a href="#transactions"><span class="glyphicon glyphicon-usd" aria-hidden="true"></span> Transactions</a>
                    </li>
                    <li>
                        <a href="#drivers"><span class="glyphicon glyphicon-user" aria-hidden="true"></span> Drivers</a>
                    </li>
                    <li>
                        <a href="#cabs"><span class="glyphicon glyphicon-road" aria-hidden="true"></span> Cabs</a>
                    </li>
                    <li>
                        <a href="#profile"><span class="glyphicon glyphicon-star" aria-hidden="true"></span> Service Profile</a>
                    </li>
                    <li>
                        <a href="#support"><span class="glyphicon glyphicon-question-sign" aria-hidden="true"></span> Support</a>
                    </li>
                </ul>
            </div>
            <!--PANEL END-->

            <!--PAGE CONTENT START-->
            <div id="page-content-wrapper">
                <div class="dashboard-user-panel col-lg-12">
                    <div class="container-fluid">
                        <div class="hide visible-xs pull-left">
                            <button id="toggle-wrapper" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-th-large"></span></button>
                        </div>
                        <label class="pull-right"><span class="glyphicon glyphicon-dashboard" aria-hidden="true"></span> Welcome, Service <a href="Logout" data-toggle="tooltip" title="Logout" data-placement="bottom"><span class="glyphicon glyphicon-log-out"></span></a></label>
                    </div>
                </div>

                <div style="margin-bottom: 50px" class="dashboard-main-background col-lg-12">
                    <div class="container-fluid">

                        <!--container start-->
                        <div id="dashboard-container">


                            <!--container ends-->

                            <div style="display: block" id="overview" class="main-container"><!--overview start-->
                                <h2>Overview</h2>
                                <div class="row">
                                    <div class="col-md-6">
                                        <!--ORDERS CONTAINER START-->
                                        <div class="dashboard-continer">
                                            <h3 class="page-header">Service Orders</h3>
                                            <ul id="service-orders" class="list-group">


                                            </ul>
                                            <!--<button class="btn btn-sm btn-default"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> View All</button>-->
                                        </div>
                                        <!--ORDERS CONTAINER END-->
                                    </div>
                                    <div class="col-md-6">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <div class="dashboard-continer">
                                                    <h4 class="page-header">Statistics</h4>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div class="dashboard-continer">
                                                    <h4 class="page-header">Statistics</h4>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div><!--overview ends-->

                            <div style="display: none" id="where" class="main-container">
                                <h2>Where My Cabs Are ?</h2>
                                <%@include file="_/components/jsp/live-map.jsp"%>
                            </div>

                            <div style="display: none" id="messages" class="main-container">
                                <h2>Messages</h2>
                                <%@include file="_/components/jsp/message_center.jsp" %>
                            </div>

                            <div style="display: none" id="services" class="main-container">
                                <h2>Services</h2>
                            </div>

                            <div style="display: none" id="transactions" class="main-container">
                                <h2>Transactions</h2>
                            </div>

                            <div style="display: none" id="drivers" class="main-container">
                                <h2>Drivers</h2>
                                <%@include file="_/components/jsp/driver-reg.jsp"%>
                            </div>

                            <div style="display: none" id="cabs" class="main-container">
                                <h2>Cabs</h2>
                            </div>

                            <div style="display: none" id="profile" class="main-container">
                                <%@include file="_/components/jsp/service-dashboard-profile.jsp"%>
                            </div>

                            <div style="display: none" id="support" class="main-container">
                                <h2>Support</h2>
                            </div>
                        </div>
                    </div>
                </div>

                <%@include file="_/components/jsp/dashboard-bottom.jsp" %>

            </div>
            <!--MODAL START-->
            <div id="details-modal" class="modal fade">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button data-dismiss="modal" class="close"><span>&times;</span></button>
                            <h4 id="modalTitle" class="modal-title">New Ride from <a href="#" id="full-name">Loading...</a></h4>
                        </div>
                        <!--MODAL BODY START-->
                        <div class="modal-body" id="order-details">

                        </div>
                        <!--MODAL BODY END-->
                        <div class="modal-footer">
                            <button class="btn btn-sm btn-default pull-left" data-toggle="tooltip" title="Payment Status" id="payment-status">Completed</button>
                            <button id="new-ride-btn" class="btn btn-success">Take a Ride</button>
                        </div>
                    </div>
                </div>
            </div>
            <!--MODAL END-->
            <!--PAGE CONTENT END-->
        </div>

        <script type="text/javascript">

            var no_membership = false;

            $('#toggle-wrapper').click(function() {
                $('#wrapper').toggleClass('toggled');
                $(this).toggleClass('active');
            });

            $(function() {
                getServiceOrders(true);
                getUpdates(true);

                if (!no_membership) {
                    setInterval(function() {
                        getServiceOrders(false);
                    }, 15000);
                }
//                $('#dashboard-container').html($('#profile').html());

                $('.sidebar-nav li a').click(function(evt) {
                    var id = $(this).attr('href');
//                    $('#dashboard-container').html($(id).html());
                    $('#dashboard-container div.main-container').hide();
                    $(id).show();
                });

            });

            function messages() {
                refresh();
            }


            function live_cabs() {
                getUpdates(false);
            }

            //<editor-fold defaultstate="collapsed" desc="get Service Orders">
            function getServiceOrders(firsttime) {
                if (!no_membership) {
                    createRequest();
                    if (firsttime) {
                        $('#service-orders').html(makePleaseWait().innerHTML);
                    }

                    xmlhttp.open('post', 'GetServiceOrders', true);
                    xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
                    xmlhttp.send();

                    xmlhttp.onreadystatechange = function() {
                        if (xmlhttp.readyState === 4 & xmlhttp.status === 200) {
                            var res = xmlhttp.responseText.trim();
                            if (res === 'NOT_LOGGED') {
                                document.location = "userLogin.jsp";
                            } else if (res === 'NO_MEMBERSHIP') {
                                no_membership = true;
                                $('#service-orders').html('<div class="no-membership-banner">You Don\'t have a Membership Plan.</div>');
                            } else {
                                $('#service-orders').html(res);
                            }
                        }
                    };
                }
            }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Refer Order">
            function referOrder(id) {

                var cab = $('#cab').val();
                var driver = $('#driver').val();

                xmlhttp.open('post', 'ReferOrder', true);
                xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
                xmlhttp.send('order=' + id + '&cab=' + cab + '&driver=' + driver);

                xmlhttp.onreadystatechange = function() {
                    if (xmlhttp.readyState === 4 & xmlhttp.status === 200) {
                        var res = xmlhttp.responseText.trim();
                        alert(res);
                        getServiceOrders(true);
                    }
                };
            }
//</editor-fold>
        </script>

        <%@include file="_/components/jsp/show-on-map.jsp" %>
    </body>
</html>