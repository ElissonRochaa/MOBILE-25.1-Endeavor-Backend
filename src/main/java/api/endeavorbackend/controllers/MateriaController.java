package api.endeavorbackend.controllers;

import api.endeavorbackend.dtos.MateriaDTO;
import api.endeavorbackend.models.Materia;
import api.endeavorbackend.services.MateriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/materias")
public class MateriaController {

    private final MateriaService materiaService;

    public MateriaController(MateriaService materiaService) {
        this.materiaService = materiaService;
    }

    @GetMapping
    public ResponseEntity<List<MateriaDTO>> listar() {
        List<Materia> materias = materiaService.listar();
        List<MateriaDTO> dtoList = materias.stream().map(MateriaDTO::new).toList();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MateriaDTO> buscar(@PathVariable Long id) {
        Optional<Materia> materia = materiaService.buscar(id);
        return materia.map(m -> ResponseEntity.ok(new MateriaDTO(m)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<Materia> create(@RequestBody Materia materia) {
        materiaService.salvar(materia);
        return ResponseEntity.ok(materia);
    }

    @PutMapping("/update")
    public ResponseEntity<MateriaDTO> update(@RequestBody Materia materia) {
        Materia atualizada = materiaService.atualizar(materia);
        MateriaDTO dto = new MateriaDTO(atualizada);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        materiaService.excluir(id);
        return ResponseEntity.noContent().build();
    }

}