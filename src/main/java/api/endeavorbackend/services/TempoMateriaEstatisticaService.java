package api.endeavorbackend.services;

import java.time.LocalDate;

public interface TempoMateriaEstatisticaService {

    long getTempoTotalPorMateria(Long usuarioId, Long materiaId);

    long getTempoTotalNoDia(Long usuarioId, LocalDate date);

    long getTempoTotalNoDiaPorMateria(Long usuarioId, Long materiaId, LocalDate date);

    long getTempoNaSemana(Long usuarioId, LocalDate inicioSemana, LocalDate fimSemana);

    long getTempoNaSemanaPorMateria(Long usuarioId, Long materiaId, LocalDate inicioSemana, LocalDate fimSemana);
}

