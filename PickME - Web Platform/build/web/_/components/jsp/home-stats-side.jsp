<div>
    <h3 class="page-header">Statistics <small>Current status</small></h3>

    <!-- <div class="row"> -->

    <ul class="list-group">
        <li class="list-group-item">
            <div class="row">
                <div class="col-xs-4">
                    <canvas id="servicesOnline"  width="100%" height="100%"></canvas>
                </div>
                <div class="col-xs-7 col-xs-offset-1">
                    <p style="border-bottom: 1px solid lightgray;padding-bottom: 7px;line-height: 30px"><label class="label label-success" style="background-color: #009999;font-size: 18px" id="on_c">35</label> Cabs Online for <b>you</b></p>
                    <p><label class="label label-danger" style="background-color: #99b7b7" id="in_c" >65</label> Cabs in a Service</p>
                </div>
            </div>
        </li>
        <li class="list-group-item">
            <label class="label label-primary" id="on_s">204</label> Cabs Services Online.
        </li>
        <li class="list-group-item">
            <label class="label label-info" id="reg_s" >266</label> Registered Cab Services from <label class="label label-success" id="c">136</label> Cities
        </li>
        <li class="list-group-item">
            <label class="label label-warning" id="reg_c">602</label> Cabs <b>registered</b> under <label class="label label-info" id="cs">266</label> Cab services
        </li>
    </ul>
    <!-- </div> -->

    <script type="text/javascript">
        var myDoughnut;

        function getUpdates() {
            $.post('GetStat', {}, function(data, textStatus, jqXHR) {
                if (textStatus === 'success') {
                    $('#on_c').html(data[0].on_c ? data[0].on_c : 0);
                    $('#in_c').html(data[0].in_c ? data[0].in_c : 0);
                    $('#on_s').html(data[0].on_s ? data[0].on_s : 0);
                    $('#reg_s').html(data[0].reg_s ? data[0].reg_s : 0);
                    $('#reg_c').html(data[0].reg_c ? data[0].reg_c : 0);
                    $('#c').html(data[0].c ? data[0].c : 0);
                    $('#cs').html(data[0].reg_s ? data[0].reg_s : 0);

                    console.log(data[0].on_c);
                    console.log(data[0].in_c);

                    setStat(data[0].on_c, data[0].in_c);
                }
            }, 'json');
        }


        function setStat(online, inA) {
            //home stat chart
            var pieData = [
                {
                    value: online ? online : 0,
                    color: "#009999",
                    label: "Online"
                },
                {
                    value: inA ? inA : 0,
                    color: "#99b7b7",
                    label: "In Service"
                }
            ];

            var pieOptions = {
                segmentShowStroke: false,
                animateScale: true,
                animateRotate: true,
                animationEasing: "easeOutQuart",
                animationSteps: 60,
                percentageInnerCutout: 85,
                showTooltips: false
            };

            var onSevices = $('#servicesOnline').get(0).getContext("2d");
            myDoughnut = new Chart(onSevices).Doughnut(pieData, pieOptions);
        }

        $(function() {
            getUpdates();
        });
    </script>
</div>