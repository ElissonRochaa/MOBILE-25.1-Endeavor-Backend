package api.endeavorbackend.controllers;

import api.endeavorbackend.dtos.TempoMateriaDTO;
import api.endeavorbackend.models.TempoMateria;
import api.endeavorbackend.services.TempoMateriaService;
import api.endeavorbackend.utils.SemanaUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/tempo-materias")
public class TempoMateriaController {

    private final TempoMateriaService tempoMateriaService;

    public TempoMateriaController(TempoMateriaService tempoMateriaService) {
        this.tempoMateriaService = tempoMateriaService;
    }

    @PostMapping("/criar")
    public ResponseEntity<TempoMateriaDTO> create(@RequestBody Map<String, Long> request ) {
        Long usuarioId = request.get("usuarioId");
        Long materiaId = request.get("materiaId");

        TempoMateria createdTempoMateria = tempoMateriaService.iniciarSessao(usuarioId, materiaId);
        TempoMateriaDTO tempoMateriaDTO = new TempoMateriaDTO(createdTempoMateria);
        return ResponseEntity.ok().body(tempoMateriaDTO);
    }

    @PutMapping("/pausar/{id}")
    public ResponseEntity<TempoMateria> pausarTempoMateria(@PathVariable Long id) {
        try {
            TempoMateria pausedTempoMateria = tempoMateriaService.pausarTempoMateria(id);
            return ResponseEntity.ok().body(pausedTempoMateria);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/continuar/{id}")
    public ResponseEntity<TempoMateria> continuarTempoMateria(@PathVariable Long id) {
        try {
            TempoMateria resumedTempoMateria = tempoMateriaService.continuarTempoMateria(id);
            return ResponseEntity.ok().body(resumedTempoMateria);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/finalizar/{id}")
    public ResponseEntity<TempoMateria> finalizarTempoMateria(@PathVariable Long id) {
        try {
            TempoMateria finishedTempoMateria = tempoMateriaService.finalizarTempoMateria(id);
            return ResponseEntity.ok().body(finishedTempoMateria);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<TempoMateriaDTO>> listar() {
        List<TempoMateria> tempoMaterias = tempoMateriaService.listar();
        List<TempoMateriaDTO> dtoList = tempoMaterias.stream().map(TempoMateriaDTO::new).toList();
        return ResponseEntity.ok().body(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TempoMateriaDTO> getById(@PathVariable Long id) {
        Optional<TempoMateria> tempoMateria = tempoMateriaService.buscar(id);
        return tempoMateria.map(m -> ResponseEntity.ok(new TempoMateriaDTO(m)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/tempo-dia/{idMateria}")
    public ResponseEntity<Long> getTotalTempoNoDiaPorMateria(
            @PathVariable Long idMateria,
            @RequestParam String date) {
        try {
            LocalDate localDate = LocalDate.parse(date);
            Long tempo = tempoMateriaService.getTempoNoDiaPorMateria(idMateria, localDate);
            return ResponseEntity.ok().body(tempo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/tempo-dia")
    public ResponseEntity<Long> getTotalTempoNoDia(@RequestParam String date) {
        try {
            LocalDate localDate = LocalDate.parse(date);
            Long tempo = tempoMateriaService.getTempoNoDia(localDate);
            return ResponseEntity.ok().body(tempo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/tempo-materia/{idMateria}")
    public ResponseEntity<Long> getTotalTempoMateria(
            @PathVariable Long idMateria) {
        try {
            Long tempo = tempoMateriaService.getTotalTempoMateria(idMateria);
            return ResponseEntity.ok().body(tempo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/tempo-semana/{idMateria}")
    public ResponseEntity<Long> getTotalTempoNaSemanaPorMateria(
            @PathVariable Long idMateria) {
        try {
            LocalDate inicioSemana = SemanaUtils.getInicioSemana(LocalDate.now());
            LocalDate fimSemana = SemanaUtils.getFimSemana(LocalDate.now());
            Long tempo = tempoMateriaService.getTempoNaSemanaPorMateria(idMateria, inicioSemana, fimSemana);
            return ResponseEntity.ok().body(tempo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/tempo-semana")
    public ResponseEntity<Long> getTotalTempoNaSemana() {
        try {
            LocalDate inicioSemana = SemanaUtils.getInicioSemana(LocalDate.now());
            LocalDate fimSemana = SemanaUtils.getFimSemana(LocalDate.now());
            Long tempo = tempoMateriaService.getTempoNaSemana(inicioSemana, fimSemana);
            return ResponseEntity.ok().body(tempo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
