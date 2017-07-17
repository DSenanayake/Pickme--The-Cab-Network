<div style="text-align: center">
    <h1 class="page-header">Restricted Area</h1>
    <img src="images/other/restricted.png" style="max-width: 300px">
    <h3>Sorry,You are not allowed to be here.</h3>
    <h4>Redirects in <span id="time-s">3</span> Sec(s).</h4>
</div>
<script type="text/javascript">
    var sec = 3;
    function setSec() {
        sec--;
        if (sec === 0) {
            document.location.href = 'index.jsp';
        } else {
            $('#time-s').html(sec);
        }
    }
    $(function() {
        setInterval(setSec, 1000);
    });
</script>