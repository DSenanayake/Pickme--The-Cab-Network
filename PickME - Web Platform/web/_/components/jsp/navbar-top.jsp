<div class="navbar navbar-inverse navbar-custom navbar-static-top">
    <div class="container-fluid">

        <div class="navbar-header">
            <a class="navbar-brand"  href="http://localhost:8080/Pickme.lk">
                <img width="32px" src="images/favicon/default.png" alt="Pickme.lk">
                Pickme.lk
            </a>
        </div>
        
        <button class="navbar-toggle" data-toggle="collapse" data-target=".navHeaderCollapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>

        <div class="collapse navbar-collapse navHeaderCollapse">

            <ul class="nav navbar-nav navbar-right">
                <li class="active"><a href="http://localhost:8080/Pickme.lk"><span class="glyphicon glyphicon-home" aria-hidden="true"></span> Home</a></li>
                <li><a href="#Register" data-toggle="modal" data-target="#registerModal"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Register</a></li>
<!--                <li><a href="#"><span class="glyphicon glyphicon-question-sign" aria-hidden="true"></span> Support</a></li>
                <li><a href="#"><span class="glyphicon glyphicon-envelope" aria-hidden="true"></span> Contact</a></li>-->
            </ul><!-- navigators -->

        </div><!-- collapse -->

    </div><!-- container -->
</div><!-- navbar -->

<div class="modal fade" id="registerModal" tabindex="-1" role="dialog" aria-hidden="true" aria-labelled-by="">

    <div class="modal-dialog">

        <div class="modal-content">

            <div class="modal-header">

                <button type="button"  class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button><!-- close btn -->
                <h4 class="modal-title">Connect with Us Today...</h4><!-- modal title -->

            </div><!-- modal header -->

            <div class="modal-body">
                <div class="row">
                    <div class="col-sm-6">
                        <div class="box-md custom-radio-btn radio-active" data-membership="client">
                            <!-- <img src="images/users/inv.png"> -->
                            <div class="custom-img img-client"></div><!-- client image -->
                            <h4>Client <a href="#"><span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span></a></h4>
                            <div id="client" class="selected-tag"><span class="glyphicon glyphicon-ok"></span></div><!-- selected tag -->
                        </div><!-- custom radio button 1 -->
                    </div><!-- column -->

                    <div class="col-sm-6">
                        <div class="box-md custom-radio-btn" data-membership="service">
                            <!-- <img src="images/users/sp.png"> -->
                            <div class="custom-img img-service"></div><!-- service image -->
                            <h4>Cab Service <a href="#"><span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span></a></h4>
                            <div id="service" class="selected-tag"><span class="glyphicon glyphicon-ok"></span></div><!-- selected tag -->
                        </div><!-- custom radio button 2-->
                    </div><!-- column -->
                </div><!-- row -->
                <a href="UserConfirmation.jsp">Verify Account</a>
            </div><!-- modal body -->

            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="redirectUser()">Register <span class="glyphicon glyphicon-ok" aria-hidden="true"></span></button>
            </div><!-- modal footer -->

        </div><!-- modal content -->

    </div><!-- modal dialog -->

</div><!-- register modal -->