<div>
    <div class="col-md-6" >
        <legend>New Membership Upgrades</legend>
        <div id="memb-req"></div>
    </div>
    <div class="col-md-3"></div>
    <div class="col-md-3"></div>
</div>

<div class="modal fade">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <h2 class="modal-title">Reject Membership Request</h2>
            </div>
            <div class="modal-body" id="req-content">
            </div>
            <div class="modal-footer">
                <button class="btn btn-danger" data-dismiss="modal" >Reject</button>
                <button class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function() {
        getMembershipRequests();
    });

    function activateMembership(id) {
        var wait = makeDarkPleaseWait();
        $.post('Admin/ActivateMembership', {id: id}, function(data, textStatus, jqXHR) {
            if (textStatus === 'success') {
                $(wait).modal('hide');
                if (data === 'OK') {
                    makeNotification('Memebership Activated Successfully.', '00cc99');
                } else {
                    makeNotification('Error while Activating Membership !', 'tomato');
                }
                getMembershipRequests();
            }
        }, 'text');
    }

    function getMembershipRequests() {
        $('#memb-req').html(makePleaseWait().innerHTML);
        $.post('Admin/GetMembershipRequest', {}, function(data, textStatus, jqXHR) {
            if (textStatus === 'success') {
                $('#memb-req').html(data);
            }
            $('[data-toggle="tooltip"]').tooltip();
        }, 'text');
    }
</script>