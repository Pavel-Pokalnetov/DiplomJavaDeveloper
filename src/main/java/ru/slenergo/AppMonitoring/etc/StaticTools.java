package ru.slenergo.AppMonitoring.etc;

import java.math.BigDecimal;

/**
 * Класс вспомогательных методов
 */
public class StaticTools {
    /**
     * Метод усечения дробной части числа
     *
     * @param number - исходное число
     * @param round  - число знаков после запятой после усечения
     * @return число типа Double
     */
    public static Double dropSmallDecimalPart(Double number, int round) {
        BigDecimal bd = new BigDecimal(number);
        bd = bd.setScale(round, BigDecimal.ROUND_DOWN);
        return (Double) (bd.doubleValue());
    }
}