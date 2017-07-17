var xmlhttp;
var myDoughnut;
var memberType = "client";
var pwToggle = true;
var m_plan, m_plan_name, m_duration, s_name, s_email, s_tel, s_exist = true;
var c_city_id, c_latLng, c_dest;
var usableServices = new Array();
var no_pay = false;
var no_membership = false;

//<editor-fold defaultstate="collapsed" desc="Sign Form Options">
var signInOptions = {
    beforeSend: function() {
        $('#signInBtn').prop('disabled', true);
        $('#signInLoader').fadeIn();
        $('#signInMsg').fadeOut('fast');
        $('#signInMsg').html('');
    },
    uploadProgress: function(event, position, total, percentComplete) {
        console.log(event);
        console.log(position);
        console.log(total);
        console.log(percentComplete);
    }, success: function(data) {
        data = data.trim();
        var msg = $('#signInMsg');
        $(msg).prop('class', 'alert');
        if (data === "OK") {
            msg.html('Redirecting...');
            msg.addClass('alert-info');
            setTimeout(function() {
                document.location.href = "CurrentTask";
            }, 1000);
        } else if (data === "NOT_CONFIRMED") {
            msg.html('<b>Please Confirm you Email.Redirecting..</b>');
            $(msg).addClass('alert-warning');
            setTimeout(function() {
                document.location.href = "UserConfirmation.jsp";
            }, 2000);
        } else if (data === "DELETED") {
            msg.html('<b>Sorry, You\'r not associate with Pickme.lk with anymore.</b>');
            $(msg).addClass('alert-danger');
        } else {
            msg.html('Sorry, Please check your Email/Password Again.');
            $(msg).addClass('alert-danger');
        }
        $(msg).fadeIn('fast');
    }, complete: function() {
        $('#signInBtn').prop('disabled', false);
        $('#signInLoader').fadeOut();
    }, error: function() {
        bootbox.alert('<h4 class="text text-danger">Sorry Error while Login ! Please try again Later</h4>');
    }
};
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Get City Name by Place_ID">
function getCityNames() {
    var ccc = $('.google-city');
    var service = new google.maps.places.PlacesService();
    for (var i = 0; i < ccc.length; i++) {
        var g_id = ccc[i];
        console.log(ccc[i]);
        var request = {
            placeid: function() {
                return g_id.innerHTML;
            }
        };
        service.getDetails(request, function(place, status) {
            if (status === google.maps.places.PlacesServiceStatus.OK) {
                //                g_id.innerHTML = place.name;
            } else {
                //                g_id.innerHTML = "Cannot Find !";
            }
        });
    }
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Reset Password">
function resetPassword(email) {
    if (email) {
        var bo = isValidEmail(email);
        if (bo) {
            var wait = makePleaseWait();
            $('#formContainer').append(wait);
            $(wait).hide();
            $('#resetForm').fadeOut('fast', function() {
                $(wait).fadeIn();
            });
            $.post('ResetPassword', {email: email}, function(data, textStatus, jqXHR) {
                if (textStatus === "success") {
                    data = data.trim();
                    if (data === "SENT") {
                        bootbox.alert('<h4 class="text text-info"><b>Success</b>, Your current password has been sent to yout Email. Please Check you inbox.</h4>');
                    } else if (data === "NOT_EXIST") {
                        bootbox.alert('<h4 class="text text-danger"><b>Sorry</b>, You\'r not associated with Pickme.lk.</h4>');
                    } else if (data === "ERROR") {
                        bootbox.alert('<h4 class="text text-warning"><b>Sorry</b>, Error while Resetting your password.</h4>');
                    }
                } else {
                    bootbox.alert('<h4 class="text text-warning"><b>Sorry</b>, Error while Resetting your password.</h4>');
                }

                $(wait).fadeOut('fast', function() {
                    $('#resetForm').fadeIn();
                    $(wait).remove();
                });
            }, 'text');
        } else {
            bootbox.alert('<h4 class="text text-warning">Please enter valid <b>Email !</b></h4>');
        }
    } else {
        bootbox.alert('<h4 class="text text-warning">Please enter your <b>Email !</b></h4>');
    }
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Temp Login">
function loginUser() {
    var email = $('.sign-in-uname').val();
    var pword = $('#signInPword').val();
    alert(email + " " + pword);
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Make Please wait Object">
function makePleaseWait() {
    var mainc = document.createElement("div");
    var loader = document.createElement("div");
    var loaderImg = document.createElement("img");
    $(loader).css({display: 'block', margin: '5% auto', verticalAlign: 'middle', textAlign: 'center', padding: '15px', fontFamily: 'monospace'});
    $(loaderImg).prop('src', 'images/other/cube.gif');
    $(loaderImg).css({width: '32px', display: 'block', margin: '0 auto 15px'});
    $(loader).append(loaderImg);
    $(loader).append(document.createTextNode('Please Wait..'));
    $(mainc).append(loader);
    return mainc;
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Show Login Dialog">
function showLoginDialog(warn) {
    bootbox.hideAll();
    //    var l_dlg = $('#loginHtml').html();
    var l_dlg = '<div style="text-align:center;margin-bottom:15px"> <img style="max-width:250px;" src="images/logos/default.png" width="100%"> </div><form id="userLoginForm" action="LoginUser" method="post" style="min-width: 200px;"> <div class="form-group"> <input type="email" autofocus class="form-control sign-in-uname" name="uname" placeholder="E-Mail" required/> </div><div class="form-group" style="margin-bottom: 35px;"> <input type="password" class="form-control" name="pword" placeholder="Password" required/> <input type="checkbox" name="stay" id="stay" value="true"/> <label for="stay">Stay Logged in</label> <a href="userLogin.jsp?reset=true" class="pull-right"><small>Forget Password ?</small></a> </div><div class="form-group"> <div id="signInMsg" style="display:none" class="alert alert-info"></div></div><div class="form-group"> <button id="signInBtn" type="submit" class="btn btn-primary btn-block">Sign in <img style="display: none" id="signInLoader" src="images/other/loader4.gif"></button> </div><a href="#" data-toggle="modal" data-target="#registerModal" onclick="closePopover()">Not a Member yet ?</a> </form>';
    var b = bootbox.dialog({
        title: 'Pickme.lk <small>Login</small>',
        message: l_dlg,
        className: 'login-dialog',
        buttons: {
            "Close": {
                className: 'btn btn-default',
                callback: function() {
                    bootbox.hideAll();
                }
            }
        }
    });
    $('#userLoginForm').ajaxForm(signInOptions);
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Is Already Exist Email">
function isExistEmail(email) {
    $.post('CheckExistUser', 'email=' + email, function(data, textStatus, jqXHR) {
        if (textStatus === "success") {
            data = data.trim();
            if (data === "YES") {
                s_exist = true;
            } else if (data === "NO") {
                s_exist = false;
            } else {
                bootbox.hideAll();
                bootbox.alert('Something went wrong ! Please try again Later.');
                s_exist = true;
            }
        } else {
            bootbox.hideAll();
            bootbox.alert('Something went wrong ! Please try again Later.');
            s_exist = true;
        }
    }, 'text');
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Is Agreeded (Service)">
function isServiceAgreed() {

    var agree = $('#serviceAgreement');
    if (agree.prop('checked')) {
        hidePopover(agree);
        return true;
    } else {
        makePopover(agree, 'Terms & Conditions', '<h4 class="text-danger">You Need to Agree with Terms & Conditions.</h4>');
        return false;
    }
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Validate Tel">
function isValidTel(tel) {
    var bo = true;
    bo = tel.length === 9 & $.isNumeric(tel);
    return bo;
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Validate Contact Details">
function validateContactDetails() {
    var bo = false;
    var name = $('#serviceName');
    var email = $('#serviceEmail');
    var tel = $('#serviceTel');
    var toName = $('#groupServiceName');
    var toTel = $('#groupServiceTel');
    var toEmail = $('#groupServiceEmail');
    if (name.val()) {
        removeError(toName);
        hidePopover(name);
        if (isValidEmail(email.val())) {
            removeError(toEmail);
            hidePopover(email);
            if (isValidTel(tel.val())) {
                removeError(toTel);
                hidePopover(tel);
                bo = true;
            } else {
                doError(toTel);
                makePopover(tel, 'Invalid Telephone No !', '<h5 class="text-danger">Please Enter Valid Telephone No</h5><i>Eg:(+94)123456789</i>');
            }
        } else {
            doError(toEmail);
            makePopover(email, 'Invalid Email !', '<h5 class="text-danger">Please Enter valid Email Address</h5><i>Eg:cabs@srilanka.com</i>');
        }
    } else {
        doError(toName);
        makePopover(name, 'Empty !', '<h5 class="text-danger">Enter Your Cab Service Name</h5><i>Eg:Abc Cab Service</i>');
    }

    return bo;
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Add Error Class">
//has error
function doError(id) {
    $(id).addClass('has-error');
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Remove Error class">
//remove error
function removeError(id) {
    $(id).removeClass('has-error');
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Check isValidEmail">
//validate Email
function isValidEmail(res) {
    if (res !== null) {
        var at = res.indexOf('@');
        var l_at = res.lastIndexOf('@');
        var dot = res.lastIndexOf('.');
        var f_dot = res.indexOf('.');
        return (f_dot !== 0 & (res !== '') & (at === l_at) & (at !== -1) & (dot > at + 1 & dot !== -1) & (dot < res.length - 1));
    } else {
        return false;
    }
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Resend Confirmation Email">
//resend email
function resendEmail() {
    bootbox.prompt('Enter Email to Send Confirmation Link', function(res) {
        if (res !== null) {
            var goOn = isValidEmail(res);
            if (goOn) {
                bootbox.alert('<div style="text-align:center;"><h4><img src="images/other/cube.gif" width="32px"> Please wait...</h4></div>');
                $.post('SendConfirmationEmail', 'email=' + res, function(data, textStatus, jqXHR) {
                    var html = '';
                    bootbox.hideAll();
                    if (textStatus === 'success') {
                        if (data === "DONE") {
                            html = 'Confirmation Email Sent Successfully ! Please Check your Inbox.';
                        } else if (data === "NOT_EXIST") {
                            html = 'This Email dosen\'t Associate with Pickme.lk.Please Check Again';
                        } else {
                            html = 'Error while sending Email.! Please try again Later';
                        }
                    } else {
                        html = 'Error while sending Email.! Please try again Later';
                    }
                    bootbox.alert('<div  style="margin-top:15px;" class="well well-sm"><h4>' + html + '<h4><div>');
                }, 'text');
            } else {
                bootbox.alert('<h5 class="text-danger">Please Enter Valid Email Address.</h5>');
            }
        }
    });
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Hide Selected Popover">
//hide popover
function hidePopover(id) {
    $(id).popover('hide');
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Make Popover">
//make and show popover
function makePopover(id, title, html) {
    var options = {
        title: title + '<button data-close="' + $(id).attr('id') + '" class="btn close close-popover">&times;</button>',
        html: true,
        placement: 'right',
        container: 'body',
        content: html,
        trigger: 'manual'
    };
    $(id).popover(options);
    $(id).popover('show');
    $('.close-popover').click(function() {
        $('#' + $(this).data('close')).popover('hide');
    });
    setTimeout('hidePopover(' + $(id).attr('id') + ')', 3000);
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Close Popover">
//close Login Popover
function closePopover() {
    $('#loginButton').popover('hide');
    bootbox.hideAll();
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Go Back">
//go Back
function goBack() {
    window.history.back();
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Home Chart Staff">
//update Stat
function updateStat() {
    myDoughnut.segments[0].value = 20;
    myDoughnut.segments[1].value = 80;
    myDoughnut.update();
}

//add Data
function addData() {
    myDoughnut.addData({
        value: 15,
        color: "#B48EAD",
        label: "In Service"
    });
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Redirect User Registration">
//redirect User
function redirectUser() {
    if (memberType === "client") {
        document.location.href = "Register.jsp";
    } else if (memberType === "service") {
        document.location.href = "Register.jsp?serviceReg=true";
    }
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Change Membership Package">
//change package
function changePkg() {
    $('#regDetails').fadeOut("fast", function() {
        $('#membershipPlan').fadeIn();
    });
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Place Membership Order">
//place service order
function placeServiceOrder() {
    var bo = validateContactDetails() & isServiceAgreed();
    if (bo) {
        $(this).prop('disabled', true);
        $.post('CheckExistUser', 'email=' + $('#serviceEmail').val(), function(data, textStatus, jqXHR) {
            $(this).prop('disabled', false);
            if (textStatus === "success") {
                data = data.trim();
                if (data === "YES") {
                    doError($('#groupServiceEmail'));
                    makePopover($('#groupServiceEmail'), 'Email Already Exist !', '<h5 class="text-danger"><i><b>' + $('#serviceEmail').val() + '</b></i> is Already Registered Under Pickme.lk</h5>');
                } else if (data === "NO") {
                    s_name = $('#serviceName').val();
                    s_email = $('#serviceEmail').val();
                    s_tel = $('#serviceTel').val();
                    $('#reviewServiceName').html(s_name);
                    $('#reviewServiceEmail').html(s_email);
                    $('#reviewServiceTel').html('(+94) ' + s_tel);
                    $('#regDetails').fadeOut("fast", function() {
                        $('#reviewServiceOrder').fadeIn();
                        $('#reviewLoader').show();
                        $('#reviewTable').html('');
                        $('#checkoutServiceBtn').prop('disabled', true);
                        $.post('GenerateMembershipOrderReview', {plan: m_plan, duration: m_duration}, function(data, textStatus, jqXHR) {
                            $('#reviewLoader').hide();
                            if (textStatus === "success") {
                                data = data.trim();
                                if (data !== "NONE") {
                                    $('#reviewTable').html(data);
                                } else {
                                    $('#reviewTable').html('<i class="text text-danger text-center">Unable to Generate Your Order !</i>');
                                }
                            } else {
                                $('#reviewTable').html('<div class="alert alert-danger"><b>Error while Generating your Order, Please try again Later...</b></div>');
                            }
                            $('#checkoutServiceBtn').prop('disabled', false);
                        }, 'html');
                    });
                } else {
                    bootbox.hideAll();
                    bootbox.alert('Something went wrong ! Please try again Later.');
                }
            } else {
                bootbox.hideAll();
                bootbox.alert('Something went wrong ! Please try again Later.');
            }
        }, 'text');
    }
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Checkout Membership Order">
//CHECKOUT ORDER
function checkoutOrder() {
    $('#reviewServiceOrder').fadeOut('fast', function() {
        $('#savingOrder').fadeIn();
        createRequest();
        xmlhttp.open('post', 'SubscribeService', true);
        xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xmlhttp.send('name=' + s_name + '&email=' + s_email + '&tel=' + s_tel + '&plan_id=' + m_plan + '&dura_id=' + m_duration);
        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState === 4 & xmlhttp.status === 200) {
                var res = xmlhttp.responseText.trim();
                if (res === "OK") {
                    $('#savingOrder .alert').html('<h4>Redirecting...</h4>');
                    $('#savingOrder .alert').removeClass('alert-info');
                    $('#savingOrder .alert').addClass('alert-success');
                    setTimeout(function() {
                        document.location.href = 'http://localhost:8080/Pickme.lk/expresscheckout.jsp';
                    }, 500);
                } else {
                    $('#savingOrder .alert').html('<h4>Sorry Error while Subscribing, Please try again later...</h4>');
                    $('#savingOrder .alert').removeClass('alert-info');
                    $('#savingOrder .alert').addClass('alert-danger');
                }
            }
        };
    });
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Edit Client Reg Details">
function editDetails() {
    $('#reviewServiceOrder').fadeOut('fast', function() {
        $('#regDetails').fadeIn();
    });
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Create AJAX Request">
function createRequest() {
    if (window.XMLHttpRequest) {
        xmlhttp = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    } else {
        bootbox.alert('The Processes of these website will not working correctly !, Please Upgrade your Browser !');
    }

}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Check Availability">
function findOnMap(place_id) {
    $('#homeResultCity').html('');
    $('#homeInputCity').prop('disabled', true);
    loaderVisible(true);
    createRequest();
    xmlhttp.open('get', 'CheckServiceAvailability?id=' + place_id, true);
    xmlhttp.send();
    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === 4 & xmlhttp.status === 200) {
            var res = xmlhttp.responseText.trim();
            if (res === "AVAILABLE") {
                $('#findCityContainer').fadeOut('fast', function() {
                    loadLocationMap(place_id);
                    $('#clientLocationContainer').fadeIn();
                });
            } else if (res === "NA") {
                bootbox.alert('<div style="margin-top:35px;" class="well"><h4>Sorry, There are no Cab Services Registered around your city or <br> <b>The\'re all in </b><label class="label label-success">Services.</label></h4></div> ');
            }
            loaderVisible(false);
            $('#homeInputCity').prop('disabled', false);
        }
    };
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Get Client Location">
function loadLocationMap(place_id) {
    c_city_id = place_id;
    var map = new google.maps.Map(document.getElementById('mapContainer'), {
        center: new google.maps.LatLng(5, 79),
        zoom: 17
    });
    var request = {
        placeId: '' + place_id
    };
    var infowindow = new google.maps.InfoWindow();
    var service = new google.maps.places.PlacesService(map);
    service.getDetails(request, function(cityPlace, status) {
        if (status === google.maps.places.PlacesServiceStatus.OK) {
            map.panTo(cityPlace.geometry.location);
            c_latLng = cityPlace.geometry.location;
            console.log(cityPlace.geometry.location);
            var marker = new google.maps.Marker({
                map: map,
                position: cityPlace.geometry.location,
                icon: 'images/marker/man.png'
            });
            infowindow.setContent('<div>So! You are at <strong>' + cityPlace.name + '</strong><br><i>Please Show us your Actual Location on th Map.</i>');
            infowindow.open(map, marker);
            google.maps.event.addListener(marker, 'click', function() {
                infowindow.open(map, marker);
            });
            google.maps.event.addListener(map, 'click', function(event) {
                marker.setPosition(event.latLng);
                c_latLng = event.latLng;
                infowindow.setContent('<div>So! You are <strong>Here</strong><br><i>We\'l pick you soon after the Order</i>');
                infowindow.open(map, marker);
                validateCoverageArea();
            });
            //            setting autocomplete
            var input = (document.getElementById('placesSearcher'));
            //map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

            var autocomplete = new google.maps.places.Autocomplete(input, {
                componentRestrictions: {
                    'country': 'lk'
                }
            });
            autocomplete.bindTo('bounds', map);
            //setting litener to autocomplete
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
                    map.setZoom(18);
                }
                //                marker.setIcon(/** @type {google.maps.Icon} */({
                //                    url: 'images/marker/man.png',
                //                    size: new google.maps.Size(71, 71),
                //                    origin: new google.maps.Point(0, 0),
                //                    anchor: new google.maps.Point(17, 34),
                //                    scaledSize: new google.maps.Size(35, 35)
                //                }));
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

                infowindow.setContent('<div>So! You are at <strong>' + place.name + '</strong><br>' + address + '<br><i>We\'l pick you soon after the Order</i>');
                infowindow.open(map, marker);
                c_latLng = place.geometry.location;
                validateCoverageArea();
            });
        }
    });
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Validate Service Coverage Area">
function validateCoverageArea() {
    console.log('CL_CITY:' + c_city_id);
    $.post('GetServicesByCity', {place_id: c_city_id}, function(data, textStatus, jqXHR) {
        if (textStatus === "success") {
            if (data[0].status !== "NA" & data[0].status !== "ERROR") {
                usableServices = new Array();
                for (var i = 0; i < data.length; i++) {

                    var lat = data[i].lattitude;
                    var lng = data[i].longitude;
                    var id = data[i].id;
                    var c_area = data[i].coverage_area;
                    var service = new google.maps.DistanceMatrixService();
                    service.getDistanceMatrix({
                        origins: [new google.maps.LatLng(lat, lng)],
                        destinations: [c_latLng],
                        travelMode: google.maps.TravelMode.DRIVING,
                        unitSystem: google.maps.UnitSystem.METRIC,
                        avoidHighways: true,
                        avoidTolls: false
                    }, function(response, status) {

                        if (status !== google.maps.DistanceMatrixStatus.OK) {
                            bootbox.alert('<h4 class="text text-danger">Error while Calculating Distance ! - ' + status + '</h4>');
                        } else {
                            try {
                                var dis = response.rows[0].elements[0].distance.value;
                                if ((dis / 1000) <= c_area) {
                                    usableServices.unshift(id + '-' + dis);
                                }
                            } catch (err) {
                                console.log(err);
                            }
                        }
                    });
                    if (i === (data.length)) {
                        alert(usableServices.length);
                    }
                }
            } else if (data[0].status === "NA") {
                bootbox.alert('<div style="margin-top:35px;" class="well"><h4>Sorry, There are no Cab Services Registered around your city or <br> <b>The\'re all in </b><label class="label label-success">Services.</label></h4></div> ');
            } else {
                bootbox.alert('<h4 class="text text-danger">Sorry, Unable to Continue ! Please try again.</h4>');
            }
        } else {
            bootbox.alert('<h4 class="text text-danger">Sorry, Unable to Continue !</h4>');
        }
    }, 'json');
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Check Coverage">

function canNextForSetDestination() {
    if (usableServices.length > 0) {
        var wait = makePleaseWait();
        $('#clientLocationContainer').fadeOut('fast', function() {
            $('#mainContainer').append(wait);
        });
        $.post('SaveCurrentProcess', {location: c_latLng.toString(), selected: usableServices.join(';')}, function(data, textStatus, jqXHR) {
            if (textStatus === "success") {
//                bootbox.alert('<h4 class="text text-info"><b>' + usableServices.length + '</b> Cab Service(s) around you.</h4>');
                $(wait).hide();
                $('#clientDestinationContainer').fadeIn();
                initDestinationSelector();
            }
        }, 'text');
    } else {
        bootbox.alert('<h4 class="text text-danger"><b>Sorry, </b>You are not in a coverage area by city you\'r selected.</h4>');
    }
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Init Destination Container">
function initDestinationSelector() {
    var destMap = new google.maps.Map(document.getElementById('destContainer'), {
        center: c_latLng,
        zoom: 17
    });
    c_dest = c_latLng;
    var infowindow = new google.maps.InfoWindow();
    var marker = new google.maps.Marker({
        map: destMap,
        position: c_latLng,
        icon: 'images/marker/map-marker.png'
    });
    infowindow.setContent('<div>I want go <strong>there !</strong>');
    infowindow.open(destMap, marker);
    google.maps.event.addListener(marker, 'click', function() {
        infowindow.open(destMap, marker);
    });
    google.maps.event.addListener(destMap, 'click', function(event) {
        marker.setPosition(event.latLng);
        infowindow.setContent('<div>I want go <strong>there</strong><br><i>We\'l drop you there soon after the Order</i>');
        infowindow.open(destMap, marker);
        c_dest = event.latLng;
    });
    var input = (document.getElementById('destSearcher'));
    var autocomplete = new google.maps.places.Autocomplete(input, {
        componentRestrictions: {
            'country': 'lk'
        }
    });
    autocomplete.bindTo('bounds', destMap);
    google.maps.event.addListener(autocomplete, 'place_changed', function() {
        infowindow.close();
        marker.setVisible(false);
        var place = autocomplete.getPlace();
        if (!place.geometry) {
            return;
        }

        if (place.geometry.viewport) {
            destMap.fitBounds(place.geometry.viewport);
        } else {
            destMap.setCenter(place.geometry.location);
            destMap.setZoom(18);
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

        infowindow.setContent('<div>I want go <strong>' + place.name + '</strong><br>' + address + '<br><i>We\'l drop you there soon after the Order</i>');
        infowindow.open(destMap, marker);
        console.log(place.geometry.location);
        c_dest = place.geometry.location;
    });
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Calculate Distance">
function generateOrderDistance() {
    var wait = makePleaseWait();
    $('#clientDestinationContainer').fadeOut('fast', function() {
        $('#mainContainer').append(wait);
    });
    var directionsDisplay;
    var directionsService = new google.maps.DirectionsService();
    var map;
    directionsDisplay = new google.maps.DirectionsRenderer();
    var mapOptions = {
        zoom: 8,
        center: c_dest
    };
    map = new google.maps.Map(document.getElementById('order-map-container'), mapOptions);
    directionsDisplay.setMap(map);
    var request = {
        origin: c_latLng,
        destination: c_dest,
        travelMode: google.maps.TravelMode.DRIVING
    };
    directionsService.route(request, function(response, status) {
        if (status === google.maps.DirectionsStatus.OK) {
            directionsDisplay.setDirections(response);
        }
    });
    var service = new google.maps.DistanceMatrixService();
    service.getDistanceMatrix({
        origins: [c_latLng],
        destinations: [c_dest],
        travelMode: google.maps.TravelMode.DRIVING,
        unitSystem: google.maps.UnitSystem.METRIC,
        avoidHighways: true,
        avoidTolls: false
    }, function(response, status) {
        if (status !== google.maps.DistanceMatrixStatus.OK) {
            bootbox.alert('<h4 class="text text-danger">Error while Calculating Distance ! - ' + status + '</h4>');
        } else {
            try {
                var distance = (response.rows[0].elements[0].distance.value);
                var duration = (response.rows[0].elements[0].duration.text);
                $('#order-distance').html((distance / 1000) + ' km');
                $('#order-duration').html(duration);
                $.post('GenerateOrder', {destination: c_dest.toString(), distance: distance, duration: duration}, function(data, textStatus, jqXHR) {
                    if (textStatus === "success") {
                        $(wait).hide();
                        $('#orderDetailsContainer').fadeIn('fast', function() {
                            google.maps.event.trigger(map, "resize");
                        });
                    } else {
                        bootbox.alert('<h4 class="text text-danger">Unable to Continue ! Please try again later.</h4>');
                    }
                }, 'text');
            } catch (err) {
                bootbox.alert('<h4 class="text text-danger">Error while Calculating Distance ! - ' + status + '</h4>');
            }
        }
    });
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Pick Service">
function showPickService() {
    disableOrderPanel(true);
    $('#select-service').modal();
    var wait = $(makePleaseWait()).html();
    $('#list-services').html(wait);
    $.post('GetUsableServices', {}, function(data, textStatus, jqXHR) {
        if (textStatus === 'success') {
//            var status = data[0].status;
            var desc = data[0].description;
            $('#list-services').html(desc);
            $('.list-services input[type="radio"]').click(function(evt) {
                $('#selected-service').val($(this).val());
                disableOrderPanel(false);
            });
        }
    }, 'json');
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Disable Buttons">
function disableOrderPanel(boolean) {
    $('.list-services-panel button').prop('disabled', boolean);
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Add to Cart">
function addToCart(viewCart) {
    var service = $('#selected-service').val();
    if (service !== 'NONE') {
        createRequest();
        xmlhttp.open('post', 'AddToCart', true);
        xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xmlhttp.send('service=' + service);
        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState === 4 & xmlhttp.status === 200) {
                if (viewCart) {
                    document.location.href = "shoppingCart.jsp";
                } else {
                    document.location.href = "index.jsp";
                }
            }
        };
    }
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Remove From Cart">
function removeFromCart(item) {
    xmlhttp.open('post', 'RemoveFromCart', true);
    xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xmlhttp.send('item=' + item);

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === 4 & xmlhttp.status === 200) {
            viewCart();
        }
    };
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="View Cart">
function viewCart() {
    try {
        $('#shopping-cart').html(makePleaseWait().innerHTML);
        $('#shopping-cart-summary').html(makePleaseWait().innerHTML);
        createRequest();
        xmlhttp.open('post', 'GenerateCart', true);
        xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xmlhttp.send();

        xmlhttp.onreadystatechange = function(evt) {
            console.log(evt);
            if (xmlhttp.readyState === 4 & xmlhttp.status === 200) {
                var json = xmlhttp.responseText;
                var object = JSON.parse(json);
                $('#shopping-cart').html(object[0].cart);
                $('#shopping-cart-summary').html(object[0].checkout);

                $('[data-toggle="tooltip"]').tooltip();
            }
        };
    } catch (e) {
        console.log(e);
    }
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="View Cab Service">
function viewCabService(id) {
    alert(id + 'should be view.');
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="loaderVisible">
function loaderVisible(v) {
    if (v) {
        $("#loaderImg").attr("src", "images/other/cube.gif");
        $("#loaderImg").show();
    } else {
        $("#loaderImg").hide(0, function() {
            $("#loaderImg").attr("src", "");
        });
    }
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Checkout Shopping Order [NOT COMPLETED]">
function checkoutShoppingOrder() {
    $(this).prop('disabled', true);
    $(this).toggleClass('disabled');
    createRequest();
    xmlhttp.open('post', 'SaveServiceInvoice', true);
    xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xmlhttp.send();

    xmlhttp.onreadystatechange = function() {
        if (xmlhttp.readyState === 4 & xmlhttp.status === 200) {
            $(this).prop('disabled', true);
            $(this).toggleClass('disabled');

            var res = xmlhttp.responseText.trim();
            if (res === 'OK') {
                document.location.href = "expresscheckout.jsp";
            } else if (res === 'NOT_LOGGED') {
                showLoginDialog();
            } else if (res === 'NO_CLIENT') {
                bootbox.alert('<h4 class="alert alert-danger">Please log in from your Client Account.But your current Order will be Lost.</h4>');
            } else if (res === 'EMPTY_CART') {
                bootbox.alert('<h3 class="alert alert-danger">Sorry, your cart is empty !</h3>');
            } else {
                bootbox.alert('<h3 class="alert alert-danger">Sorry, something went wrong. We cannot continue from here. !</h3>');
            }
        }
    };
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="View Service Order">
function viewServiceOrder(id) {
    $('#order-details').html(makePleaseWait().innerHTML);
    $('#new-ride-btn').prop('disabled', true);
    $('#details-modal').modal();
    $('#details-modal').on('shown.modal.bs', function() {
        $.post('GetOrderDetails', {order: id}, function(data, textStatus, jqXHR) {
            if (textStatus === 'success') {
                var status = data[0].status;
                var html;
                if (status === 'OK') {
                    var order = data[0].order;
                    console.log(order);
                    $('#full-name').html(order.client.firstname + ' ' + order.client.lastname);
                    html = '<div class="table-responsive" style="border-bottom: 1px solid lightgray;margin-bottom: 15px;">'
                            + '<table class="table table-hover">'
                            + '<thead><th>Start Point</th><th>Destination</th><th>Distance</th><th>Cost Per KM(Rs)</th><th>Total</th></thead>'
                            + '<tr><td><button onclick="showOnMap(' + order.start_point.latitude + ',' + order.start_point.longitude + ')" class="btn btn-sm btn-default"><span class="glyphicon glyphicon-map-marker"></span></button></td>'
                            + '<td><button onclick="showOnMap(' + order.destination.latitude + ',' + order.destination.longitude + ')" class="btn btn-sm btn-default"><span class="glyphicon glyphicon-map-marker"></span></button></td>'
                            + '<td>' + order.km + '</td>'
                            + '<td>' + order.cost_per_km + '</td>'
                            + '<td>' + order.total + '</td>'
                            + '</tr>'
                            + '</table>'
                            + '</div>'
                            + '<div class="well">'
                            + '<legend style="font-size: 16px"><b>Assign Cab & Driver</b></legend>'
                            + '<div id="set-assigns"><div class="form-group">'
                            + '<select class="form-control"><option>Cab 1</option><option>Cab 2</option></select>'
                            + '</div>'
                            + '<div class="form-group">'
                            + '<select class="form-control"><option>Driver 1</option><option>Driver 2</option></select>'
                            + '</div>'
                            + '</div>'
                            + '</div>';

                    $('#payment-status').html(order.payment_status);
                    if (order.payment_status === 'No Payment') {
                        no_pay = true;
                    }
                    $.post('GetOnlineCabsAndDrivers', {}, function(data, textStatus, jqXHR) {
                        if (textStatus === 'success') {
                            data = data.trim();
                            if (data === 'ERROR') {
                                $('#set-assigns').html('<h3 class=\'text text-danger\'>Sorry,Error while getting Available Cabs and Drivers.</h3>');
                            } else if (data === 'NOT_LOGGED') {
                                document.location.href = "userLoging.jsp";
                            } else {
                                $('#new-ride-btn').prop('disabled', false);
                                $('#new-ride-btn').attr('onclick', "referOrder('" + order.id + "')");
                                $('#set-assigns').html(data);
                            }
                        }
                    }, 'text');
                } else if (status === 'NOT_FOUND') {
                    html = '';
                } else {
                    html = '';
                }
                $('#order-details').html(html);

            }
        }, 'json');
    });
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Make Notification">
function makeNotification(text, bg) {
    var not = document.createElement('div');
    $(not).addClass('top-notification');
    $(not).html(text);
    if (bg) {
        $(not).css({
            backgroundColor: bg
        });
    }
    $(not).css({top: -35});
    $('body').append(not);
    $(not).animate({top: 0}, 250).delay(2000).animate({top: -35}, 200, function() {
        $(not).remove();
    });

    $(not).mouseenter(function() {
        $(not).clearQueue();
        $(not).stop();
    });

    $(not).mouseleave(function() {
        $(not).animate({top: 0}, 250).delay(500).animate({top: -35}, 200, function() {
            $(not).remove();
        });
        $(not).queue(function() {
            $(this).dequeue();
        });
    });

    return not;
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Show Terms & COnditions">
function showTermsAndCond() {
    var dlg = bootbox.dialog({
        title: 'Terms & Conditions',
        message: '<div style="overflow:scroll;height:300px;" id="terms-show"></div>',
        buttons: {
            close: {
                label: 'Close'
            }
        }
    });
    $.get('docs/terms_and_conditions.txt', {}, function(data, textStatus, jqXHR) {
        if (textStatus === 'success') {
            $('#terms-show').html(data);
        }
    }, 'text');
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Dark Please Wait">
function makeDarkPleaseWait() {
    var dark = document.createElement('div');
    $(dark).addClass('modal fade');
    $(dark).html('<div style="width:100%; height:100%;background-color:rgba(50,50,50,.1);position:absolute;top:0;left:0;"><div style="border-radius:4px;;max-width:140px;margin:15% auto;;background-color:white;">' + makePleaseWait().innerHTML + '</div></div>');
    $(dark).modal({keyboard: false, backdrop: true});
    return $(dark);
}
//</editor-fold>

function changeCity(){
    $('#clientLocationContainer').hide();
    $('#clientDestinationContainer').hide();
    $('#orderDetailsContainer').hide();
    $('#findCityContainer').show();
}

//************************************************************************************************************************************************
//          DOCUMENT READY FUNCTION START                                             /                                                         /
//**********************************************************************************************************************************************
//<editor-fold defaultstate="collapsed" desc="document ready state JQuery">
$(function() {


    $('#serviceContactDetails :input').focusout(function() {
        validateContactDetails();
    });
    function ResultDlg(bo) {
        if (!bo) {
            $('#noResultMsg').fadeOut('fast');
        } else {
            $('#noResultMsg').fadeIn('fast');
        }
    }

//administrator login

    var options = {
        beforeSend: function() {
            $('#admin-login-loader').removeClass('hide');
            $('#admin-login-btn').addClass('disabled');
        },
        uploadProgress: function(event, position, total, percentComplete) {

        },
        success: function(data) {
            $('#admin-login-loader').hide();
            $('#admin-login-btn').removeClass('disabled');
            if (data === "found") {
                document.location.href = "dashboard";
                $('#admin-login-msg').removeClass('alert alert-danger');
                $('#admin-login-msg').html('');
            } else if (data === "not_found") {
                $('#admin-login-msg').addClass('alert alert-danger');
                $('#admin-login-msg').html('Incorrect Email/Password');
            } else if (data === "error") {
                $('#admin-login-msg').addClass('alert alert-danger');
                $('#admin-login-msg').html('Error in Server. Please try again later..');
            }
        },
        complete: function(response) {
            $('#admin-login-loader').hide();
            $('#admin-login-btn').removeClass('disabled');
        },
        error: function() {
            $('#admin-login-loader').hide();
            $('#admin-login-btn').removeClass('disabled');
        }
    };
    $('#admin-login-form').ajaxForm(options);
    //Popover
    $('[data-toggle="popover"]').popover();
    var loginOptions = {title: 'Login',
        html: true,
        placement: 'bottom',
        container: 'body',
        content: function() {
            return $('#loginHtml').html();
        }
    };
    $('#loginButton').popover(loginOptions);
    //Tooltip
    $('[data-toggle="tooltip"]').tooltip();
    //custom radio btn config
    $('.custom-radio-btn').click(function() {
        $('.custom-radio-btn').removeClass('radio-active');
        $(this).addClass('radio-active');
        $('.selected-tag').hide();
        $('#' + $(this).data('membership')).show();
        memberType = $(this).data('membership');
    });
    //config pw toggle
    $('#client-pw-toggle').on('click', function() {
        $(this).toggleClass('active');
        if (pwToggle) {
            $($(this).data('attach-to')).attr('type', 'text');
            pwToggle = false;
        } else {
            $($(this).data('attach-to')).attr('type', 'password');
            pwToggle = true;
        }
    });
    //membership plan seletion
    $('.select-plan').on('click', function(evt) {

//        SET PLAN NAME
        $('#plan-name').html($(this).data('plan-name'));
        m_plan_name = $(this).data('plan-name');
        //        SET PLAN ID
        m_plan = $(this).data('plan-id');
        //        SET DURA PLAN PRICE & DURATION
        var dura = $(this).parent().children('.custom-content').children('.duration-plan').val();
        var s_dura = $('#serviceDurationMonths');
        var s_price = $('#serviceDurationPrice');
        m_duration = dura;
        s_dura.html('Loading...');
        s_price.html('Loading...');
        $.post('GetDurationDetails', 'duration=' + dura, function(data, textStatus, jqXHR) {
            if (textStatus === "success") {
                if (data !== "ERROR" | data !== "ZERO") {
                    s_dura.html(data[0].months + ' Month(s)');
                    s_price.html('Rs: ' + data[0].price);
                } else {
                    s_dura.html('Unable to view.');
                    s_price.html('Unable to view.');
                }
            } else {
                s_dura.html('Error while Loading...');
                s_price.html('Error while Loading...');
            }
        }, 'json');
        //        TRANSITION
        $('#membershipPlan').fadeOut("fast", function() {
            $('#regDetails').fadeIn();
        });
    });
    //    FIND CITY
    $('#homeInputCity').keyup(function(evt) {
        if ($(this).val()) {
            createRequest();
            ResultDlg(false);
            loaderVisible(true);
            setTimeout(findCityPlace, 250);
        } else {
            $('#homeResultCity').html('');
            loaderVisible(false);
            ResultDlg(false);
        }
    });
    function findCityPlace() {
        $('#homeResultCity').html('');
        xmlhttp.open('get', 'Places.jsp?input=' + $('#homeInputCity').val(), true);
        xmlhttp.send();
        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState === 4 & xmlhttp.status === 200) {
                var res = xmlhttp.responseText;
                var status = JSON.parse(res).status;
                var html = '';
                if (status === "OK") {
                    var obj = JSON.parse(res).predictions;
                    for (var i = 0; i < obj.length; i++) {
                        html += '<li><button class="btn btn-primary little-space" onclick="findOnMap(\'' + obj[i].place_id + '\')">' + obj[i].description + '</button></li>';
                    }

                    ResultDlg(false);
                } else if (status === "ZERO_RESULTS") {
                    ResultDlg(true);
                } else if (status === "OVER_QUERY_LIMIT") {
                    ResultDlg(false);
                    bootbox.alert('Yout Google Api Key Limit is Exceeded !');
                } else if (status === "REQUEST_DENIED") {
                    ResultDlg(false);
                    bootbox.alert("Invalid Goolge API key.");
                } else if (status === "ERROR") {
                    ResultDlg(false);
                    bootbox.alert("Please Check your Internet Connection !");
                }
                $('#homeResultCity').html(html);
                loaderVisible(false);
            }
        };
    }


    $('#userLoginForm').ajaxForm(signInOptions);
});
//</editor-fold>
//DOCUMENT READY FUNCTION END