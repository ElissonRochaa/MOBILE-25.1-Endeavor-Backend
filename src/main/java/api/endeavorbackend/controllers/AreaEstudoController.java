package api.endeavorbackend.controllers;

import api.endeavorbackend.models.AreaEstudo;
import api.endeavorbackend.repositorios.AreaEstudoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/area-estudo")
public class AreaEstudoController {

    @Autowired
    private AreaEstudoRepository areaEstudoRepository;

    @PostMapping("/criar")
    public ResponseEntity<AreaEstudo> criar(@RequestBody AreaEstudo areaEstudo) {
        AreaEstudo salva = areaEstudoRepository.save(areaEstudo);
        return ResponseEntity.ok(salva);
    }

    @GetMapping
    public ResponseEntity<List<AreaEstudo>> listar() {
        List<AreaEstudo> areaEstudos = areaEstudoRepository.findAll();
        return ResponseEntity.ok(areaEstudos);
    }
}
