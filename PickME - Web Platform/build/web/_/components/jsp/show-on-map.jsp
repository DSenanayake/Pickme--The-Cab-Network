<div id="show-on-map" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title">Show on Map <button class="btn close" data-dismiss='modal'>&times;</button></h3>
            </div>
            <div class="modal-body">
                <div id="temp-map" style="height: 400px"></div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    function showOnMap(lat, lng) {

        $('#show-on-map').modal();

        $('#show-on-map').on('shown.bs.modal', function() {
            // google.maps.event.trigger(map, "resize");
            var myLatLng = new google.maps.LatLng(lat, lng);
            console.log(myLatLng);
            var mapOptions = {
                zoom: 17,
                center: myLatLng
            };
            var map = new google.maps.Map(document.getElementById('temp-map'), mapOptions);
            var mapMarker = new google.maps.Marker({
                position: myLatLng,
                map: map
            });
            var info = new google.maps.InfoWindow({
                content: 'Location'
            });
            google.maps.event.addListener(mapMarker, 'click', function() {
                info.open(map, mapMarker);
            });
            info.open(map, mapMarker);
            google.maps.event.trigger(map, "resize");
        });
    }

//    $(function() {
//        $('#show-on-map').on('shown.bs.modal', function() {
//            alert('ok');
//        });
//    });

</script>