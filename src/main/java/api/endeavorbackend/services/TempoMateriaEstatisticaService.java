package api.endeavorbackend.services;

import api.endeavorbackend.models.DTOs.EvolucaoDTO;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

public interface TempoMateriaEstatisticaService {

    long getTempoTotalPorMateria(UUID usuarioId, UUID materiaId);

    long getTempoTotalNoDia(UUID usuarioId, LocalDate date);

    long getTempoTotalNoDiaPorMateria(UUID usuarioId, UUID materiaId, LocalDate date);

    double getTempoNaSemana(UUID usuarioId, LocalDate inicioSemana, LocalDate fimSemana);

    double getTempoNaSemanaPorMateria(UUID usuarioId, UUID materiaId, LocalDate inicioSemana, LocalDate fimSemana);

    List<EvolucaoDTO> getEvolucaoPorPeriodo(UUID usuarioId, LocalDate inicio, LocalDate fim, ChronoUnit unidade, int intervalo);

    int getDiasConsecutivosDeEstudo(UUID usuarioId, long tempoMinimoDiarioSegundos);
}

