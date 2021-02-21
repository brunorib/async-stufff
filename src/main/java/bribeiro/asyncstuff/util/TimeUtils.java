package bribeiro.asyncstuff.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class TimeUtils {

    public static String getDateNormalFormat() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static String getDateDaysFromNowNormalFormat(int numberDays) {
        return LocalDateTime.ofInstant(Instant.now().minus(numberDays, ChronoUnit.DAYS), ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

}
