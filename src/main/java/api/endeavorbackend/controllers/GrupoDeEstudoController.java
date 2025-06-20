package api.endeavorbackend.controllers;

import api.endeavorbackend.models.DTOs.*;
import api.endeavorbackend.services.GrupoDeEstudoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/grupos-estudo")
public class GrupoDeEstudoController {

    private final GrupoDeEstudoService grupoDeEstudoService;

    @Autowired
    public GrupoDeEstudoController(GrupoDeEstudoService grupoDeEstudoService) {
        this.grupoDeEstudoService = grupoDeEstudoService;
    }

    @PostMapping
    public ResponseEntity<GrupoDeEstudoDTO> create(@Valid @RequestBody CriacaoGrupoDeEstudoDTO dto) {
        GrupoDeEstudoDTO created = grupoDeEstudoService.create(dto);
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(created);
    }

    @GetMapping
    public ResponseEntity<List<GrupoDeEstudoDTO>> getAll() {
        return ResponseEntity.ok(grupoDeEstudoService.getAll());
    }

    @GetMapping("/usuario")
    public ResponseEntity<List<GrupoDeEstudoDTO>> getAllFromUsuario(@RequestParam UUID usuarioId) {
        return ResponseEntity.ok(grupoDeEstudoService.getAllFromUsuario(usuarioId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrupoDeEstudoDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(GrupoDeEstudoDTO.from(grupoDeEstudoService.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GrupoDeEstudoDTO> update(@PathVariable UUID id, @Valid @RequestBody CriacaoGrupoDeEstudoDTO dto) {
        try {
            GrupoDeEstudoDTO updated = grupoDeEstudoService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        grupoDeEstudoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/por-area/{areaEstudoId}")
    public ResponseEntity<List<GrupoDeEstudoDTO>> getByAreaEstudo(@PathVariable UUID areaEstudoId) {
        System.out.println(grupoDeEstudoService.getByAreaEstudo(areaEstudoId));
        return ResponseEntity.ok(grupoDeEstudoService.getByAreaEstudo(areaEstudoId));
    }

    @PatchMapping("/{grupoId}/adicionar-usuario/{usuarioId}")
    public ResponseEntity<GrupoDeEstudoDTO> adicionarUsuarioAoGrupo(
            @PathVariable UUID grupoId,
            @PathVariable UUID usuarioId) {
        GrupoDeEstudoDTO grupoAtualizado = grupoDeEstudoService.adicionarUsuarioAoGrupo(grupoId, usuarioId);
        return ResponseEntity.ok(grupoAtualizado);
    }

    @PatchMapping("/{grupoId}/remover-usuario/{usuarioId}")
    public ResponseEntity<GrupoDeEstudoDTO> removerUsuarioDoGrupo(
            @PathVariable UUID grupoId,
            @PathVariable UUID usuarioId) {
        GrupoDeEstudoDTO grupoAtualizado = grupoDeEstudoService.removerUsuarioDoGrupo(grupoId, usuarioId);
        return ResponseEntity.ok(grupoAtualizado);
    }

    @GetMapping("/{grupoId}/membros")
    public ResponseEntity<List<MembroComTempoDTO>> getUsuariosFromGrupo(@PathVariable UUID grupoId) {
        return ResponseEntity.ok(grupoDeEstudoService.getMembrosFromGrupo(grupoId));
    }
}
