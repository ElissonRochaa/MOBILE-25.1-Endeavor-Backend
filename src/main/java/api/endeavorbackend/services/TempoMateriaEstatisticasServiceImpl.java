package api.endeavorbackend.services;

import api.endeavorbackend.repositorios.TempoMateriaRepository;
import org.springframework.stereotype.Service;
import api.endeavorbackend.models.TempoMateria;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
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

    public List<Long> getEvolucaoPorPeriodo(UUID usuarioId, LocalDate inicio, LocalDate fim, ChronoUnit unidade, int intervalo) {
        List<Long> evolucao = new ArrayList<>();
        LocalDate atual = inicio;

        while (!atual.isAfter(fim)) {
            LocalDate subFim = switch (unidade) {
                case DAYS -> atual.plusDays(intervalo - 1);
                case WEEKS -> atual.plusWeeks(intervalo - 1);
                case MONTHS -> atual.plusMonths(intervalo - 1);
                default -> throw new IllegalArgumentException("Unidade de tempo não suportada: " + unidade);
            };

            if (subFim.isAfter(fim)) {
                subFim = fim;
            }

            long tempo = getTempoNaSemana(usuarioId, atual, subFim);
            evolucao.add(tempo);

            atual = switch (unidade) {
                case DAYS -> atual.plusDays(intervalo);
                case WEEKS -> atual.plusWeeks(intervalo);
                case MONTHS -> atual.plusMonths(intervalo);
                default -> atual;
            };
        }

        return evolucao;
    }


    public int getDiasConsecutivosDeEstudo(UUID usuarioId, long tempoMinimoDiarioSegundos) {
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
