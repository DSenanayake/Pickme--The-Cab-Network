<div>
    <div class="well well-sm">
        <a class="btn btn-sm btn-primary" data-toggle="modal" id="compose-btn" href="#new-msg">Compose</a>
        <button class="btn pull-right btn-default" onclick="refresh()" data-toggle='tooltip' title="Refresh"><span class="glyphicon glyphicon-refresh" aria-hidden='true'></span></button>
    </div>
    <div role="tabpanel" id="msg-tabs">

        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active"><a href="#new-message" aria-controls="new-message" role="tab" data-toggle="tab">Message</a></li>
            <li role="presentation"><a href="#warning" aria-controls="warning" role="tab" data-toggle="tab">Warning</a></li>
            <li role="presentation"><a href="#feedback" aria-controls="feedback" role="tab" data-toggle="tab">Feedback</a></li>
            <li role="presentation"><a href="#complaint" aria-controls="complaint" role="tab" data-toggle="tab">Complaint</a></li>
        </ul>

        <!-- Tab panes -->
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane active table-responsive" id="new-message"></div>
            <div role="tabpanel" class="tab-pane table-responsive" id="warning"></div>
            <div role="tabpanel" class="tab-pane table-responsive" id="feedback"></div>
            <div role="tabpanel" class="tab-pane table-responsive" id="complaint"></div>
        </div>

    </div>
</div>
<div class="modal fade" id="new-msg">
    <div class="modal-dialog">
        <div class="modal-content">
            <form id="send-msg" method="get" class="form-horizontal">
                <div class="modal-header">
                    <div class="row">
                        <h3 class="modal-title col-md-2" id="msg-title">New</h3>
                        <div class="col-md-10">
                            <select name="type" id="msg-type" class="form-control">
                                <option value="1">Message</option>
                                <option value="2">Warning</option>
                                <option value="3">Feedback</option>
                                <option value="4">Complaint</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="msg-to" class="col-sm-2 control-label">To :</label>
                        <div class="col-sm-10">
                            <!--<input type="text" class="form-control" id="msg-to" placeholder="Receiver">-->
                            <select name="receiver" multiple class="chosen-select form-control"><%=Model.User.getAllUserNames("option", "value", null)%></select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="msg-subject" class="col-sm-2 control-label">Subject :</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="msg-subject" placeholder="Subject">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="msg-content" class="col-sm-2 control-label">Content :</label>
                        <div class="col-sm-10">
                            <textarea class="form-control" id="msg-content" placeholder="Enter Text Here..." ></textarea>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <div id="prior-btns" class="btn-group btn-group-xs pull-left" role="group" aria-label="...">
                        <button type="button" class="btn btn-danger">Important</button>
                        <button type="button" class="btn btn-primary disabled">Medium</button>
                        <button type="button" class="btn btn-success">Low</button>
                    </div>


                    <button type="button" data-dismiss="modal" class="btn btn-default">Close</button>
                    <button type="submit" class="btn btn-primary">Send</button>
                </div>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    var msg = 0, warn = 0, feed = 0, comp = 0, sent = 0;
    function refresh() {
        makeNotification('Getting New Messages', '#00cc99');
        $.post('GetMessages', {msg: msg, warn: warn, feed: feed, comp: comp, sent: sent}, function(data, textStatus, jqXHR) {
            if (textStatus === 'success') {
                var status = data[0].status;

                var messages = data[0].messages;
                var warnings = data[0].warnings;
                var feedbacks = data[0].feedbacks;
                var complaints = data[0].complaints;

                $('#new-message').html(messages);
                $('#warning').html(warnings);
                $('#feedback').html(feedbacks);
                $('#complaint').html(complaints);

                if (status === 'ERROR') {
                    makeNotification('Error while getting Messages !', 'tomato');
                }

            }
        }, 'json');
    }
    $(function() {
        $('#prior-btns button').click(function() {
            $('#prior-btns button').removeClass('disabled');
            $(this).addClass('disabled');
        });

        $('#msg-tabs a').click(function(e) {
            e.preventDefault();
            $(this).tab('show');
        });

        $('#new-msg').on('shown.bs.modal', function() {
            $('.chosen-select').chosen();
        });

    });

</script>