package api.endeavorbackend.controllers;

import api.endeavorbackend.dtos.MateriaDTO;
import api.endeavorbackend.models.Materia;
import api.endeavorbackend.models.Usuario;
import api.endeavorbackend.services.MateriaService;
import api.endeavorbackend.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/materias")
public class MateriaController {

    private final MateriaService materiaService;
    private final UsuarioService usuarioService;

    public MateriaController(MateriaService materiaService, UsuarioService usuarioService) {
        this.materiaService = materiaService;
        this.usuarioService = usuarioService;
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
    public ResponseEntity<MateriaDTO> create(@RequestBody MateriaDTO dto) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(dto.getUsuarioId());
        Materia materia = new Materia();
        materia.setNome(dto.getNome());
        materia.setDescricao(dto.getDescricao());
        materia.setUsuario(usuario);

        Materia saved = materiaService.salvar(materia);
        return ResponseEntity.ok(new MateriaDTO(saved));
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