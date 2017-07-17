<div>
    <div style="margin-top: 20px;" class="panel panel-primary">

        <div class="panel-heading">
            <h3 class="panel-title">Search Options</h3>
        </div>

        <div class="panel-body">

            <div class="form-group">
                <label class="control-label">Keywords</label>
                <input type="text" placeholder="Cab Service Name" id="filter-keywords" class="form-control" />
            </div>
            <hr/>
            <div class="form-group">
                <label class="control-label">Filter by City</label>
                <input id="city" type="text" placeholder="Enter Your City" class="form-control" data-list="Ada, Java, JavaScript, Brainfuck, LOLCODE, Node.js, Ruby on Rails" />
                <ul id="city-result" class="list-group" style="display: none;margin-top: 5px;position: relative;left: 0;"></ul>
            </div>
            <hr/>
            <legend>Filter By Service</legend>
            <div class="form-group">
                <div data-role="rangeslider">
                    <label style="display: block" class="control-label">Coverage Area (<span id="min-area">0</span>KM - <span id="max-area">100</span>KM)</label>
                    <div id="coverage-show"></div>
                </div>
            </div>
            <div class="form-group">
                <label style="display: block" class="control-label">Cost Per KM (Rs <span id="min-cost">0</span> - Rs <span id="max-cost">1000</span>)</label>
                <div id="cost-show"></div>
            </div>
            <div class="form-group">
                <label style="display: block" class="control-label">Minimum Order Distance (<span id="min-dist">0</span>KM - <span id="max-dist">100</span>KM)</label>
                <div id="min-dist-show"></div>
            </div>
            <hr/>
            <legend>Filter by Cabs</legend>
            <div class="form-group">
                <label style="display: block" class="control-label">Registered Cabs (<span id="min-cabs">0</span> - <span id="max-cabs">50</span>)</label>
                <div id="cabs"></div>
            </div>
            <div class="form-group">
                <label>Vehicle Make</label>
                <select class="form-control" id="veh-make">
                    <option value="-1">Any</option>
                </select>
            </div>
            <div class="form-group">
                <label>Vehicle Model</label>
                <select class="form-control" id="veh-model">
                    <option value="-1">Any</option>
                </select>
            </div>
            <hr/>
            <div class="form-group">
                <button onclick="filterServices()" class="btn btn-lg btn-info">Update Result</button>
            </div>
        </div>        
        <div class="panel-footer">
            <span id="ser-found">0</span> Services Found.
        </div>
    </div>
</div>
<script type="text/javascript">
    var coverage_area = 100;
    var min_dist = [0, 100];
    var cost = [0, 1000];
    var cabs = [0, 50];
    var make = -1;
    var model = -1;
    var sort = "NAME";
    var availability = "ALL";
    var keywords;
    var city;
    var start = 0;

    $(function() {
        $('#coverage-show').slider({
            min: 0,
            max: 100,
            value: 100
        }).on('slide', function(evt) {
            coverage_area = evt.value;
        }).on('slideStop', function(evt) {
            $('#max-area').html(evt.value);
            filterServices();
        });

        $('#min-dist-show').slider({
            min: 0,
            max: 100,
            value: [0, 100],
            range: true
        }).on('slide', function(evt) {
            min_dist = evt.value;
        }).on('slideStop', function(evt) {
            $('#min-dist').html(evt.value[0]);
            $('#max-dist').html(evt.value[1]);
            filterServices();
        });

        $('#cost-show').slider({
            min: 0,
            max: 1000,
            range: true,
            value: [0, 1000]
        }).on('slide', function(evt) {
            cost = evt.value;
        }).on('slideStop', function(evt) {
            $('#min-cost').html(evt.value[0]);
            $('#max-cost').html(evt.value[1]);
            filterServices();
        });

        $('#cabs').slider({
            min: 0,
            max: 50,
            range: true,
            value: [0, 1000]
        }).on('slide', function(evt) {
            cabs = evt.value;
        }).on('slideStop', function(evt) {
            $('#min-cabs').html(evt.value[0]);
            $('#max-cabs').html(evt.value[1]);
            filterServices();
        });

        $('#ser-availability button').click(function() {
            $('#ser-availability button').removeClass('active');
            $(this).addClass('active');
            availability = $(this).html().toUpperCase().trim();
            filterServices();
        });
        getVehicleModels(-1);
        $('#veh-make').change(function(evt) {
            getVehicleModels($(this).val());
            make = $(this).val();
            filterServices();
        });
        $('#veh-model').change(function(evt) {
            model = $(this).val();
            filterServices();
        });
        $('#filter-keywords').keyup(function() {
            keywords = $(this).val();
            if ($(this).val().length > 2) {
                filterServices();
            }
        });
        $('#sort-by').change(function() {
            sort = $(this).val();
            filterServices();
        });
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
        $.post('GetVehicleMakes', {}, function(data, textStatus, jqXHR) {
            if (textStatus === 'success') {
                $('#veh-make').html(data);
            }
        }, 'text');
    });
    function getVehicleModels(make) {
        $.post('GetVehicleModels', {make: make}, function(data, textStatus, jqXHR) {
            if (textStatus === 'success') {
                $('#veh-model').html(data);
            }
        }, 'text');
    }
    function setTempCity(id, label) {
        city = id;
        $('#city').val(label);
        $('#city-result').html('Selected : <label>' + label + ' <a style="cursor:pointer" onclick="clearCity()">Remove</a></label>');
        $('#city-result').fadeIn();
//        $('#city-result').slideUp();
        filterServices();
    }
    function setStart(s) {
        start = s;
        filterServices();
    }
    function clearCity() {
        city = '';
        $('#city').val('');
        $('#city-result').html('');
        $('#city-result').slideUp();
        filterServices();
    }
    function filterServices() {
        $('#filter-result').html(makePleaseWait().innerHTML);
        $.post('FilterServices',
                {
                    sort: sort,
                    availability: availability,
                    keywords: keywords,
                    city: city,
                    area_max: coverage_area,
                    cost_min: cost[0],
                    cost_max: cost[1],
                    dist_min: min_dist[0],
                    dist_max: min_dist[1],
                    cabs_min: cabs[0],
                    cabs_max: cabs[1],
                    make: make,
                    model: model,
                    start: start
                }
        , function(data, textStatus, jqXHR) {
            if (textStatus === 'success') {
//                alert('al');
                console.log(data);
                var count = data[0].count;
                var result = data[0].result;
                var pagination = data[0].pagination;

                $('#filter-result').html(result);
                $('#ser-found').html(count);
                $('#result-pagination').html(pagination);
            }
        }, 'json');
    }
</script>