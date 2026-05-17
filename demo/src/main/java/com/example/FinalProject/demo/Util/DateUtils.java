package com.example.FinalProject.demo.Util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static String formatear(LocalDate fecha, String patron) {
        return fecha.format(DateTimeFormatter.ofPattern(patron));
    }
}
