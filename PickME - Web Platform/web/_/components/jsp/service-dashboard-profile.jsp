<script type="text/javascript">
    function setTempProfile() {
        var filename = $('#profile-img').val().split('\\')[$('#profile-img').val().split('\\').length - 1];
        $('#temp-img-show').html('<h3 class="text text-info">' + filename + '</h3>');
    }
    $(document).ready(function() {

        var options = {
            beforeSend: function() {
                $('#temp-img-show').html(makePleaseWait().innerHTML);
            },
            uploadProgress: function(event, position, total, percentComplete) {

            },
            success: function() {

            },
            complete: function(response) {
                $('#temp-img-show').html('');
                if (response.responseText === 'OK') {
                    bootbox.alert("Your logo has been changed !");
                } else {
                    bootbox.alert("Error while changing Logo !");
                }
            },
            error: function() {

            }
        };

        $("#profile-change-form").ajaxForm(options);

    });
</script>
<h2>Service Profile</h2>
<div id="profile-view">
    <form class="form-horizontal">
        <div class="form-group"> 
            <div class="center-block" style="max-width: 150px">
                <img style="max-width: 150px;margin-bottom: 10px" class="center-block img-thumbnail img-rounded" src="images/logos/service_provider/default.png">
                <a href="#profile-change-dlg" data-toggle="modal" class="btn btn-sm btn-success center-block"><%= Model.HTML.getIcon("repeat")%> Change</a>
            </div>
        </div>

        <legend>Service Details</legend>
        <div class="form-group">
            <label class="col-sm-2 control-label">Cab Service Name</label>
            <div class="col-sm-10">
                <p class="form-control-static">Lanka Cabs</p>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label">Cost per KM</label>
            <div class="col-sm-10">
                <p class="form-control-static">Rs : 150/-</p>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label">Coverage Area</label>
            <div class="col-sm-10">
                <p class="form-control-static">10 Km</p>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label">Minimum Order Distance</label>
            <div class="col-sm-10">
                <p class="form-control-static">1 Km</p>
            </div>
        </div>

        <legend>Location Details</legend>
        <div class="form-group">
            <label class="col-sm-2 control-label">Address 1</label>
            <div class="col-sm-10">
                <p class="form-control-static">No 1</p>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label">Address 2</label>
            <div class="col-sm-10">
                <p class="form-control-static">Main Road</p>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label">Latitude</label>
            <div class="col-sm-10">
                <p class="form-control-static">79.5556825151</p>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label">Longitude</label>
            <div class="col-sm-10">
                <p class="form-control-static">7.5556825151</p>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label">City</label>
            <div class="col-sm-10">
                <p class="form-control-static">Kuliyapitiya</p>
            </div>
        </div>

        <legend>Contact Details</legend>
        <div class="form-group">
            <label class="col-sm-2 control-label">Tel 1</label>
            <div class="col-sm-10">
                <p class="form-control-static">+94789654123</p>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label">Tel 2</label>
            <div class="col-sm-10">
                <p class="form-control-static">+94789654123</p>
            </div>
        </div>

        <legend>Login Details</legend>
        <div class="form-group">
            <label class="col-sm-2 control-label">Email</label>
            <div class="col-sm-10">
                <p class="form-control-static">cab_service@pickme.lk</p>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label">Password</label>
            <div class="col-sm-10">
                <p class="form-control-static">&bull;&bull;&bull;&bull;&bull;&bull;&bull;&bull;</p>
            </div>
        </div>

    </form>
</div>

