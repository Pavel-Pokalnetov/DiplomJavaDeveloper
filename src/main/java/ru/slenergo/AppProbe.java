package ru.slenergo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppProbe {
    public static void main(String[] args) {

        // Получаем текущее время, обрезанное до дня
        LocalDateTime now = LocalDateTime.now().truncatedTo(java.time.temporal.ChronoUnit.DAYS);

        // Создаем список времен с разницей в 1 час
        List<LocalDateTime> timeList = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            timeList.add(now.plusHours(i));
        }

        // Выводим список времен
        for (LocalDateTime t : timeList) {
            System.out.println(t);
        }
    }
}

