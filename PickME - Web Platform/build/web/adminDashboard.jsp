<%-- 
    Document   : adminDashboard
    Created on : Mar 3, 2015, 3:23:18 PM
    Author     : Deeptha
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html>
    <head>
        <title>Administrator Dashboard - Pickme.lk</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="_/components/css/bootstrap.css">
        <link href="_/css/chosen.css" type="text/css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="_/css/styles.css">
        <link href="images/favicon/default.ico" rel="icon" type="image/x-icon"> 
    </head>
    <body>
        <!-- implement JavaScript -->
        <script src="_/js/jquery.min.js" type="text/javascript"></script>
        <script src="_/js/jquery.form.js" type="text/javascript"></script>
        <script src="_/js/jquery.easing.1.3.js" type="text/javascript"></script>
        <script src="_/components/js/bootstrap.js" type="text/javascript"></script>
        <script src="_/js/Chart.js" type="text/javascript"></script>
        <script src="_/js/bootbox.js" type="text/javascript"></script>
        <script src="_/js/aloha.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="_/js/chosen.jquery.min.js"></script>
        <!--<script type="text/javascript" src="_/js/chosen.proto.js"></script>-->
        <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=false&libraries=places"></script>
        <script src="_/js/functions.js" type="text/javascript"></script>

        <div id="wrapper">
            <!--PANEL START-->
            <div id="sidebar-wrapper">
                <ul class="sidebar-nav">
                    <li class="service-name">
                        <h3 id="service-name">Welcome</h3>
                    </li>
                    <li>
                        <a href="#overview"><span class="glyphicon glyphicon-stats" aria-hidden="true"></span> Overview </a>
                    </li>
                    <li>
                        <a href="#message"><span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span> Messages </a>
                    </li>
                    <li>
                        <a href="#privileges"><span class="glyphicon glyphicon-eye-close" aria-hidden="true"></span> Privileges </a>
                    </li>
                    <li>
                        <a href="#terms"><span class="glyphicon glyphicon-certificate" aria-hidden="true"></span> Terms & Conditions </a>
                    </li>
                </ul>
            </div>
            <!--PANEL END-->

            <!--PAGE CONTENT START-->
            <div id="page-content-wrapper">
                <div class="dashboard-user-panel col-lg-12">
                    <div class="container-fluid">
                        <div class="hide visible-xs pull-left">
                            <button id="toggle-wrapper" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-th-large" aria-hidden="true"></span></button>
                        </div>
                        <label class="pull-right"><span class="glyphicon glyphicon-user" aria-hidden="true"></span> Administrator <a href="Logout" data-toggle="tooltip" title="Logout" data-placement="bottom"><span class="glyphicon glyphicon-log-out"></span></a></label>
                    </div>
                </div>

                <div class="col-lg-12 dashboard-main-background" style="margin-bottom: 50px;min-height: 600px">
                    <div class="container-fluid">
                        <div id="overview" style="display: none">
                            <h2 class="page-header">Overview</h2>
                            <%@include file="_/components/jsp/admin-overview.jsp" %>
                        </div>

                        <div id="message" style="display: block">
                            <h2 class="page-header">Messages</h2>
                            <%@include file="_/components/jsp/message_center.jsp"%>
                        </div>

                        <div id="privileges" style="display: none">
                            <h2 class="page-header">Privileges</h2>

                            <div class="form-group" style="margin-bottom: 60px">
                                <div class="col-md-2">
                                    <label class="control-label">Enable Privileges </label>
                                </div>
                                <div class="col-md-10">
                                    <div class="onoffswitch">
                                        <input type="checkbox" name="onoffswitch" class="onoffswitch-checkbox" onchange="togglePrivileges()" id="myonoffswitch" <%if (application.getAttribute("PRIVILEGES_FILTER") != null) {
                                                if ((Boolean) application.getAttribute("PRIVILEGES_FILTER")) {
                                                    out.print("checked");
                                                }
                                            };%>>
                                        <label class="onoffswitch-label" for="myonoffswitch">
                                            <span class="onoffswitch-inner"></span>
                                            <span class="onoffswitch-switch"></span>
                                        </label>
                                    </div>  
                                </div>
                            </div>

                            <div role="tabpanel">

                                <!-- Nav tabs -->
                                <ul class="nav nav-tabs" role="tablist" id="priv-tabs">
                                    <li role="presentation" class="active"><a href="#manage-privileges" aria-controls="manage-privileges" role="tab" data-toggle="tab">Manage Privileges</a></li>
                                    <li role="presentation"><a onclick="getPrivilegesTable(0, true)" href="#edit-privileges" aria-controls="edit-privileges" role="tab" data-toggle="tab">Add/Edit Privileges</a></li>
                                </ul>

                                <!-- Tab panes -->
                                <div class="tab-content">
                                    <div role="tabpanel" class="tab-pane active" id="manage-privileges">
                                        <div class="panel-body table-responsive" id="user-privileges"></div>
                                    </div>

                                    <div role="tabpanel" class="tab-pane" id="edit-privileges">
                                        <div id="priv-table"></div>
                                        <div class="well">
                                            <button  onclick="addNewPrivilege()" class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span> Add New</button>
                                        </div>
                                    </div>
                                </div>

                            </div>

                        </div>

                        <div id="terms" style="display: none">
                            <h2 class="page-header">Terms & Conditions</h2>
                            <!--<div class="panel panel-default">-->
                            <!--<div class="panel-heading"></div>-->
                            <!--<div class="panel-body">-->
                            <div class="well well-sm" style="background-color: #20d9d9">
                                <button class="action-bold btn btn-default"><span class="glyphicon glyphicon-bold"></span></button>
                                <button class="action-italic btn btn-default"><span class="glyphicon glyphicon-italic"></span></button>
                                <button class="action-underline btn btn-default"><span style="text-decoration: underline" class="glyphicon glyphicon-magnet"></span></button>
                                <button class="action-unformat btn btn-default"><span class="glyphicon glyphicon-remove"></span></button>
                                <button class="pull-right btn btn-sm btn-default" onclick="updateTerms()">Update</button>
                            </div>
                            <div id="terms-edit" class="custom-editor"></div>
                            <!--</div>-->
                            <!--</div>-->
                        </div>
                    </div>
                </div>
                <%@include file="_/components/jsp/admin-dashboard-bottom.jsp" %>
            </div>
            <!--PAGE CONTENT END-->
        </div>

        <script type="text/javascript">
            $('#toggle-wrapper').click(function() {
                $('#wrapper').toggleClass('toggled');
                $(this).toggleClass('active');
            });
            $(function() {
                message();

                aloha(document.querySelector('#terms-edit'));
                
                for (var command in aloha.ui.commands) {
                    $('.action-' + command).on(
                            'click',
                            aloha.ui.command(aloha.ui.commands[command])
                            );
                }

                $('.sidebar-nav li a').click(function(evt) {
                    var id = $(this).attr('href');
                    var string = ((id === '#overview' ? '' : '#overview, ') +
                            (id === '#message' ? '' : '#message, ') +
                            (id === '#privileges' ? '' : '#privileges, ') +
                            (id === '#terms' ? '' : '#terms, '));
                    string = string.substring(0, string.lastIndexOf(','));
                    $(string).hide(0);
//                    $(string).slideUp(500);
//                    $(id).slideDown(500, function() {
                    $(id).show(0, function() {
                        id = id.replace('#', ' ') + '()';
                        setTimeout(id, 0);
                    });
                });
                var options = {
                    beforeSend: function() {
                        $('#add-privilege-form *').prop('disabled', true);
                    },
                    uploadProgress: function(event, position, total, percentComplete) {

                    },
                    success: function() {

                    },
                    complete: function(response) {
                        if (response.responseText === 'OK') {
                            makeNotification("Restrictions Added Successfully.", '#79D076');
                        } else if (response.responseText === 'EXIST') {
                            makeNotification("Cannot Add More than one same Restriction for each User Type.");
                        } else {
                            makeNotification("Error while Adding Restrictions!", '#C23739');
                        }
                        privileges();
                        $('#add-privilege-form *').prop('disabled', false);
                        $('#allow').prop('check', true);
                        $('#allowed').val('');
                        $('#disallowed').val('');
                    },
                    error: function() {

                    }
                };

                $('#add-privilege-form').ajaxForm(options);

                var addOptions = {
                    beforeSend: function() {
                        $('#add-new-privilege-form *').prop('disabled', true);
                    },
                    uploadProgress: function(event, position, total, percentComplete) {

                    },
                    success: function() {

                    },
                    complete: function(response) {
                        if (response.responseText === 'OK') {
                            makeNotification("Privileges Added/Edited Successfully.", '#79D076');
                        } else {
                            makeNotification("Sorry, Unable to Complete !", '#C23739');
                        }
                        getPrivilegesTable();
                        $('#add-new-privilege-form *').prop('disabled', false);
                    },
                    error: function() {

                    }
                };

                $('#add-new-privilege-form').ajaxForm(addOptions);

                $('#priv-tabs a').click(function(e) {
                    e.preventDefault();
                    $(this).tab('show');
                });
            });
            function overview() {
                getMembershipRequests();
            }

            function message() {
                refresh(true);
            }

            function privileges() {
                $('#user-privileges').html(makePleaseWait().innerHTML);
                $.post('Admin/GetUserPrivileges', {}, function(data, textStatus, jqXHR) {
                    if (textStatus === 'success') {
                        $('#user-privileges').html(data);
                    }
                }, 'text');
            }

            function terms() {
                $.get('docs/terms_and_conditions.txt', {}, function(data, textStatus, jqXHR) {
                    if (textStatus === 'success') {
                        $('#terms-edit').html(data);
                    }
                }, 'text');
            }

            function togglePrivileges() {
                var checked = $('#myonoffswitch').prop('checked');
                $.post('Admin/TogglePrivileges', {enable: checked}, function(data, textStatus, jqXHR) {
                }, 'text');
            }

            function addPrivilege(uid) {
                $('#userType option[value="' + uid + '"]').prop('selected', true);
                $('#add-privilege-dlg').modal();
                getPrivilegesSelect();
            }

            function removePrivilege(prid) {
                $.post('Admin/RemovePrivilege', {privilege: prid}, function(data, textStatus, jqXHR) {
                    if (textStatus === 'success') {
                        if (data === 'OK') {
                            makeNotification('Removed Successfully.', '#C23739');
                        }
                        privileges();
                    }
                }, 'text');
            }

            function toggleAllow(prid) {
                $.post('Admin/ToggleAllow', {privilege: prid}, function(data, textStatus, jqXHR) {
                    if (textStatus === 'success') {
                        if (data === 'OK') {
                            makeNotification('Changed Successfully.', '#79D076');
                        }
                        privileges();
                    }
                }, 'text');
            }

            function getPrivilegesSelect() {
                $.post('Admin/GetPrivileges', {mode: 'select'}, function(data, textStatus, jqXHR) {
                    if (textStatus === 'success') {
                        $('#privilegeUri').html(data);
                    }
                }, 'text');
            }

            function getPrivilegesTable(start, firsttime) {
                if (firsttime) {
                    $('#priv-table').html(makePleaseWait().innerHTML);
                }
                if (!start) {
                    start = 0;
                }
                $.post('Admin/GetPrivileges', {mode: 'table', start: start}, function(data, textStatus, jqXHR) {
                    if (textStatus === 'success') {
                        $('#priv-table').html(data);
                    }
                }, 'text');
            }

            function editPrivilege(uri, name) {
                $('#uri_id').val(uri);
                $('#uri').val(uri);
                $('#uri').prop('disabled', true);
                $('#pname').val(name);
                $('#dlg-btn-text').html('Update Privilege');
                $('#add-new-privilege-dlg').modal();
            }

            function addNewPrivilege() {
                $('#uri_id').val('NEW');
                $('#uri').val('');
                $('#uri').prop('disabled', false);
                $('#pname').val('');
                $('#dlg-btn-text').html('Add Privilege');
                $('#add-new-privilege-dlg').modal();
            }

            function deletePrivilege(uprid) {
                $.post('Admin/DeleteUserPrivilege', {delete: uprid}, function(data, textStatus, jqXHR) {
                    if (textStatus === 'success') {
                        if (data === 'OK') {
                            makeNotification('Deleted Successfully.', '#79D076');
                        } else if (data === 'USING') {
                            makeNotification('Cannot Delete User Privilege while it Using. Please Remove it from Restrictions first.');
                        } else {
                            makeNotification('Error while Removing.', '#C23739');
                        }
                        getPrivilegesTable();
                    }
                }, 'text');
            }

            function updateTerms() {
                var big_text = $('#terms-edit').html();
                $.post('Admin/UpdateTermsAndConditions', {text: big_text}, function(data, textStatus, jqXHR) {
                    if (textStatus === 'success') {
                        if (data === 'OK') {
                            makeNotification('Terms & Conditions Updated Successfully.', '#79D076');
                        }
                        terms();
                    }
                }, 'text');
            }

        </script>

        <div id="add-privilege-dlg" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form id="add-privilege-form" class="form-horizontal" action="Admin/AddPrivilege" method="post">
                        <div class="modal-header"><button class="close btn" data-dismiss="modal">&times;</button><h3 class="modal-title">Add Privilege to User</h3></div>
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="privilegeUri" class="col-sm-2 control-label">Privilege</label>
                                <div class="col-sm-10">
                                    <select id="privilegeUri" name="privilegeUri" class="form-control"></select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="userType" class="col-sm-2 control-label">User Type</label>
                                <div class="col-sm-10">
                                    <select id="userType" name="userType" class="form-control">
                                        <option value="5" id="guest">Anyone(Guest)</option>
                                        <option value="1" id="client">Client</option>
                                        <option value="3" id="driver">Cab Driver</option>
                                        <option value="2" id="service">Service Provider</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="allow" class="col-sm-2 control-label">Allow</label>
                                <div class="col-sm-10">
                                    <div class="onoffswitch">
                                        <input type="checkbox" name="allow" class="onoffswitch-checkbox" id="allow" checked>
                                        <label class="onoffswitch-label" for="allow">
                                            <span class="onoffswitch-inner"></span>
                                            <span class="onoffswitch-switch"></span>
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="allowed" class="col-sm-2 control-label">Allowed Page</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="allowed" name="allowed" placeholder="Allowed Page Name" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="disallowed" class="col-sm-2 control-label">Disallowed Page</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="disallowed" name="disallowed" placeholder="Disallowed Page Name" />
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer"><button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span> Add</button></div>
                    </form>
                </div>
            </div>
        </div>

        <div class="modal fade" id="add-new-privilege-dlg">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button class="close btn" data-dismiss="modal">&times;</button>
                        <h3 class="modal-title">Add/Edit Privilege</h3>
                    </div>
                    <form id="add-new-privilege-form" action="Admin/AddEditPrivilege" method="post" class="form-horizontal">
                        <input type="hidden" name="id" id="uri_id" value="NEW">
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="uri" class="col-sm-2 control-label">Privilege URI</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" name="uri" id="uri" placeholder="eg: /Pickme.lk/home.jsp">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="pname" class="col-sm-2 control-label">Privilege Name</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" name="pname" id="pname" placeholder="eg: Home Page">
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-primary btn-lg"><span class="glyphicon glyphicon-plus"></span> <span id="dlg-btn-text">Add New Privilege</span></button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

    </body>
</html>