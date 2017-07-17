<div>
    <h3 class="page-header">Find Cab Services by City</h3>


    <input type="text" id="find-city-input" placeholder="Find your City" class="form-control little-space" />

    <ul id="city-result" style="display: none" class="list-group">
    </ul>

    <script type="text/javascript">
        function setTempCity(id, label) {
            document.location.href = "findServices.jsp?city=" + id + "&city_label=" + label;
        }
        $(function() {
            $('#find-city-input').keyup(function() {
                if ($(this).val().length > 2) {
                    $.post('FindCity', {term: $('#find-city-input').val()}, function(data, textStatus, jqXHR) {
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
        });
    </script>

</div>