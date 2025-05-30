package api.endeavorbackend.controllers;

import api.endeavorbackend.services.TempoMateriaEstatisticaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/estatisticas")
public class EstatisticasController {
    private final TempoMateriaEstatisticaService estatisticaService;

    public EstatisticasController(TempoMateriaEstatisticaService estatisticaService) {
        this.estatisticaService = estatisticaService;
    }

    @GetMapping("/materia")
    public ResponseEntity<Long> getTempoTotalPorMateria(
            @RequestParam Long usuarioId,
            @RequestParam Long materiaId) {
        long tempo = estatisticaService.getTempoTotalPorMateria(usuarioId, materiaId);
        return ResponseEntity.ok(tempo);
    }

    @GetMapping("/dia")
    public ResponseEntity<Long> getTempoTotalNoDia(
            @RequestParam Long usuarioId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        long tempo = estatisticaService.getTempoTotalNoDia(usuarioId, data);
        return ResponseEntity.ok(tempo);
    }

    @GetMapping("/dia/materia")
    public ResponseEntity<Long> getTempoTotalNoDiaPorMateria(
            @RequestParam Long usuarioId,
            @RequestParam Long materiaId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        long tempo = estatisticaService.getTempoTotalNoDiaPorMateria(usuarioId, materiaId, data);
        return ResponseEntity.ok(tempo);
    }

    @GetMapping("/semana")
    public ResponseEntity<Long> getTempoNaSemana(
            @RequestParam Long usuarioId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        long tempo = estatisticaService.getTempoNaSemana(usuarioId, inicio, fim);
        return ResponseEntity.ok(tempo);
    }

    @GetMapping("/semana/materia")
    public ResponseEntity<Long> getTempoNaSemanaPorMateria(
            @RequestParam Long usuarioId,
            @RequestParam Long materiaId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        long tempo = estatisticaService.getTempoNaSemanaPorMateria(usuarioId, materiaId, inicio, fim);
        return ResponseEntity.ok(tempo);
    }

    @GetMapping("/semana/evolucao")
    public ResponseEntity<List<Long>> getTempoEvolucao(
            @RequestParam Long usuarioId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim
    ) {
        List<Long> lista = estatisticaService.getEvolucaoSemanal(usuarioId, inicio, fim);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/foguinho")
    public ResponseEntity<Integer> getTempoFoguinho(
            @RequestParam Long usuarioId,
            @RequestParam Long tempoMinimoDiario
    ) {
        Integer strike = estatisticaService.getDiasConsecutivosDeEstudo(usuarioId, tempoMinimoDiario);
        return ResponseEntity.ok(strike);
    }
}
