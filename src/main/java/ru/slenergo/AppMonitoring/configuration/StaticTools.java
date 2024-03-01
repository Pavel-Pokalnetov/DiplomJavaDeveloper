package ru.slenergo.AppMonitoring.configuration;

import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;


/**
 * Класс вспомогательных методов
 */
public class StaticTools {

    /** Метод округления числа до 0.1
     * @param number
     * @return число Double округленное до 0.1
     */
    public static Double dropSmallDecimalPart(Double number,int round) {
        BigDecimal bd = new BigDecimal(number);
        bd = bd.setScale(round, BigDecimal.ROUND_DOWN);
        return bd.doubleValue();
    }
}
