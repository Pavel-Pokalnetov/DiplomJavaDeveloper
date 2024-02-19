var countDown = 1800;
function countdown() {
    setInterval(function () {
        if (countDown == 0) {
            location.reload("/report/summary/update");
        }else {
            countDown--;
            document.getElementById('refreshCounter').innerHTML = countDown;
        }
    }, 1000);
}
countdown();

