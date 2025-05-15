package api.endeavorbackend.services;

import api.endeavorbackend.models.TempoMateria;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TempoMateriaService {
    TempoMateria iniciarSessao(Long usuarioId, Long materiaId);

    TempoMateria pausarTempoMateria(Long id);

    TempoMateria continuarTempoMateria(Long id);

    TempoMateria finalizarTempoMateria(Long id);

    void deleteTempoMateria(TempoMateria tempoMateria);

    List<TempoMateria> listar();

    Optional<TempoMateria> buscar(Long id);

    Long getTotalTempoMateria(Long idMateria);

    Long getTempoNoDia(LocalDate date);

    Long getTempoNoDiaPorMateria(Long idMateria, LocalDate date);

    Long getTempoNaSemana(LocalDate inicioSemana, LocalDate fimSemana);

    Long getTempoNaSemanaPorMateria(Long idMateria, LocalDate inicioSemana, LocalDate fimSemana);

    Long getDuracaoSessaoEstudo(Long materia);

    Long getTempoTotalAcumulado(Long id);
}
