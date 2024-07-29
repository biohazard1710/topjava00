package ru.javawebinar.topjava.util;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StringToLocalDateConverter implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(String source) {
        return (source == null || source.isEmpty()) ? null : LocalDate.parse(source, DateTimeFormatter.ISO_DATE);
    }

}
