package api.endeavorbackend.utils;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class SemanaUtils {
    public static LocalDate getInicioSemana(LocalDate data) {
        return data.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
    }

    public static LocalDate getFimSemana(LocalDate data) {
        return data.with(TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY));
    }
}

