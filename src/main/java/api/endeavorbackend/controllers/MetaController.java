package api.endeavorbackend.controllers;

import api.endeavorbackend.models.DTOs.CreateMetaDTO;
import api.endeavorbackend.models.DTOs.MetaDTO;
import api.endeavorbackend.models.Materia;
import api.endeavorbackend.models.Meta;
import api.endeavorbackend.services.MateriaService;
import api.endeavorbackend.services.MetaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/metas")
public class MetaController {
    private final MetaService metaService;
    private final MateriaService materiaService;

    public MetaController(final MetaService metaService, MateriaService materiaService) {
        this.metaService = metaService;
        this.materiaService = materiaService;
    }

    @GetMapping
    public ResponseEntity<List<MetaDTO>> getMetas() {
        List<Meta> metas = metaService.buscarMetas();
        List<MetaDTO> metaDTOS = metas.stream()
                .map(MetaDTO::from)
                .toList();
        return ResponseEntity.ok(metaDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetaDTO> getMetasById(@PathVariable final UUID id) {
        Meta meta = metaService.buscarMeta(id);
        return ResponseEntity.ok(MetaDTO.from(meta));
    }

    @GetMapping("/porMateria/{materiaId}")
    public ResponseEntity<List<MetaDTO>> getMetasByMateria(@PathVariable final UUID materiaId) {
        List<Meta> metas = metaService.buscarMetasPorMateria(materiaId);
        List<MetaDTO> metaDTOS = metas.stream()
                .map(MetaDTO::from)
                .toList();
        return ResponseEntity.ok(metaDTOS);
    }

    @PostMapping("/criar")
    public ResponseEntity<MetaDTO> criarMeta(@RequestBody CreateMetaDTO dto) {
        System.out.println(dto);
        System.out.println(dto.materiaId());
        Materia materia = materiaService.buscar(dto.materiaId());
        System.out.println(materia.getId());
        Meta meta = new Meta();
        meta.setNome(dto.nome());
        meta.setConcluida(dto.concluida());
        meta.setData(dto.data());
        meta.setMateria(materia);
        meta.setDescricao(dto.descricao());

        Meta metaCriada = metaService.adicionarMeta(meta);
        System.out.println(metaCriada);
        return ResponseEntity.ok(MetaDTO.from(metaCriada));
    }

    @PutMapping("/atualizar")
    public ResponseEntity<MetaDTO> atualizarMeta(@RequestBody MetaDTO meta) throws Exception {
        Meta metaEncontrada = metaService.buscarMeta(meta.id());
        if (meta.data() != null) {
            metaEncontrada.setData(meta.data());
        } else if (meta.concluida() != null) {
            metaEncontrada.setConcluida(meta.concluida());
        } else if (meta.descricao() != null) {
                    metaEncontrada.setDescricao(meta.descricao());
        } else if (meta.nome() != null) {
            metaEncontrada.setNome(meta.nome());
        } else {
            throw new Exception("Nenhum campo para atualizar");
        }

        Meta metaAtualizada = metaService.atualizarMeta(metaEncontrada);
        return ResponseEntity.ok(MetaDTO.from(metaAtualizada));
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<MetaDTO> excluirMeta(@PathVariable UUID id) {
        metaService.removerMeta(id);
        return ResponseEntity.noContent().build();
    }

}
