<%@page import="Db.Administrator"%>
<%@page import="Model.*" %>
<div class="container-fluid middle-bg navbar-inverse">

    <div class="dropdown visible-xs pull-left">

        <button id="menuLabel" title="Menu" class="btn btn-invisible navbar-btn dropdown-toggle" data-toggle="dropdown" aria-expanded="true">
            <span class="glyphicon glyphicon-chevron-down" aria-hidden="true"></span>
        </button><!-- dropdown toggle btn -->

        <ul class="dropdown-menu" aria-labelledby="menuLabel">
            <li><a href="findServices.jsp"><span class="glyphicon glyphicon-search" aira-hidden="true"></span> Find Services</a></li>
            <li><a href="shoppingCart.jsp"><span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span> My Cart<span class="badge pull-right"><%=Model.Cart.getCurrentCartItemCount(session)%></span></a></li>
        </ul><!-- dropdown menu -->

    </div>

    <div class="hidden-xs">
        <ul class="nav navbar-nav">
            <li title="Find Services" data-toggle="tooltip" data-placement="bottom"><a href="findServices.jsp"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></a></li>
            <li title="<%=Model.Cart.getCurrentCartItemCount(session)%> Items" data-toggle="tooltip" data-placement="bottom"><a href="shoppingCart.jsp"><span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span></a></li>
        </ul>
    </div>

    <!--LOGGED CONTROLS START-->
    <%if (session.getAttribute("CURRENT_USER") != null) {
            Db.LoginDetails details = (Db.LoginDetails) session.getAttribute("CURRENT_USER");
    %>
    <div class="dropdown pull-right">
        <button id="uLabel" type="button" data-toggle="dropdown" aria-expanded="true" class="btn btn-invisible navbar-btn dropdown-toggle"><span class="glyphicon glyphicon-user" aria-hidden="true"></span> ${fname} <span class="caret"></span></button>

        <ul class="dropdown-menu" role="menu" aria-labelledby="uLabel">
            <%if (!details.getUsers().isEmpty()) {%>
            <li><a href="shoppingCart.jsp"><%=HTML.getIcon("shopping-cart")%> My Cart</a></li>
            <li><a href="clientProfile.jsp"><%=Model.HTML.getIcon("user")%> Profile</a></li>
                <%} else {%>
            <li><a href="Dashboard"><%=HTML.getIcon("dashboard")%> Dashboard</a></li>
                <%}%>
            <li class="divider"></li>
            <li><a href="Logout"><%=HTML.getIcon("log-out")%> Logout</a></li>
        </ul>
    </div>
    <!--LOGGED CONTROLS END-->
    <%} else {%>
    <!--LOG IN BUTTON START-->
    <div>
        <button onclick="showLoginDialog()" type="button" class="btn btn-invisible pull-right navbar-btn">Login</button><!-- login button -->
    </div><!-- login form -->
    <!--LOG IN BUTTON END-->
    <%}%>
</div><!-- middle panel -->