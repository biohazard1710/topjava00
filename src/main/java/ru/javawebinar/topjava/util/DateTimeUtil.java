package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static final LocalDateTime MIX_DATE = LocalDateTime.of(1, 1, 1, 0, 0);
    private static final LocalDateTime MAX_DATE = LocalDateTime.of(9999, 1, 1, 0, 0);

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T value, T start, T end) {
        return (start == null || value.compareTo(start) >= 0 && (end == null || value.compareTo(end) < 0));
    }

    public static LocalDateTime getStartInclusive(LocalDate localDate) {
        return localDate != null ? localDate.atStartOfDay() : MIX_DATE;
    }

    public static LocalDateTime getEndExclusive(LocalDate localDate) {
        return localDate != null ? localDate.plus(1, ChronoUnit.DAYS).atStartOfDay() : MAX_DATE;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

