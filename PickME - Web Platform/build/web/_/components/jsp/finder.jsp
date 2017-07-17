<div class="row col-lg-12">

    <div>
        <h2>Find your closest city...</h2>
        <input id="inputCity" class="form-control input-lg" type="text" id="findCity" placeholder="Find your city..."/>
    </div>
    <br>
    <div id="recentCities">
        <div>
            <label>Recent : </label>
            <button class="btn btn-info">Colombo</button>
            <button class="btn btn-info">Kandy</button>
            <button class="btn btn-info">Negambo</button>
            <button class="btn btn-info">Kurunegala</button>
            <button class="btn btn-info">Katunayake</button>
            <small><a href="#">(clear recent)</a></small>
        </div>
    </div><!--popularCities-->
    <br>
    <table class="table table-hover col-sm-12" id="cities"></table><!--cities-->

    <div style="text-align: center">
        <img src="" id="loaderImg" style="max-width: 50px;display: none;">
    </div>
    <div id="noResult" class="alert alert-info" style="display: none;"><b>Sorry !</b> no result found !...</div>
</div>