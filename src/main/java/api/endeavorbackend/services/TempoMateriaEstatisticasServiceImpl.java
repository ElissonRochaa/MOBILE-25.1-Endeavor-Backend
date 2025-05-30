package api.endeavorbackend.services;

import api.endeavorbackend.repositorios.TempoMateriaRepository;
import org.springframework.stereotype.Service;
import api.endeavorbackend.models.TempoMateria;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TempoMateriaEstatisticasServiceImpl implements TempoMateriaEstatisticaService {
    private final TempoMateriaRepository tempoMateriaRepository;

    public TempoMateriaEstatisticasServiceImpl(TempoMateriaRepository tempoMateriaRepository) {
        this.tempoMateriaRepository = tempoMateriaRepository;
    }

    public long getTempoTotalPorMateria(Long usuarioId, Long materiaId) {
        return tempoMateriaRepository.findByMateriaAndUsuario(materiaId, usuarioId)
                .stream()
                .mapToLong(TempoMateria::getTempoTotalAcumulado)
                .sum();
    }

    public long getTempoTotalNoDia(Long usuarioId, LocalDate date) {
        return tempoMateriaRepository.findByUsuarioAndDate(usuarioId, date)
                .stream()
                .mapToLong(TempoMateria::getTempoTotalAcumulado)
                .sum();
    }

    public long getTempoTotalNoDiaPorMateria(Long usuarioId, Long materiaId, LocalDate date) {
        return tempoMateriaRepository.findByMateriaAndUsuarioAndDate(materiaId, usuarioId, date)
                .stream()
                .mapToLong(TempoMateria::getTempoTotalAcumulado)
                .sum();
    }

    public long getTempoNaSemana(Long usuarioId, LocalDate inicioSemana, LocalDate fimSemana) {
        return tempoMateriaRepository.findByUsuarioAndSemana(
                        usuarioId,
                        inicioSemana.atStartOfDay(),
                        fimSemana.atTime(23, 59, 59)
                )
                .stream()
                .mapToLong(TempoMateria::getTempoTotalAcumulado)
                .sum();
    }

    public long getTempoNaSemanaPorMateria(Long usuarioId, Long materiaId, LocalDate inicio, LocalDate fim) {
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

    public List<Long> getEvolucaoSemanal(Long usuarioId, LocalDate inicio, LocalDate fim) {
        List<Long> evolucao = new ArrayList<>();
        LocalDate semanaInicio = inicio;

        while (!semanaInicio.isAfter(fim)) {
            LocalDate semanaFim = semanaInicio.plusDays(6);
            if (semanaFim.isAfter(fim)) {
                semanaFim = fim;
            }
            long tempo = getTempoNaSemana(usuarioId, semanaInicio, semanaFim);
            evolucao.add(tempo);
            semanaInicio = semanaFim.plusDays(1);
        }

        return evolucao;
    }

    public int getDiasConsecutivosDeEstudo(Long usuarioId, long tempoMinimoDiarioSegundos) {
        LocalDate hoje = LocalDate.now();
        int diasConsecutivos = 0;

        while (true) {
            LocalDate dia = hoje.minusDays(diasConsecutivos);
            long tempoNoDia = getTempoTotalNoDia(usuarioId, dia);
            if (tempoNoDia >= tempoMinimoDiarioSegundos) {
                diasConsecutivos++;
            } else {
                break;
            }
        }

        return diasConsecutivos;
    }


}
