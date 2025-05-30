package api.endeavorbackend.controllers;

import api.endeavorbackend.models.Meta;
import api.endeavorbackend.services.MetaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/metas")
public class MetaController {
    private final MetaService metaService;

    public MetaController(final MetaService metaService) {
        this.metaService = metaService;
    }

    @GetMapping
    public ResponseEntity<List<Meta>> getMetas() {
        List<Meta> metas = metaService.buscarMetas();
        return ResponseEntity.ok(metas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Meta> getMetasById(@PathVariable final UUID id) {
        Meta meta = metaService.buscarMeta(id);
        return ResponseEntity.ok(meta);
    }

    @PostMapping("/criar")
    public ResponseEntity<Meta> criarMeta(@RequestBody Meta meta) {
        Meta metaCriada = metaService.adicionarMeta(meta);
        return ResponseEntity.ok(metaCriada);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Meta> atualizarMeta(@RequestBody Meta meta) {
        Meta metaAtualizada = metaService.atualizarMeta(meta);
        return ResponseEntity.ok(metaAtualizada);
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Meta> excluirMeta(@PathVariable UUID id) {
        metaService.removerMeta(id);
        return ResponseEntity.noContent().build();
    }

}
