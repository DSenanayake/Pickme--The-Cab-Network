<div style="margin-top: 20px;margin-left:10px;">
    <div class="well row">
        <div class="col-md-3">
            <label>Sort</label>
            <select id="sort-by" class="form-control">
                <option value="NAME">Name</option>
                <option value="COST_LOW">Cost : Lowest First</option>
                <option value="COST_HIGH">Cost : Highest First</option>
            </select>
        </div>
        <div class="col-md-offset-5 col-md-4">
            <label>Availability</label>
            <div id="ser-availability" class="btn-group" role="group" aria-label="Availability">
                <button type="button" class="btn btn-default active">All</button>
                <button type="button" class="btn btn-default">Online</button>
                <button type="button" class="btn btn-default">Offline</button>
            </div>
        </div>
    </div>

    <div class="row custom-filter-result" id="filter-result">
    </div>
    <div id="result-pagination" class="row well well-sm" style="margin-top: 20px"></div>
</div>

