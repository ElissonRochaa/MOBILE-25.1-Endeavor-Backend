package api.endeavorbackend.controllers;

import api.endeavorbackend.models.DTOs.TempoMateriaDTO;
import api.endeavorbackend.models.TempoMateria;
import api.endeavorbackend.models.enuns.StatusCronometro;
import api.endeavorbackend.services.TempoMateriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/tempo-materias")
public class TempoMateriaController {

    private final TempoMateriaService tempoMateriaService;

    public TempoMateriaController(TempoMateriaService tempoMateriaService) {
        this.tempoMateriaService = tempoMateriaService;
    }

    @PostMapping("/criar")
    public ResponseEntity<TempoMateriaDTO> create(@RequestBody Map<String, UUID> request ) {
        System.out.println("WTFFF");
        UUID usuarioId = request.get("usuarioId");
        UUID materiaId = request.get("materiaId");

        TempoMateria createdTempoMateria = tempoMateriaService.iniciarSessao(usuarioId, materiaId);
        TempoMateriaDTO tempoMateriaDTO = new TempoMateriaDTO(createdTempoMateria);
        return ResponseEntity.ok().body(tempoMateriaDTO);
    }

    @PutMapping("/pausar/{id}")
    public ResponseEntity<TempoMateriaDTO> pausarTempoMateria(@PathVariable UUID id) {
        try {
            TempoMateria pausedTempoMateria = tempoMateriaService.pausarSessao(id);
            TempoMateriaDTO tempoMateriaDTO = new TempoMateriaDTO(pausedTempoMateria);
            return ResponseEntity.ok().body(tempoMateriaDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/continuar/{id}")
    public ResponseEntity<TempoMateriaDTO> continuarTempoMateria(@PathVariable UUID id) {
        try {
            TempoMateria resumedTempoMateria = tempoMateriaService.continuarSessao(id);
            TempoMateriaDTO tempoMateriaDTO = new TempoMateriaDTO(resumedTempoMateria);
            return ResponseEntity.ok().body(tempoMateriaDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/finalizar/{id}")
    public ResponseEntity<TempoMateriaDTO> finalizarTempoMateria(@PathVariable UUID id) {
        try {
            TempoMateria finishedTempoMateria = tempoMateriaService.finalizarSessao(id);
            TempoMateriaDTO tempoMateriaDTO = new TempoMateriaDTO(finishedTempoMateria);
            return ResponseEntity.ok().body(tempoMateriaDTO);
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
    public ResponseEntity<TempoMateriaDTO> getById(@PathVariable UUID id) {
        TempoMateria tempoMateria = tempoMateriaService.buscar(id);
        TempoMateriaDTO dto = new TempoMateriaDTO(tempoMateria);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/buscaPorUsuario")
    public ResponseEntity<?> buscarSessaoPorUsuario(@RequestParam UUID usuarioId){
        try {
            List<TempoMateria> sessoes = tempoMateriaService.buscarPorUsuario(usuarioId);
            List<TempoMateriaDTO> dtoList = sessoes.stream().map(TempoMateriaDTO::new).toList();
            return ResponseEntity.ok().body(dtoList);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/buscaPorUsuarioMateria")
    public ResponseEntity<?> buscarSessaoPorUsuarioEMateria(
            @RequestParam UUID usuarioId,
            @RequestParam UUID materiaId) {
        try {
            TempoMateria sessao = tempoMateriaService.buscarSessaoPorUsuarioIdMateria(usuarioId, materiaId);
            if (sessao == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sessão não encontrada.");
            }

            TempoMateriaDTO sessaoDTO = new TempoMateriaDTO(sessao);
            return ResponseEntity.ok(sessaoDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar sessão: " + e.getMessage());
        }
    }

    @GetMapping("/buscaPorUsuarioMateriaAtiva")
    public ResponseEntity<?> buscarSessaoPorUsuarioEMateriaAtiva(
            @RequestParam UUID usuarioId,
            @RequestParam UUID materiaId) {
        try {
            TempoMateria sessao = tempoMateriaService.buscarSessaoPorUsuarioIdMateriaAtiva(usuarioId, materiaId);
            if (sessao == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sessão não encontrada.");
            }

            TempoMateriaDTO sessaoDTO = new TempoMateriaDTO(sessao);
            return ResponseEntity.ok(sessaoDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar sessão: " + e.getMessage());
        }
    }
        @GetMapping("/buscaPorStatusUsuario")
        public ResponseEntity<?> buscarSessaoPorStatusUsuario(@RequestParam StatusCronometro statusCronometro, @RequestParam UUID usuarioId){
            try {
                List<TempoMateria> sessoes = tempoMateriaService.buscarPorStatusUsuario(statusCronometro, usuarioId);
                if (sessoes == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sessão não encontrada.");
                }

                List<TempoMateriaDTO> sessaoDTO = sessoes.stream().map(TempoMateriaDTO::new).toList();
                return ResponseEntity.ok().body(sessaoDTO);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Erro ao buscar sessão: " + e.getMessage());
            }
        }

    @GetMapping("/sessoes-hoje")
    public ResponseEntity<?> buscarSessoesDeHojePorUsuario(@RequestParam UUID usuarioId) {
        try {
            List<TempoMateria> sessoes = tempoMateriaService.buscarSessoesDeHojePorUsuario(usuarioId);
            if (sessoes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma sessão encontrada para hoje.");
            }

            List<TempoMateriaDTO> sessoesDTO = sessoes.stream().map(TempoMateriaDTO::new).sorted((o1, o2) -> o1.getTempoTotalAcumulado() > o2.getTempoTotalAcumulado() ? 1:0).toList();
            return ResponseEntity.ok(sessoesDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar sessões: " + e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletar(@PathVariable UUID id) {
        return ResponseEntity.ok("sessão " + tempoMateriaService.deleteSessao(id) + " deletada com sucesso.");
    }

    }



