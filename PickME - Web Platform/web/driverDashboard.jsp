<%-- 
    Document   : driverDashboard
    Created on : Mar 3, 2015, 3:23:35 PM
    Author     : Deeptha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html>
    <head>
    <u:KeepLogged request="${pageContext.request}" />
    <title>Driver Dashboard - Pickme.lk</title>
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
    <div class="container custom-bg">
        <div class="col-md-4">
            <div class="panel panel-default">
                <div class="panel-heading"></div>
                <ul class="list-group">
                    <li class="list-group-item text text-center">
                        <img src="<%=new Model.Driver().getProfilePic((Db.LoginDetails) session.getAttribute("CURRENT_USER"))%>" width="150px">
                    </li>
                    <a class="list-group-item" onclick="order()"><span class="glyphicon glyphicon-map-marker"></span> Current Order</a>
                    <a class="list-group-item"  onclick="profile()"><span class="glyphicon glyphicon-user"></span> Profile</a>
                    <a class="list-group-item"  onclick="app()"><span class="glyphicon glyphicon-phone"></span> Mobile Application</a>
                    <li class="list-group-item"><a href="Logout" class="btn btn-sm btn-danger">Logout <span class="glyphicon glyphicon-off"></span></a></li>
                </ul>
            </div>
        </div>
        <div class="col-md-8">
            <div id="order" style="display: block">
                <h3 class="page-header">Current Order</h3>
                <div class="well">
                    <div id="map-canvas" style="height: 350px"></div>
                    <div id="order-details">
                    </div>
                </div>
            </div>
            <div id="profile" style="display: none">
                <h3 class="page-header">Driver Profile</h3>
                <h5><a onclick="editProfile()">Customize</a> your Personal Details.</h5>
                <div id="view" class="well"><div class="row">
                        <form class="form-horizontal">
                            <%=new Model.Driver().printDriverDetails((Db.LoginDetails) session.getAttribute("CURRENT_USER"), false)%>
                        </form>
                    </div></div>
                <div id="edit" class="well" style="display: none">
                    <form class="form-horizontal" method="post" id="edit-pro">
                        <div class="row">
                            <%=new Model.Driver().printDriverDetails((Db.LoginDetails) session.getAttribute("CURRENT_USER"), true)%>
                        </div>
                        <button class="btn btn-primary" type="button" onclick="viewProfile()"><span class="glyphicon glyphicon-backward"></span> Back</button>
                        <button class="btn btn-primary"><span class="glyphicon glyphicon-ok"></span> Save</button>
                    </form>

                </div>
            </div>
            <div id="app" style="display: none">
                <h3 class="page-header">Mobile Application</h3>
                <h5>Download Pickme.lk For Mobile to Track your Location.</h5>
                <div class="well">
                    <legend>Screenshots</legend>
                    <div class="row">
                        <img  src="images/other/app2.png" class="img-responsive col-md-3 little-space">

                        <img  src="images/other/app.png" class="img-responsive col-md-3 little-space">

                        <div class="col-md-6">
                            <label>Download Pickme.lk Mobile Application for Track you Location. This will helps your Cab Service Provider to Track your Location easily without picky telephone calls.</label>
                            <a class="btn btn-success btn-lg btn-block" href="DownloadAPK">Download</a>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
    <script type="text/javascript">

        var directionsDisplay;
        var directionsService = new google.maps.DirectionsService();
        var map;
        var no_order = true;
        function initialize(lat, lng) {
            directionsDisplay = new google.maps.DirectionsRenderer();
            var my = new google.maps.LatLng(lat, lng);
            var mapOptions = {
                zoom: 10,
                center: my
            };
            map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
            directionsDisplay.setMap(map);
        }

        function calcRoute(s_lat, s_lng, e_lat, e_lng) {
            var start = new google.maps.LatLng(s_lat, s_lng);
            var end = new google.maps.LatLng(e_lat, e_lng);
            var request = {
                origin: start,
                destination: end,
                travelMode: google.maps.TravelMode.DRIVING
            };
            directionsService.route(request, function(response, status) {
                if (status === google.maps.DirectionsStatus.OK) {
                    directionsDisplay.setDirections(response);
                }
            });
        }
        function getCurrentOrder() {
            if (no_order) {
                $.post('GetCurrentOrder', {}, function(data, textStatus, jqXHR) {
                    if (textStatus === 'success') {
                        var html;
                        var status = data[0].status;
                        if (status === 'FOUND') {
//                            html = ;
                            initialize(data[0].my_location.lat, data[0].my_location.lng);
                            calcRoute(data[0].start_point.lat, data[0].start_point.lng, data[0].end_point.lat, data[0].end_point.lng);
                            html = "<div style='margin-top:15px;'>"
                                    + "<legend>Order Details</legend>"
                                    + "<b>Ordered At :</b>" + data[0].time + "<br>"
                                    + "<b>Current Cab :</b>" + data[0].cab + "<br>"
                                    + "<b>Order Distance :</b>" + data[0].km + "Km<br>"
                                    + "<legend style='margin-top:15px;'>Client Details</legend>"
                                    + "<img src='" + data[0].user.pic + "' width='90px'><br>"
                                    + "<b>First Name : </b>" + data[0].user.fname + "<br>"
                                    + "<b>Last Name : </b>" + data[0].user.lname + "<br>"
                                    + "<b>Gender : </b>" + data[0].user.gender + "<br>"
                                    + "<b>mobile : </b>" + data[0].user.mobile + "<br>";
                            html += "</div>";
                            html += "<div id='nextBtn' style='margin-top:15px;'>"
                                    + "<button onclick='reached(" + data[0].id + ")' class='btn btn-primary'>"
                                    + "Reached to the Client"
                                    + "</button>"
                                    + "</div>";
                            no_order = false;
                        } else {
                            html = '<div class"text text-center"><h3>You Dont\'t have a order right now.</h3></div>';
                        }
                        $('#order-details').html(html);
                    }
                }, 'json');
            }
        }
        function reached(id) {
            $.post('Reached', {id: id}, function(data, textStatus, jqXHR) {
            }, 'text');
            $('#nextBtn').html('<button class="btn btn-success" onclick="completed(' + id + ')" >Complete</button>');
        }
        function completed(id) {
            $.post('Completed', {id: id}, function(data, textStatus, jqXHR) {
            }, 'text');
            no_order = true;
            getCurrentOrder();
            document.location.reload();
        }
        function order() {
            $('#order').show();
            $('#profile').hide();
            $('#app').hide();
        }
        function profile() {
            $('#order').hide();
            $('#profile').show();
            $('#app').hide();
        }
        function app() {
            $('#order').hide();
            $('#profile').hide();
            $('#app').show();
        }
        function editProfile() {
            $('#view').hide();
            $('#edit').show();
        }
        function viewProfile() {
            $('#view').show();
            $('#edit').hide();
        }
        $(function() {
            getCurrentOrder();
            setInterval(getCurrentOrder, 4500);
        });
    </script>

</body>
</html>