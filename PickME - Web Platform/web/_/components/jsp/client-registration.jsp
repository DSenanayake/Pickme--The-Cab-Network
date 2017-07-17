<div id="clientRegContainer">
    <h2 class="page-header">Register as a Client <small>It's Free</small></h2>

    <form id="clientRegForm" class="form-horizontal" method="post" action="RegisterClient">
        <legend>Personal Details</legend>
        <div class="form-group">
            <label for="clientFirstname" class="col-sm-2 control-label">First Name:</label>
            <div class="col-sm-10">
                <input id="clientFirstname" pattern="[a-zA-Z]+" title="Letters Only - Eg : John" type="text" name="clientFirstname" placeholder="Firstname" class="form-control" required />				
            </div>
        </div><!-- firstname -->

        <div class="form-group">
            <label for="clientLastname" class="col-sm-2 control-label">Last Name:</label>
            <div class="col-sm-10">
                <input id="clientLastname" pattern="[a-zA-Z]+" title="Letters Only - Eg : Davidson" type="text" name="clientLastname" placeholder="Lastname" class="form-control" required />				
            </div>
        </div><!-- lastname -->

        <div class="form-group">
            <label for="clientGender" class="col-sm-2 control-label">Gender:</label>
            <div class="col-sm-10">
                <input id="male" type="radio" name="clientGender" value="1" checked/> <label for="male">Male</label>
                <input id="female" type="radio" name="clientGender" value="2" /> <label for="female">Female</label>
            </div>
        </div><!-- gender -->

        <div class="form-group">
            <label for="clientCity" class="col-sm-2 control-label">City:</label>
            <div class="col-sm-10">
                <input type="hidden" id="clientCity" name="clientCity" value="NOT_SELECTED"/>
                <a id="ccBtn" onclick="hidePopover(this)" data-toggle="modal" href="#searchCityDlg" class="btn btn-default btn-block"><span aria-hidden="true" class="glyphicon glyphicon-map-marker"></span> <span id="displayClientCity">Select</span></a>
            </div>
        </div><!-- city -->

        <div class="form-group">
            <label for="clientMobile" class="col-sm-2 control-label">Mobile:</label>
            <div class="col-sm-10">
                <div class="input-group">
                    <div class="input-group-addon">
                        <span>+94</span>
                    </div>
                    <input id="clientMobile" max="9" pattern="\d{9}" title="Eg : (+94)123456789" type="tel" name="clientMobile" placeholder="Mobile" class="form-control" required />				
                </div>
            </div>
        </div><!-- mobile -->

        <legend>Loggin Details</legend>
        <div class="form-group">
            <label for="email" class="col-sm-2 control-label">E-Mail:</label>
            <div class="col-sm-10">
                <a id="email-wrapper">
                    <input id="email" type="email" name="email" placeholder="E-Mail" class="form-control" required />
                </a>
            </div>
        </div><!-- email -->

        <div class="form-group">
            <label for="password" class="col-sm-2 control-label">Password:</label>
            <div class="col-sm-10">
                <div class="input-group">
                    <input id="password" pattern="(?=^.{6,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$" title="Password length must be at least 6, Include Uppercase,Lowercase and Number or Special Character" type="password" name="password" placeholder="Password" value="" class="form-control" required />
                    <span class="input-group-btn">
                        <button id="client-pw-toggle" type="button" class="btn btn-default" title="Show/Hide Password" data-toggle="tooltip" data-attach-to="#password"><span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span></button>
                    </span>
                </div>				
            </div>
        </div><!-- password -->

        <div class="form-group">
            <div class="col-sm-10 col-sm-offset-2">
                <label class="control-label" for="clientAgreement">
                    <input type="checkbox" name="clientAgreement" id="clientAgreement" required="" title="Please Agree with Terms & Conditions"/>
                    I Accept the
                </label>
                <a href="#" onclick="showTermsAndCond()">Terms & Conditions.</a>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-10 col-sm-offset-2">
                <hr>
                <div id="clientLoader" class="alert alert-info" style="display: none">
                    <img src="images/other/cube.gif" width="32px">
                    <label> Please wait...</label>
                </div>
                <button id="clientRegisterBtn" type="submit" class="btn btn-primary pull-right" onclick="return validateFields()" >Register <span class="glyphicon glyphicon-ok" aria-hidden="true"></span></button>
            </div>
        </div>
    </form>

    <div id="searchCityDlg" class="modal fade">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <button aria-label="Close" data-dismiss="modal" class="close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Search Your City</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <input type="text" class="form-control" id="clientCityInput" placeholder="Find City..."/>
                    </div>
                    <div class="form-group" style="display: none" id="clientCityLoader">
                        <img class="center-block" width="32px" src="images/other/cube.gif">
                    </div>
                    <ul id="modalSearchContent" class="list-group"></ul>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript">

        function setClientCity(clientCity, cityDesc) {
            $('#searchCityDlg').modal('hide');
            $('#clientCity').val(clientCity);
            $('#displayClientCity').html(cityDesc);
        }

        function validateFields() {
            if ($('#clientCity').val() !== "NOT_SELECTED") {
                return true;
            } else {
                makePopover('#ccBtn','Not Selected !','<label class="label label-danger">Please Select Your City !</label>');
                return false;
            }
        }

        function CityLoader(bo) {
            if (bo) {
                $('#clientCityLoader').fadeIn();
            } else {
                $('#clientCityLoader').fadeOut('fast');
            }
        }

        $(function() {

            $('#clientCityInput').keyup(function() {
                $('#modalSearchContent').html('');
                if ($(this).val()) {
                    CityLoader(true);
                    setTimeout(getClientCities, 500);
                } else {
                    CityLoader(false);
                    $('#modalSearchContent').html('');
                }
            });

            function getClientCities() {
                $.get("Places.jsp?input=" + $('#clientCityInput').val(), '', function(data, textStatus, jqXHR) {
                    var html = '';
                    if (textStatus === "success") {
                        if (data.status === "OK") {
                            var obj = data.predictions;
                            for (var i = 0; i < obj.length; i++) {
                                html += ('<a onclick="setClientCity(\'' + obj[i].place_id + '\',\'' + obj[i].terms[0].value + '\')" href="#' + obj[i].terms[0].value + '" class="list-group-item list-group-item-info"><h4 class="list-group-item-title">' + obj[i].terms[0].value + '</h4><p>' + obj[i].terms[1].value + '</p></a>');
                            }
                        } else if (data.status === "ZERO_RESULTS") {
                            html = '<p class="text text-warning"><span class="glyphicon glyphicon-exclamation-sign"></span> Cannot Find your city !</p>';
                        } else {
                            html = '<p class="text text-danger"><span class="glyphicon glyphicon-remove"></span> Error while Loading Cities...!</p>';
                        }
                    } else {
                        html = '<p class="text text-danger">Error while Loading Cities...!</p>';
                    }
                    CityLoader(false);
                    $('#modalSearchContent').html(html);
                }, 'json');
            }

            //            FORM SUBMIT
            var options = {
                beforeSend: function() {
                    $('#clientRegisterBtn').prop('disabled', true);
                    $('#clientLoader').show();
                },
                uploadProgress: function(event, position, total, percentComplete) {

                },
                success: function(data) {
                    if (data === "OK") {
                        confirmEmail();
                    } else if (data === "EXIST") {
                        $('#password').val('');
                        showExistDialog();
                    } else if (data === "ERROR") {
                        bootbox.alert('Sorry, Error while Registering User, We\'ll fix this issue as soon as posible !');
                    }
                },
                complete: function(response) {
                    $('#clientRegisterBtn').prop('disabled', false);
                    $('#clientLoader').hide();
                    $('#password').val('');
                },
                error: function() {
                    bootbox.alert('Sorry, Error while Registering User, We\'ll fix this issue as soon as posible !');
                }
            };
            $('#clientRegForm').ajaxForm(options);

            function showExistDialog() {
                var options = {
                    title: '<b>Already Exist !</b> <button id="popoverClose" class="btn close">&times;</button>',
                    html: true,
                    placement: 'bottom',
                    container: 'body',
                    trigger: 'manual',
                    content: function() {
                        return '<div class="text-danger"><b class="label label-danger">' + $('#email').val() +
                                '</b> is already exist, Please try another email or if you fogot your password please <a href="resetPassword.jsp">Reset</a> your password.'
                                + '</div>';
                    }
                };
                $('#email').popover(options);
                $('#email').popover('show');
                $('#popoverClose').click(function() {
                    $('#email').popover('hide');
                });
            }

            function confirmEmail() {
                var email = $('#email').val();
                $('#clientRegContainer').html('<h2 class="page-header">Activate Your Account <small>Confirm E-Mail</small></h2> <div class="alert alert-info">Your account has been created. We sent confirmation email to <b class="text-primary">' + email + '</b> Please Confirm your email to Activate your account ! </div>');
            }

        });
    </script>
</div>