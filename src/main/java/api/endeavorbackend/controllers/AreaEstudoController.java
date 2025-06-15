package api.endeavorbackend.controllers;

import api.endeavorbackend.models.AreaEstudo;
import api.endeavorbackend.models.DTOs.AreaEstudoDTO;
import api.endeavorbackend.models.DTOs.CriacaoAreaEstudoDTO;
import api.endeavorbackend.services.AreaEstudoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.UUID;

@RestController
@RequestMapping("/api/areas-estudo")
public class AreaEstudoController {

    private final AreaEstudoService areaEstudoService;

    public AreaEstudoController(AreaEstudoService areaEstudoService) {
        this.areaEstudoService = areaEstudoService;
    }

    @PostMapping
    public ResponseEntity<AreaEstudoDTO> create(@RequestBody CriacaoAreaEstudoDTO dto) {
        AreaEstudo created = areaEstudoService.create(dto);
        return ResponseEntity.ok(AreaEstudoDTO.from(created));
    }
    @GetMapping
    public ResponseEntity<List<AreaEstudoDTO>> getAll() {
        List<AreaEstudoDTO> list = areaEstudoService.getAll()
                .stream()
                .map(AreaEstudoDTO::from)
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaEstudoDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(AreaEstudoDTO.from(areaEstudoService.getById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AreaEstudoDTO> update(@PathVariable UUID id, @RequestBody String novoNome) {
        try {
            AreaEstudo updated = areaEstudoService.update(id, novoNome);
            return ResponseEntity.ok(AreaEstudoDTO.from(updated));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        areaEstudoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<AreaEstudoDTO>> findByNome(@RequestParam String nome) {
        List<AreaEstudoDTO> results = areaEstudoService.findByNome(nome)
                .stream()
                .map(AreaEstudoDTO::from)
                .toList();
        return ResponseEntity.ok(results);
    }
}
