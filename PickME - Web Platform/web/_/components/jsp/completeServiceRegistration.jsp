<%
    Db.LoginDetails log = (Db.LoginDetails) session.getAttribute("CURRENT_USER");
    if (log != null) {
        if (log.getLoginStatus().getLoginStatusId() != Model.UserStatus.INACTIVATED) {
            response.sendRedirect("userLogin.jsp");
        } else {
            int user = log.getUserType().getUserTypeId();
            if (user == Model.UserType.SERVICE_PROVIDER) {
                Db.ServiceProvider provider = (Db.ServiceProvider) log.getServiceProviders().toArray()[0];
%>
<div>
    <h2 class="page-header">Complete Registration</h2>
    <form class="form-horizontal" method="post" action="CompleteServiceProfile" id="complete-form">
        <legend>Service Details</legend>
        <div class="form-group">
            <label for="serviceFormName" class="col-sm-3">Cab Service Name <span class="pull-right">:</span></label>
            <div class="col-sm-9">
                <input type="text" id="serviceFormName" name="name" placeholder="Cab Service Name" class="form-control" value="<%=provider.getServiceProviderName()%>"  />
            </div>
        </div><!-- service name -->

        <div class="form-group">
            <label for="serviceName" class="col-sm-3">Coverage Area <span  title="Coverage Area in KM from your current Service Location" data-toggle="tooltip" class="glyphicon glyphicon-info-sign" aria-hidden="true"></span> <span class="pull-right">:</span></label>
            <div class="col-sm-9">
                <div class="input-group">
                    <input type="text" id="coverage" name="coverage" placeholder="Eg: 10 KM" class="form-control" />
                    <div class="input-group-addon">KM</div>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label  class="col-sm-3">Minimum Order Distance <span class="pull-right">:</span></label>
            <div class="col-sm-9">
                <div class="input-group">
                    <input type="text" id="serviceFormCo" class="form-control" name="distance" placeholder="Eg: 1 KM" />
                    <div class="input-group-addon">KM</div>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label for="serviceName" class="col-sm-3">Cost per KM <span class="pull-right">:</span></label>
            <div class="col-sm-9">
                <div class="input-group">
                    <div class="input-group-addon">Rs:</div>
                    <input type="text" id="serviceName" name="cost" placeholder="0" class="form-control" />
                    <div class="input-group-addon">.00</div>
                </div>
            </div>
        </div>

        <legend>Location Details</legend>
        <div class="form-group">
            <label for="serviceFormAddress1" class="col-sm-3">Address 1 <span class="pull-right">:</span></label>
            <div class="col-sm-9">
                <input type="text" id="serviceFormAddress1" name="address1" placeholder="Address 1" class="form-control" />
            </div>
        </div><!-- address 1 -->

        <div class="form-group">
            <label for="serviceFormAddress2" class="col-sm-3">Address 2 <span class="pull-right">:</span></label>
            <div class="col-sm-9">
                <input type="text" id="serviceFormAddress2" name="address2" placeholder="Address 2" class="form-control"  />
            </div>
        </div><!-- address 2 -->

        <div class="form-group">
            <label for="serviceFormAddress2" class="col-sm-3">Location <span class="pull-right">:</span></label>

            <div class="col-sm-3 little-space">
                <button class="btn btn-default btn-block" type="button" onclick="selectFromMap()">Select from Map <span class="glyphicon glyphicon-map-marker" aria-hidden="true"></span></button>
            </div>
            <div class="col-sm-3 little-space">
                <input type="text" id="latitude" title="Latitude" data-toggle="tooltip" name="latitude" placeholder="Latitude" class="form-control" readonly />
            </div>
            <div class="col-sm-3 little-space">
                <input type="text" id="longitude" title="Longitude" data-toggle="tooltip" name="longitude" placeholder="Logitude" class="form-control" readonly/>
            </div>
        </div><!-- location -->

        <div class="form-group">
            <label for="serviceFromCity" class="col-sm-3">City <span class="pull-right">:</span></label>
            <div class="col-sm-9">
                <input type="text" id="city" name="city_dummy" class="form-control" placeholder="Find City">
                <input type="hidden" id="city_id" name="city" class="form-control">
                <div id="city-result" style="display: none;margin-top: 5px" ></div>
            </div>
        </div><!-- city -->

        <legend>Contact Details</legend>
        <div class="form-group">
            <label for="serviceFormTel1" class="col-sm-3">Tel 1 <span class="pull-right">:</span></label>
            <div class="col-sm-9">
                <div class="input-group">
                    <div class="input-group-addon">
                        <b>+94</b>
                    </div>
                    <input type="tel" id="tel1" name="tel1" placeholder="Tel 1" class="form-control"  />
                </div>
            </div>
        </div>

        <legend>Logging Details</legend>
        <div class="form-group">
            <label for="email" class="col-sm-3">E-Mail <span class="pull-right">:</span></label>
            <div class="col-sm-9">
                <p class="form-control-static"><%=log.getEmail()%></p>	
            </div>
        </div><!-- email -->

        <div class="form-group">
            <label for="serviceFormPassword" class="col-sm-3">Password <span class="pull-right" >:</span></label>
            <div class="col-sm-9">
                <div class="input-group">
                    <input id="serviceFormPassword" type="password" name="password" placeholder="Password" class="form-control" />
                    <span class="input-group-btn">
                        <button id="client-pw-toggle" type="button" class="btn btn-default" title="Show/Hide Password" data-toggle="tooltip" data-attach-to="#serviceFormPassword"><span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span></button>
                    </span>
                </div>				
            </div>
        </div><!-- password -->
        <hr>
        <div class="form-group">
            <div class="col-sm-9 col-sm-offset-3">
                <button type="submit" class="btn btn-success pull-right">Continue <span class="glyphicon glyphicon-ok" aria-hidden="true"></span></button>
            </div>
        </div>
    </form>

</div>
<%}
        }
    } else {
        response.sendRedirect("userLogin.jsp");
    }%>

