<%@taglib prefix="s" uri="Service" %>
<div style="margin-bottom:50px;">
    <div id="membershipPlan">
        <h2 class="page-header">Select Membership Plan</h2>

        <!--MEMBERSHIP ROW START-->
        <div class="row">
            <%
                java.util.List<Db.MembershipPlan> plans = new Model.MembershipPlan().getMembershipPlans();
                for (Db.MembershipPlan p : plans) {
            %>
            <div class="col-sm-4">
                <div class="bg-lite custom-member-plan">
                    <div class="custom-content">
                        <h3 class="page-header"><%= p.getMembershipPlanName()%></h3>
                        <ul style="text-align:left;" class="list-unstyled">
                            <li>Drivers<span class="badge pull-right"><%= p.getDrivers() == -1 ? "Unlimited" : p.getDrivers()%></span></li>
                            <li>Cabs<span class="badge pull-right"><%= p.getCabs() == -1 ? "Unlimited" : p.getCabs()%></span></li>
                            <li>Agreements<span class="badge pull-right"> None </span></li>
                        </ul>
                        <select class="duration-plan form-control">
                            <s:Durations plan="<%=p.getMembershipPlanId()%>">
                                <option value="${id}">${months} Month(s) - Rs : ${price}</option>
                            </s:Durations>
                        </select>
                    </div>
                    <div class="pkg-price-tag">
                        <label>Price - <%= p.getPerMonth() > 0 ? "Rs: " + p.getPerMonth() + " per Month" : "<label class='label label-danger'>Free</label>"%></label>
                    </div>
                    <button class="center-block btn btn-lite btn-block select-plan" data-plan-id="<%= p.getMembershipPlanId()%>" data-plan-name="<%= p.getMembershipPlanName()%>" data-price="<%= p.getPerMonth()%>">Select</button>
                </div>
            </div>
            <%}%>
        </div>
        <!--MEMBERSHIP ROW END-->
    </div>

    <div id="regDetails" style="display:none">
        <h2 class="page-header">Registration</h2>
        <div id="serviceContactDetails" class="form-horizontal">
            <legend>Contact Details</legend>
            <div id="groupServiceName" class="form-group">
                <label class="control-label col-sm-3" for="serviceName">Cab Service Name <small class="text text-danger">*</small></label>

                <div class="col-sm-9">
                    <input type="text" id="serviceName" name="serviceName" placeholder="Cab Service Name" class="form-control" autofocus />
                </div>
            </div>
            <div id="groupServiceEmail" class="form-group">
                <label class="col-sm-3 control-label"  for="serviceEmail">E-Mail <small class="text text-danger">*</small></label>

                <div class="col-sm-9">
                    <input type="email" id="serviceEmail" name="serviceEmail" placeholder="E-Mail" class="form-control" />
                </div>
            </div>
            <div id="groupServiceTel" class="form-group">
                <label class="col-sm-3 control-label"  for="serviceTel">Tel <small class="text text-danger">*</small></label>

                <div class="col-sm-9">
                    <div class="input-group">
                        <div class="input-group-addon">+94</div>
                        <input type="tel" id="serviceTel" name="serviceTel" placeholder="Tel" class="form-control" max="9" />
                    </div>
                </div>
            </div>
            <legend>Membership Details</legend>
            <div class="form-group">
                <label class="col-sm-3 control-label">Membership Plan</label>

                <div class="col-sm-9">
                    <div><span id="plan-name">None</span> <a href="#" onclick="changePkg()"> (Change)</a></div>
                </div>
            </div>
            <div id="groupServiceDuration" class="form-group">
                <label for="serviceDuration" class="col-sm-3 control-label">Duration</label>

                <div class="col-sm-9">
                    <p class="form-control-static" id="serviceDurationMonths">Loading...</p>
                </div>
            </div>

            <div id="groupServicePrice" class="form-group">
                <label for="serviceDuration" class="col-sm-3 control-label">Price</label>

                <div class="col-sm-9">
                    <p class="form-control-static" id="serviceDurationPrice">Loading...</p>
                </div>
            </div>

            <div id="groupServiceAgree" class="form-group">
                <div class="col-sm-9 col-lg-offset-3">
                    <div class="checkbox">
                        <input type="checkbox" id="serviceAgreement" name="serviceAgreement" required=""/>
                        <label class="" for="serviceAgreement">I Agree the</label> <a onclick="showTermsAndCond()" href="#">Terms & Conditions.</a>
                    </div>
                </div>
            </div>
            <hr>
            <div class="form-group">
                <div class="col-sm-9 col-sm-offset-3">
                    <button class="btn btn-success pull-right" onclick="placeServiceOrder()" type="button">Place Order</button>
                </div>
            </div>
        </div>
    </div>

    <div id="reviewServiceOrder" style="display:none;">
        <h2 class="page-header">Review Order</h2>
        <legend>Review Service Details <small><a href="#editDetails" onclick="editDetails()">(Edit)</a></small></legend>
        <div class="form-group">
            <label class="col-sm-3" for="serviceName">Cab Service Name <span class="pull-right">:</span></label>

            <div class="col-sm-9">
                <p id="reviewServiceName">Service Name</p>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3"  for="serviceEmail">E-Mail <span class="pull-right">:</span></label>

            <div class="col-sm-9">
                <p id="reviewServiceEmail">E-Mail</p>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3"  for="serviceTel">Tel <span class="pull-right">:</span></label>

            <div class="col-sm-9">
                <p id="reviewServiceTel">+9488888888</p>
            </div>
        </div>

        <div>
            <legend>Membership Plan</legend>
            <img id="reviewLoader" src="images/other/cube.gif" width="32px" class="center-block" style="display: none">
            <div id="reviewTable">

            </div>
        </div>

        <hr>
        <div class="text text-warning">
            <i>Note:We'll Contact you within 4 hours after checkout to complete your Registration.</i>
        </div>
        <hr/>
        <button id="checkoutServiceBtn" title="Secure Checkout with Paypal" data-toggle="tooltip" data-placement="left" type="submit" class="btn btn-default pull-right" onclick="checkoutOrder()">Checkout <img src="images/logos/pp64.png" width="24px"></button>
    </div>

    <div id="savingOrder" style="display: none">
        <h2 class="page-header">Subscribing..</h2>
        <div class="alert alert-info">
            <h4 class="text-center">Please wait while Subscribing your Order...</h4>
            <div><img class="center-block" src="images/other/cube.gif" width="32px"></div>
        </div>
    </div>
</div>