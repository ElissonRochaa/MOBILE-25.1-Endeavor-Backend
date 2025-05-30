package api.endeavorbackend.services;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TempoMateriaEstatisticaService {

    long getTempoTotalPorMateria(UUID usuarioId, UUID materiaId);

    long getTempoTotalNoDia(UUID usuarioId, LocalDate date);

    long getTempoTotalNoDiaPorMateria(UUID usuarioId, UUID materiaId, LocalDate date);

    long getTempoNaSemana(UUID usuarioId, LocalDate inicioSemana, LocalDate fimSemana);

    long getTempoNaSemanaPorMateria(UUID usuarioId, UUID materiaId, LocalDate inicioSemana, LocalDate fimSemana);

    List<Long> getEvolucaoSemanal(UUID usuarioId, LocalDate inicio, LocalDate fim);

    int getDiasConsecutivosDeEstudo(UUID usuarioId, long tempoMinimoDiarioSegundos);
}

