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

anychart.onDocumentReady(function () {

    // add data
    var data = [
        ["00:00", 300, 0, 0, 0],
        ["01:00", 143, 0, 0, 0],
        ["02:00", 13, 0, 0, 0],
        ["03:00", 9, 0, 1, 20],
        ["04:00", 11, 0, 2,120],
        ["05:00", -12, 0, 5, 1],
        ["06:00", -15, 0, 6, 1],
        ["07:00", 16, 0, 9, 1],
        ["08:00", 16, 0, 10, 4],
        ["09:00", 17, 0, 11, 5],
        ["10:00", 17, 0, 13, 6],
        ["11:00", 17, 0, 14, 7],
        ["12:00", 17, 0, 14, 10],
        ["13:00", 17, 0, 14, 12],
        ["14:00", 19, 0, 16, 12],
        ["15:00", 20, 0, 17, 14],
        ["16:00", 20, 0, 19, 16],
        ["17:00", 20, 0, 20, 17],
        ["18:00", 20, 0, 20, 20],
        ["19:00", 20, 0, 22, 20],
        ["20:00", 20, 0, 22, 20],
        ["21:00", 20, 0, 22, 20],
        ["22:00", 20, 0, 22, 20],
        ["23:00", 20, 0, 22, 20]
    ];

    // создайте набор данных
    var dataSet = anychart.data.set(data);

    // сопоставьте данные для всех серий
    var firstSeriesData = dataSet.mapAs({x: 0, value: 1});
    var secondSeriesData = dataSet.mapAs({x: 0, value: 2});
    var thirdSeriesData = dataSet.mapAs({x: 0, value: 3});
    var fourthSeriesData = dataSet.mapAs({x:0,value:4});

    // создайте линейную диаграмму
    var chart = anychart.line();

    // создайте серии и назовите их
    var firstSeries = chart.line(firstSeriesData);
    firstSeries.name("Добыча воды со скважин");
    var secondSeries = chart.line(secondSeriesData);
    secondSeries.name("Подача в город");
    var thirdSeries = chart.line(thirdSeriesData);
    thirdSeries.name("Запас в РЧВ");
    var fourthSeries =  chart.line(fourthSeriesData);
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

