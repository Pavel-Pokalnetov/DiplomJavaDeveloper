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


anychart.onDocumentReady(function () {

    // Получаем элемент таблицы
    var table = document.getElementById('dataTable');

// Создаем массив для хранения данных
    var data = [];

// Проходим по каждой строке таблицы
    for (var i = 0; i < table.rows.length; i++) {
        // Создаем новый массив для хранения данных строки
        var rowData = [];

        // Проходим по каждой ячейке строки
        for (var j = 0; j < table.rows[i].cells.length; j++) {
            // Добавляем значение ячейки в массив данных строки
            rowData.push(table.rows[i].cells[j].innerText);
        }

        // Добавляем массив данных строки в общий массив
        data.push(rowData);
    }


    // создайте набор данных
    var dataSet = anychart.data.set(data);

    // сопоставьте данные для всех серий
    var firstSeriesData = dataSet.mapAs({x: 0, value: 1});
    var secondSeriesData = dataSet.mapAs({x: 0, value: 2});
    var thirdSeriesData = dataSet.mapAs({x: 0, value: 3});
    var fourthSeriesData = dataSet.mapAs({x: 0, value: 4});

    // создайте линейную диаграмму
    var chart = anychart.line();

    // создайте серии и назовите их
    var firstSeries = chart.line(firstSeriesData);
    firstSeries.name("Добыча воды со скважин");
    var secondSeries = chart.line(secondSeriesData);
    secondSeries.name("Подача в город");
    var thirdSeries = chart.line(thirdSeriesData);
    thirdSeries.name("Запас в РЧВ");
    var fourthSeries = chart.line(fourthSeriesData);
    fourthSeries.name("Рост запаса в РЧВ");

    // добавьте легенду
    chart.legend().enabled(true);

    // добавьте заголовок
    chart.title("Сводный график ");

    // назовите оси
    chart.yAxis().title("М³");
    chart.xAxis().title("Время");

    // настройка маркеров серий
    firstSeries.hovered().markers().enabled(true).type("circle").size(4);
    secondSeries.hovered().markers().enabled(true).type("circle").size(4);
    thirdSeries.hovered().markers().enabled(true).type("circle").size(4);
    fourthSeries.hovered().markers().enabled(true).type("circle").size(4);


    // включите перекрестие
    chart.crosshair().enabled(true).yStroke(null).yLabel(false);

    // измените положение всплывающей подсказки
    chart.tooltip().positionMode("point");
    chart.tooltip().position("right").anchor("left-center").offsetX(5).offsetY(5);

    // укажите, где будет отображаться диаграмма
    chart.container("graphic-container");

    // нарисуйте получившуюся диаграмму
    chart.draw();

});