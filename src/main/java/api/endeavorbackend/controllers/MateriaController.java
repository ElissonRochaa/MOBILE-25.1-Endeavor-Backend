package api.endeavorbackend.controllers;

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
    public ResponseEntity<List<Materia>> listar() {
        List<Materia> materias = materiaService.listar();
        return ResponseEntity.ok(materias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Materia> buscar(@PathVariable String id) {
        Optional<Materia> materia = materiaService.buscar(id);
        return materia.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}