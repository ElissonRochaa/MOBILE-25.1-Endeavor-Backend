package api.endeavorbackend.controllers;

import api.endeavorbackend.models.DTOs.EvolucaoDTO;
import api.endeavorbackend.services.TempoMateriaEstatisticaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/estatisticas")
public class EstatisticasController {
    private final TempoMateriaEstatisticaService estatisticaService;

    public EstatisticasController(TempoMateriaEstatisticaService estatisticaService) {
        this.estatisticaService = estatisticaService;
    }

    @GetMapping("/materia")
    public ResponseEntity<Long> getTempoTotalPorMateria(
            @RequestParam UUID usuarioId,
            @RequestParam UUID materiaId) {
        long tempo = estatisticaService.getTempoTotalPorMateria(usuarioId, materiaId);
        return ResponseEntity.ok(tempo);
    }

    @GetMapping("/dia")
    public ResponseEntity<Long> getTempoTotalNoDia(
            @RequestParam UUID usuarioId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        long tempo = estatisticaService.getTempoTotalNoDia(usuarioId, data);
        return ResponseEntity.ok(tempo);
    }

    @GetMapping("/dia/materia")
    public ResponseEntity<Long> getTempoTotalNoDiaPorMateria(
            @RequestParam UUID usuarioId,
            @RequestParam UUID materiaId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        long tempo = estatisticaService.getTempoTotalNoDiaPorMateria(usuarioId, materiaId, data);
        return ResponseEntity.ok(tempo);
    }

    @GetMapping("/semana")
    public ResponseEntity<Double> getTempoNaSemana(
            @RequestParam UUID usuarioId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        double tempo = estatisticaService.getTempoNaSemana(usuarioId, inicio, fim);
        return ResponseEntity.ok(tempo);
    }

    @GetMapping("/semana/materia")
    public ResponseEntity<Double> getTempoNaSemanaPorMateria(
            @RequestParam UUID usuarioId,
            @RequestParam UUID materiaId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        double tempo = estatisticaService.getTempoNaSemanaPorMateria(usuarioId, materiaId, inicio, fim);
        return ResponseEntity.ok(tempo);
    }

    @GetMapping("/evolucao")
    public ResponseEntity<List<EvolucaoDTO>> getTempoEvolucao(
            @RequestParam UUID usuarioId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim,
            @RequestParam ChronoUnit unidade,
            @RequestParam int intervalo
            ) {
        List<EvolucaoDTO> lista = estatisticaService.getEvolucaoPorPeriodo(usuarioId, inicio, fim, unidade, intervalo);
        lista.forEach(e -> System.out.println(e.getData() + " - " + e.getTempo()));
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/foguinho")
    public ResponseEntity<Integer> getTempoFoguinho(
            @RequestParam UUID usuarioId,
            @RequestParam Long tempoMinimoDiario
    ) {
        Integer strike = estatisticaService.getDiasConsecutivosDeEstudo(usuarioId, tempoMinimoDiario);
        return ResponseEntity.ok(strike);
    }
}
