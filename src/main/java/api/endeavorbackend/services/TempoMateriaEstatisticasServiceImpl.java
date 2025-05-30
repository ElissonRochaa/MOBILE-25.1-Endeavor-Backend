package api.endeavorbackend.services;

import api.endeavorbackend.repositorios.TempoMateriaRepository;
import org.springframework.stereotype.Service;
import api.endeavorbackend.models.TempoMateria;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class TempoMateriaEstatisticasServiceImpl implements TempoMateriaEstatisticaService {
    private final TempoMateriaRepository tempoMateriaRepository;

    public TempoMateriaEstatisticasServiceImpl(TempoMateriaRepository tempoMateriaRepository) {
        this.tempoMateriaRepository = tempoMateriaRepository;
    }

    public long getTempoTotalPorMateria(UUID usuarioId, UUID materiaId) {
        return tempoMateriaRepository.findByMateriaAndUsuario(materiaId, usuarioId)
                .stream()
                .mapToLong(TempoMateria::getTempoTotalAcumulado)
                .sum();
    }

    public long getTempoTotalNoDia(UUID usuarioId, LocalDate date) {
        return tempoMateriaRepository.findByUsuarioAndDate(usuarioId, date)
                .stream()
                .mapToLong(TempoMateria::getTempoTotalAcumulado)
                .sum();
    }

    public long getTempoTotalNoDiaPorMateria(UUID usuarioId, UUID materiaId, LocalDate date) {
        return tempoMateriaRepository.findByMateriaAndUsuarioAndDate(materiaId, usuarioId, date)
                .stream()
                .mapToLong(TempoMateria::getTempoTotalAcumulado)
                .sum();
    }

    public long getTempoNaSemana(UUID usuarioId, LocalDate inicioSemana, LocalDate fimSemana) {
        return tempoMateriaRepository.findByUsuarioAndSemana(
                        usuarioId,
                        inicioSemana.atStartOfDay(),
                        fimSemana.atTime(23, 59, 59)
                )
                .stream()
                .mapToLong(TempoMateria::getTempoTotalAcumulado)
                .sum();
    }

    public long getTempoNaSemanaPorMateria(UUID usuarioId, UUID materiaId, LocalDate inicio, LocalDate fim) {
        return tempoMateriaRepository.findByMateriaAndUsuarioAndSemana(
                        materiaId,
                        usuarioId,
                        inicio.atStartOfDay(),
                        fim.atTime(23, 59, 59)
                )
                .stream()
                .mapToLong(TempoMateria::getTempoTotalAcumulado)
                .sum();
    }
}
