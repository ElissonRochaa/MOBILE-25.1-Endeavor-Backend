package api.endeavorbackend.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

public interface TempoMateriaEstatisticaService {

    long getTempoTotalPorMateria(UUID usuarioId, UUID materiaId);

    long getTempoTotalNoDia(UUID usuarioId, LocalDate date);

    long getTempoTotalNoDiaPorMateria(UUID usuarioId, UUID materiaId, LocalDate date);

    long getTempoNaSemana(UUID usuarioId, LocalDate inicioSemana, LocalDate fimSemana);

    long getTempoNaSemanaPorMateria(UUID usuarioId, UUID materiaId, LocalDate inicioSemana, LocalDate fimSemana);

    List<Long> getEvolucaoPorPeriodo(UUID usuarioId, LocalDate inicio, LocalDate fim, ChronoUnit unidade, int intervalo);

    int getDiasConsecutivosDeEstudo(UUID usuarioId, long tempoMinimoDiarioSegundos);
}

