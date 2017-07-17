<%@page import="Model.*" %>
<div>
    <h4 class="page-header">Welcome Back, ${fname}</h4>
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title"></h3>
        </div>
        <div class="panel-body">
            <form>
                <div class="form-group" style="text-align: center">
                    <div class="img-circle" style="overflow: hidden">
                        <img class="img-thumbnail img-circle" alt="Profile Picture" src="images/profiles/default.png"  style="width: 100%;max-width: 150px">
                    </div>
                    <div>
                        <button type="submit" class="btn btn-sm btn-info">
                            <!--<input type="file" class="profile-change"/>Save-->
                        </button>
                    </div>
                </div>
            </form>
        </div>
        <ul class="list-group">
            <a href="#" class="list-group-item"><%=HTML.getIcon("user")%> Profile</a>
            <a href="#" class="list-group-item"><%=HTML.getIcon("time")%> Recent Orders</a>
            <a href="#" class="list-group-item"><%=HTML.getIcon("map-marker")%> My Places</a>
            <a href="#" class="list-group-item"><%=HTML.getIcon("usd")%> Pay Later Service</a>
        </ul>
        <div class="panel-footer">
            <h5><a href="Logout" class="btn btn-sm btn-danger"><%=HTML.getIcon("off")%> Logout</a></h5>
        </div>
    </div>
</div>