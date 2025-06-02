package api.endeavorbackend.services;

import api.endeavorbackend.models.DTOs.EvolucaoDTO;
import api.endeavorbackend.repositorios.TempoMateriaRepository;
import org.springframework.stereotype.Service;
import api.endeavorbackend.models.TempoMateria;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.time.DayOfWeek;

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
                .mapToLong(TempoMateria::getTempoTotalAcumulado).map(tempo -> tempo / (60 * 60))
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


    public List<EvolucaoDTO> getEvolucaoPorPeriodo(UUID usuarioId, LocalDate inicio, LocalDate fim, ChronoUnit unidade, int intervalo) {
        List<EvolucaoDTO> evolucao = new ArrayList<>();

        if (unidade == ChronoUnit.WEEKS) {
            LocalDate segunda = inicio.with(DayOfWeek.MONDAY);
            LocalDate domingo = segunda.plusDays(6);

            for (LocalDate dia = segunda; !dia.isAfter(domingo); dia = dia.plusDays(1)) {
                Long tempo = getTempoNaSemana(usuarioId, dia, dia);
                evolucao.add(new EvolucaoDTO(dia, tempo));
            }

            return evolucao;
        }

        LocalDate atual = inicio;

        while (!atual.isAfter(fim)) {
            LocalDate subFim;

            switch (unidade) {
                case DAYS -> subFim = atual;
                case MONTHS -> {
                    YearMonth ym = YearMonth.from(atual).plusMonths(intervalo - 1);
                    subFim = ym.atEndOfMonth();
                }
                default -> throw new IllegalArgumentException("Unidade de tempo não suportada: " + unidade);
            }

            if (subFim.isAfter(fim)) {
                subFim = fim;
            }

            long tempo = getTempoNaSemana(usuarioId, atual, subFim);
            evolucao.add(new EvolucaoDTO(atual, tempo));

            atual = switch (unidade) {
                case DAYS -> atual.plusDays(intervalo);
                case MONTHS -> atual.plusMonths(intervalo).withDayOfMonth(1);
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
