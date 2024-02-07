package ru.slenergo.AppMonitoring.services.exceptions;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Исключение для перехвата ситуации повторного ввода данных в текущем часе
 */
public class PrematureEntryException extends Exception {
    @Getter
    String message;
    public PrematureEntryException(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu HH:mm");
        message = "Данные за текущий час уже были переданы.\n" +
                "Последние данные переданы "+date.format(formatter);
    }

}