<div id="profile-edit" style="display: none">
    <form class="form-horizontal">
        <legend>Logo</legend>
        <div>
            <div class="form-group" style="overflow:hidden">
                <div class="center-block">
                    <img src="images/logos/default.png" alt="Cab Service Logo" title="Click to Change" data-toggle="tooptip" width="100%" style="max-width:250px;" class="thumbnail center-block">
                    <input type="file" name="serviceFormLogo" id="serviceFormLogo" title="Click to Change" />
                    <div style="text-align:center;max-width:150px;" class="label label-success center-block">Click to Change</div>
                </div>
            </div>
        </div>
        <br/>
        <legend>Service Details</legend>
        <div class="form-group">
            <label for="serviceFormName" class="col-sm-3">Cab Service Name <span class="pull-right">:</span></label>
            <div class="col-sm-9">
                <input type="text" id="serviceFormName" name="serviceFormName" placeholder="Cab Service Name" class="form-control" value="ABC Cab Service" required />
            </div>
        </div><!-- service name -->

        <div class="form-group">
            <label for="serviceName" class="col-sm-3">Coverage Area <span  title="Coverage Area in KM from your current Service Location" data-toggle="tooltip" class="glyphicon glyphicon-info-sign" aria-hidden="true"></span> <span class="pull-right">:</span></label>
            <div class="col-sm-9">
                <div class="input-group">
                    <input type="text" id="serviceName" name="serviceName" placeholder="eg : 10 Km" class="form-control" required />
                    <div class="input-group-addon">KM</div>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label for="serviceName" class="col-sm-3">Minimum Order Distance <span class="pull-right">:</span></label>
            <div class="col-sm-9">
                <div class="input-group">
                    <input type="text" id="serviceName" name="serviceName" placeholder="eg : 1 Km" class="form-control" required />
                    <div class="input-group-addon">KM</div>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label for="serviceName" class="col-sm-3">Cost per KM <span class="pull-right">:</span></label>
            <div class="col-sm-9">
                <div class="input-group">
                    <div class="input-group-addon">Rs:</div>
                    <input type="text" id="serviceName" name="serviceName" placeholder="Cost per KM" class="form-control" required />
                    <div class="input-group-addon">.00</div>
                </div>
            </div>
        </div>

        <legend>Location Details</legend>
        <div class="form-group">
            <label for="serviceFormAddress1" class="col-sm-3">Address 1 <span class="pull-right">:</span></label>
            <div class="col-sm-9">
                <input type="text" id="serviceFormAddress1" name="serviceFormAddress1" placeholder="Address 1" class="form-control" required />
            </div>
        </div><!-- address 1 -->

        <div class="form-group">
            <label for="serviceFormAddress2" class="col-sm-3">Address 2 <span class="pull-right">:</span></label>
            <div class="col-sm-9">
                <input type="text" id="serviceFormAddress2" name="serviceFormAddress2" placeholder="Address 2" class="form-control" required />
            </div>
        </div><!-- address 2 -->

        <div class="form-group">
            <label for="serviceFormAddress2" class="col-sm-3">Location <span class="pull-right">:</span></label>

            <div class="col-sm-3 little-space">
                <button class="btn btn-default btn-block">Select from Map <span class="glyphicon glyphicon-map-marker" aria-hidden="true"></span></button>
            </div>
            <div class="col-sm-3 little-space">
                <input type="text" id="serviceFormLong" name="serviceFormLong" placeholder="Logitude" class="form-control" required />
            </div>
            <div class="col-sm-3 little-space">
                <input type="text" id="serviceFormLat" name="serviceFormLat" placeholder="Lattitude" class="form-control" required />
            </div>
        </div><!-- location -->

        <div class="form-group">
            <label for="serviceFromCity" class="col-sm-3">City <span class="pull-right">:</span></label>
            <div class="col-sm-9">
                <select id="serviceFromCity" name="serviceFromCity" class="form-control">
                    <option value="not_selected">Select</option>
                    <option value="kurunegala">Kurunegala</option>
                    <option value="narammala">Narammala</option>
                    <option value="hettipola">Hettipola</option>
                    <option value="kulipitiya">Kuliyapitiya</option>
                </select>
            </div>
        </div><!-- city -->

        <legend>Contact Details</legend>
        <div class="form-group">
            <label for="serviceFormTel1" class="col-sm-3">Tel 1 <span class="pull-right">:</span></label>
            <div class="col-sm-9">
                <div class="input-group">
                    <div class="input-group-addon">
                        <span class="glyphicon glyphicon-phone-alt" aria-hidden="true"></span>
                    </div>
                    <input type="tel" id="serviceFormTel1" name="serviceFormTel1" placeholder="Tel 1" class="form-control" required />
                </div>
            </div>
        </div>

        <div class="form-group">
            <label for="serviceFormTel2" class="col-sm-3">Tel 2 <span class="pull-right">:</span></label>
            <div class="col-sm-9">
                <div class="input-group">
                    <div class="input-group-addon">
                        <span class="glyphicon glyphicon-phone-alt" aria-hidden="true"></span>
                    </div>
                    <input type="tel" id="serviceFormTel2" name="serviceFormTel2" placeholder="Tel 2" class="form-control" required />
                </div>
            </div>
        </div>

        <legend>Logging Details</legend>
        <div class="form-group">
            <label for="email" class="col-sm-3">E-Mail <span class="pull-right">:</span></label>
            <div class="col-sm-9">
                <p>abc@mail.com</p>	
            </div>
        </div><!-- email -->

        <div class="form-group">
            <label for="serviceFormPassword" class="col-sm-3">Password <span class="pull-right">:</span></label>
            <div class="col-sm-9">
                <div class="input-group">
                    <input id="serviceFormPassword" type="password" name="serviceFormPassword" placeholder="Password" class="form-control" required />
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

<div class="modal fade" id="profile-change-dlg">
    <div class="modal-dialog">
        <div class="modal-content">
            <form id="profile-change-form" action="UpdateProfilePic" enctype="multipart/form-data" method="post">
                <div class="modal-header">
                    <button class="btn close" data-dismiss="modal">&times;</button>
                    <h3 class="modal-title">Change Profile Logo</h3>
                </div>
                <div class="modal-body">
                    <span id="temp-img-show"></span>
                    <div class="center-block" style="margin: 0 auto">
                        <input id="profile-img" onchange="setTempProfile()" title="Choose File" data-toggle="tooltip" type="file" name="url" style="
                               position: absolute;
                               overflow: hidden;
                               height: 50px;
                               width: 95px;
                               opacity: 0;
                               cursor: pointer;
                               "/>
                        <button type="button" class="btn btn-lg btn-primary">
                            Browse
                        </button>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-info">Save</button>
                </div>
            </form>
        </div>
    </div>
</div>