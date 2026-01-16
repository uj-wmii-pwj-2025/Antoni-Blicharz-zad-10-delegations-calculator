package uj.wmii.pwj.delegations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Calc {

    BigDecimal calculate(String name, String start, String end, BigDecimal dailyRate) throws IllegalArgumentException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm VV");
        ZonedDateTime startTime = ZonedDateTime.parse(start, formatter);
        ZonedDateTime endTime = ZonedDateTime.parse(end, formatter);
        if (startTime.compareTo(endTime) > 0)
            return BigDecimal.ZERO.setScale(2);

        Duration time = Duration.between(startTime, endTime);

        BigDecimal rate = dailyRate.multiply(BigDecimal.valueOf(time.toDays()));
        time = time.minusDays(time.toDays());


        if (time.toMinutes() == 0)
            return rate;
        else if (time.toHours() < 8)
            return rate.add(dailyRate.divide(BigDecimal.valueOf(3), 2, RoundingMode.HALF_UP));
        else if (time.toHours() < 12)
            return rate.add(dailyRate.divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP));
        else
            return rate.add(dailyRate);
    }
}
