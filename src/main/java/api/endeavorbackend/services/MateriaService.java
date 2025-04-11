package api.endeavorbackend.services;

import api.endeavorbackend.models.Materia;
import api.endeavorbackend.repositorios.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MateriaService {
    @Autowired
    MateriaRepository materiaRepository;

    public List<Materia> listar() {
        return materiaRepository.findAll();
    }

    public Optional<Materia> buscar(String id) {
        return materiaRepository.findById(id);
    }


}