<div class="modal fade" id="select-from-map">
    <div class="modal-dialog">
        <div class="modal-content" style="margin : 45px auto;padding: 25px">
            <input type="text" id="pac-input" class="little-space form-control" placeholder="Find Your Location"/>
            <div id="map-canvas" style="height: 400px"></div>
            <div class="modal-footer">
                <button class="btn btn-default" data-dismiss="modal">Close</button>
                <button class="btn btn-info" data-dismiss="modal" onclick="setLocation()"><span class="glyphicon glyphicon-map-marker"></span> Confirm</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var lat, lng;
    function setTempCity(id, label) {
        $('#city_id').val(id);
        $('#city').val(label);
        $('#city-result').html('<b>Selected : </b>' + label + ' <a style="cursor:pointer" onclick="clearCity()">Remove</a>');
    }
    function clearCity() {
        $('#city').val('');
        $('#city_id').val('');
        $('#city-result').html('');
        $('#city-result').slideUp();
    }
    function selectFromMap() {
        $('#select-from-map').modal({keyboard: true});
    }
    function setLocation() {
        $('#latitude').val(lat);
        $('#longitude').val(lng);
    }
    function rediret() {
        document.location.href = "serviceDashboard.jsp";
    }
    $(function() {
        var wait;
        var options = {
            beforeSend: function() {
                wait = makeDarkPleaseWait();
            },
            uploadProgress: function(event, position, total, percentComplete) {
            }, success: function(data) {
                if (data === 'OK') {
                    makeNotification('Successfully Completed Your Account. Redirects in 3 Sec(s).', '#00cc99');
                    setTimeout(rediret, 3000);
                } else if (data === 'ERROR') {
                    makeNotification('Unable to Complete your Profile.', '#C51F1F');
                } else {
                    if (data === 'NAME:NULL') {
                        $('#serviceFormName').focus();
                        makeNotification('Cab Service Provider Name must be Provided !');
                    } else if (data === 'AREA:FORMAT') {
                        $('#coverage').focus();
                        makeNotification('Please Enter Valid Number of Kilometers.(Coverage Area) !');
                    } else if (data === 'DISTANCE:FORMAT') {
                        $('#serviceFormCo').focus();
                        makeNotification('Please Enter Valid Number for Distance in Kilometers !');
                    } else if (data === 'COST:FORMAT') {
                        makeNotification('Please Enter Valid Number for Cost Per Kilometer !');
                    } else if (data === 'ADDRESS1:NULL') {
                        makeNotification('Your Address is Required !');
                    } else if (data === 'ADDRESS2:NULL') {
                        makeNotification('Your Address is Required !');
                    } else if (data === 'LAT:NULL') {
                        makeNotification('Your Location is Required !');
                    } else if (data === 'LNG:NULL') {
                        makeNotification('Your Location is Required !');
                    } else if (data === 'CITY:NULL') {
                        makeNotification('Your City is Required !');
                    } else if (data === 'TEL:NULL') {
                        makeNotification('Your Telephone No is Required !');
                    } else if (data === 'TEL:FORMAT') {
                        makeNotification('Please Enter Valid Telephone No !');
                    } else if (data === 'PASSWORD:NULL') {
                        makeNotification('Please Enter New Password !');
                    } else if (data === 'PASSWORD:FORMAT') {
                        makeNotification('Password must be at least 6 Characters with Uppercase Character , Lowercase Character & Number !');
                    }
                }
            }, complete: function(e) {
                $('#serviceFormPassword').val('');
                $(wait).modal('hide');
            }, error: function() {
                makeNotification('Unable to Reach.', '#C51F1F');
            }
        };
        $('#complete-form').ajaxForm(options);
//        map start
        var mapOptions = {
            center: new google.maps.LatLng(7.518119048327208, 79.99079693108797),
            zoom: 12
        };
        var map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
        var input = (document.getElementById('pac-input'));
        var autocomplete = new google.maps.places.Autocomplete(input);
        autocomplete.bindTo('bounds', map);
        var infowindow = new google.maps.InfoWindow();
        var marker = new google.maps.Marker({
            map: map,
            anchorPoint: new google.maps.Point(0, -29),
            draggable: true,
            animation: google.maps.Animation.DROP,
            icon: {url: 'images/marker/map-marker.png'}
        });
        google.maps.event.addListener(map, 'click', function(evt) {
            lat = evt.latLng.k;
            lng = evt.latLng.D;
            marker.setPosition(evt.latLng);
            infowindow.close();
        });
        google.maps.event.addListener(autocomplete, 'place_changed', function() {
            infowindow.close();
            marker.setVisible(false);
            var place = autocomplete.getPlace();
            if (!place.geometry) {
                return;
            }

            // If the place has a geometry, then present it on a map.
            if (place.geometry.viewport) {
                map.fitBounds(place.geometry.viewport);
            } else {
                map.setCenter(place.geometry.location);
                map.setZoom(17); // Why 17? Because it looks good.
            }
            marker.setPosition(place.geometry.location);
            marker.setVisible(true);
            var address = '';
            if (place.address_components) {
                address = [
                    (place.address_components[0] && place.address_components[0].short_name || ''),
                    (place.address_components[1] && place.address_components[1].short_name || ''),
                    (place.address_components[2] && place.address_components[2].short_name || '')
                ].join(' ');
            }

            infowindow.setContent('<div><strong>' + place.name + '</strong><br>' + address);
            infowindow.open(map, marker);
        });
//        map end

        $('#city').keyup(function() {
            if ($(this).val().length > 2) {
                $.post('FindCity', {term: $('#city').val()}, function(data, textStatus, jqXHR) {
                    if (textStatus === 'success') {
                        if (data !== 'NO_RES') {
                            $('#city-result').html(data);
                            $('#city-result').slideDown();
                        }
                    }
                }, 'text');
            } else {
                $('#city-result').html('');
                $('#city-result').slideUp();
            }

        });
        $('#select-from-map').on('shown.bs.modal', function() {
            google.maps.event.trigger(map, "resize");
        });
    });
</script>