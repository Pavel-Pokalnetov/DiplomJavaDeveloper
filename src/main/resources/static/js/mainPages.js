var countDown = 300;
function countdown() {
    setInterval(function () {
        if (countDown == 0) {
            location.reload();
        }else {
            countDown--;
            document.getElementById('refreshCounter').innerHTML = countDown;
        }
    }, 1000);
}
countdown();

window.onload = function(){
    window.setInterval(function(){
        var options = {
            year: 'numeric',
            month: 'long',
            day: 'numeric',
            timezone: 'UTC'
        };
        var currentDate = new Date()
        var clock = document.getElementById("current-clock");
        var date  =  document.getElementById("current-date");
        // clock.innerHTML = currentDate.toLocaleDateString("ru",options);


        date.innerHTML = currentDate.toLocaleTimeString("ru",options);
    },100);


};