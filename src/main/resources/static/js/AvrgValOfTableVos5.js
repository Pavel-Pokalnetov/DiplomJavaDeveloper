function calcAvrfValue() {
// расчет статистики для таблицы dataTable
    let table = document.getElementById("dataTable");

// Получаем количество строк и столбцов
    let rows = table.rows.length;
    let cols = table.rows[0].cells.length;

    var newRow = table.insertRow(-1);
    newRow.classList.add('table-secondary')
    let newCell = newRow.insertCell(0);

// Для каждого столбца
    for (let j = 1; j < cols; j++) {
        let sum = 0;
        let count = 0;

        // Суммируем все значения в столбце
        for (let i = 0; i < rows; i++) {
            let cellValue = parseFloat(table.rows[i].cells[j].innerText);
            if (!isNaN(cellValue)) {
                sum += cellValue;
                count++;
            }
        }

        var avg = 0;
        switch (true) {
            case (j > 7 || j===6):
                avg = sum / count;
                break
            default:
                avg = sum;
        }

        // Вычисляем среднее значение


        // Создаем новую ячейку и добавляем ее в конец каждого столбца

        let newCell = newRow.insertCell(j);
        if (j > 8) {
            newCell.innerText = avg.toFixed(2);
        } else {
            newCell.innerText = avg.toFixed(1);
        }
    }
}

window.onload = calcAvrfValue;


