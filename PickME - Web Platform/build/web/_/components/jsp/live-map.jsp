<div>
    <div id="live-map" style="top: 0;left: 0;height: 500px"></div>
</div>
<script type="text/javascript">
    var live_map;
    var home_lat, home_lng;
    var sp_id;
    var markers = [];
    var infos = [];

    function getUpdates(firsttime) {
        $.post('GetLiveMapUpdates', {}, function(data, textStatus, jqXHR) {
            if (textStatus === 'success') {
                var status = data[0].status;

                if (status === 'OK') {
                    home_lat = data[0].lat;
                    home_lng = data[0].lng;
                    sp_id = data[0].id;


                    initialize(home_lat, home_lng);

                    placeCabs();
                    if (firsttime) {
                        setInterval(placeCabs, 5000);
                    }
                } else if (status === 'NO_LOG') {
                    document.location.href = "userLogin.jsp";
                } else {
                    makeNotification('Something went Wrong !.', 'tomato');
                }
            }
        }, 'json');
    }

    function placeCabs() {
        $.post('GetCabLocations', {id: sp_id}, function(data, textStatus, jqXHR) {
            if (textStatus === 'success') {
                deleteMarkers();
                removeInfos();
                for (var i = 0; i < data.length; i++) {
                    var myLatlng = new google.maps.LatLng(data[i].lat, data[i].lng);
                    addMarker(myLatlng, data[i]);
                }
            }
        }, 'json');
    }

    // Add a marker to the map and push to the array.
    function addMarker(location, data) {
        var marker = new google.maps.Marker({
            position: location,
            map: live_map,
            icon: {url: 'images/marker/driver.png'}
        });
        addInfo(marker, data);
        markers.push(marker);
    }

    function removeInfos() {
        for (var i = 0; i < infos.length; i++) {
            $(infos[i]).remove();
        }
    }

    function addInfo(marker, data) {
        var infowindow = new google.maps.InfoWindow({
            content: '<h5><img src="' + data.pic + '" width="32px"> ' + data.label + '</h5><p>' + data.mobile + '</p>'
        });

        google.maps.event.addListener(marker, 'click', function() {
            infowindow.open(live_map, marker);
        });

        infos.push(infowindow);
    }

// Sets the map on all markers in the array.
    function setAllMap(map) {
        for (var i = 0; i < markers.length; i++) {
            markers[i].setMap(map);
        }
    }

// Removes the markers from the map, but keeps them in the array.
    function clearMarkers() {
        setAllMap(null);
    }

// Shows any markers currently in the array.
    function showMarkers() {
        setAllMap(live_map);
    }

// Deletes all markers in the array by removing references to them.
    function deleteMarkers() {
        clearMarkers();
        markers = [];
    }

    function initialize(lat, lng) {
        var myLatlng = new google.maps.LatLng(lat, lng);
        var mapOptions = {
            zoom: 12,
            center: myLatlng
        };
        live_map = new google.maps.Map(document.getElementById('live-map'), mapOptions);

        var marker = new google.maps.Marker({
            position: myLatlng,
            map: live_map,
            title: 'Your Location',
            icon: {url: 'images/marker/company.png'}
        });
    }

</script>