<div class="row">
    <div class="panel panel-info">
        <div class="panel-heading">
            <h4 class="panel-title">Administrator Login</h4>
        </div>
        <div class="panel-body">
            <div class="col-sm-5">
                <form id="admin-login-form" class="form-horizontal" role="form" method="post" action="checkAdminLogin">
                    <div class="form-group">
                        <label for="uname" class="col-sm-2 control-label">Username</label>
                        <div class="col-sm-10">
                            <input class="form-control" type="email" name="uname" id="uname" placeholder="Username" required="required"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label accesskey="pword" class="col-sm-2 control-label">Password</label>
                        <div class="col-sm-10">
                            <input class="form-control" type="password" name="pword" id="pword" placeholder="Password" required="required"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <div id="admin-login-loader" class="pull-left hide">
                                <img src="images/other/loader.gif">
                                <label>Please wait...</label>
                            </div>
                            <button id="admin-login-btn" class="btn btn-primary pull-right">Sign in</button>
                        </div>
                    </div>
                    <div id="admin-login-msg"></div>
                </form>
            </div>
        </div>
    </div>
</div>