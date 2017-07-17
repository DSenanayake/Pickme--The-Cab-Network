<div id="mainContainer">
    <%
        String city = "";
        if (session.getAttribute("CURRENT_CITY") != null) {
            city = session.getAttribute("CURRENT_CITY").toString();
    %>
    <h2 class="page-header">Pickme.lk <small>anytime anywhere ! - <span class="google-city"><%=Model.GooglePlaceDetails.getCityById(city)%></span> <a onclick="changeCity()">(change)</a></small></h2>
    <!--INPUT CONTAINER START-->
    <input type="hidden" id="c_city" value="<%=city%>"/>
    <script type="text/javascript">
        $(function() {
            findOnMap("<%=city%>");
//            getCityNames();
        });
    </script>
    <%}%>
    <!--<h2 class="page-header">Pickme.lk <small>anytime anywhere !</small></h2>-->
    <div id="findCityContainer">
        <legend>Find your closest City</legend>
        <div class="form-group">
            <input  type="text" id="homeInputCity" name="homeInputCity" placeholder="Search your city..." class="form-control" />
        </div>

        <div class="form-group">
            <img id="loaderImg" class="center-block" width="32px">

            <div id="noResultMsg" style="display: none" class="alert alert-danger">
                <b>Sorry !</b> We think no Cab Services Registered around you.
            </div>

            <!--CITY RESULTS START-->
            <ul id="homeResultCity" class="list-inline" ></ul>
            <!--CITY RESULTS END-->

        </div>

        <div class="form-group">
            <legend>Recent Searches</legend>
            <ul class="list-inline" >
                <li><a class="btn btn-success little-space" href="#">Colombo</a></li>
                <li><a class="btn btn-success little-space" href="#">Kurunegala</a></li>
                <li><a class="btn btn-success little-space" href="#">Kandy</a></li>
                <li><a class="btn btn-success little-space" href="#">Negambo</a></li>
                <li><a class="btn btn-success little-space" href="#">Wattala</a></li>
                <li><a class="btn btn-success little-space" href="#">Ja-Ela</a></li>
                <li><a class="btn btn-success little-space" href="#">Gampaha</a></li>
                <li><a class="btn btn-success little-space" href="#">Mathara</a></li>
            </ul>
        </div>
    </div>
    <!--INPUT CONTAINER END-->
    <%--}--%>

    <!--MAP START-->
    <div id="clientLocationContainer" style="display: none">
        <legend>Show us Your Actual Position on the Map.</legend>
        <div class="form-group">
            <input type="text" id="placesSearcher" name="placeSearcher" class="form-control" placeholder="Search Place..."/>
        </div>
        <div class="form-group">
            <div id="mapContainer" style="height: 400px;margin: 0;padding: 0"></div>
        </div>
        <div class="form-group form-inline">
            <button class="btn btn-danger"><%=Model.HTML.getIcon("chevron-left")%> Back</button>
            <button onclick="canNextForSetDestination()" class="btn btn-success pull-right">Next <%=Model.HTML.getIcon("chevron-right")%></button>
        </div>
    </div>
    <!--MAP END-->

    <!--DEST MAP START-->
    <div id="clientDestinationContainer" style="display: none">
        <legend>Where do you want to go ?</legend>
        <div class="form-group">
            <input type="text" id="destSearcher" name="destSearcher" class="form-control" placeholder="Search Destination..."/>
        </div>
        <div class="form-group">
            <div id="destContainer" style="height: 400px;margin: 0;padding: 0"></div>
        </div>
        <div class="form-group form-inline">
            <button class="btn btn-danger"><%=Model.HTML.getIcon("chevron-left")%> Back</button>
            <button onclick="generateOrderDistance()" class="btn btn-success pull-right">Next <%=Model.HTML.getIcon("chevron-right")%></button>
        </div>
    </div>
    <!--DEST MAP END-->

    <div id="orderDetailsContainer" style="display: none;">
        <legend>Order Summary</legend>
        <div class="form-group">
            <div style="height: 400px" id="order-map-container"></div>
        </div>

        <div class="panel panel-success">
            <div class="panel-heading">
                <h3 class="panel-title">Order Details</h3>
            </div>

            <ul class="list-group">
                <li class="list-group-item">Distance : <b id="order-distance"></b></li>
                <li class="list-group-item">Duration : <b id="order-duration"></b></li>
                <li class="list-group-item">
                    <button class="btn btn-sm btn-success" onclick="showPickService()">Order Now <%=Model.HTML.getIcon("usd")%></button>
                    <i class="text text-info"><b>Note:</b> Cab will pick you up as soon as posable after confirm your payment.</i>
                </li>
            </ul>
        </div>
    </div>

    <div class="modal fade" id="select-service">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title">Select a Service <button class="btn close" data-dismiss="modal"><span>&times;</span></button></h3>
                </div>
                <div class="modal-body">
                    <ul class="list-group list-services" id="list-services">

                    </ul>
                    <input type="hidden" name="selected-service" id="selected-service" value="NONE"/>
                </div>
                <div class="modal-footer list-services-panel">
                    <button class="btn btn-default" onclick="addToCart(false)">Add to Cart <%=Model.HTML.getIcon("shopping-cart")%></button>
                    <button class="btn btn-info" onclick="addToCart(true)">Pick Me <%=Model.HTML.getIcon("map-marker")%></button>
                </div>
            </div>
        </div>
    </div>

</div>