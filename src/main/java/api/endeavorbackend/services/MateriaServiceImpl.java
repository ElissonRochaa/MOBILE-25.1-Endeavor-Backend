package api.endeavorbackend.services;

import api.endeavorbackend.models.Materia;
import api.endeavorbackend.repositorios.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MateriaServiceImpl implements MateriaService {
    @Autowired
    MateriaRepository materiaRepository;
    @Autowired
    private UsuarioService usuarioService;

    public List<Materia> listar() {
        return materiaRepository.findAll();
    }

    public Materia buscar(UUID id) {
        return materiaRepository.findByIdWithTempoMaterias(id);
    }

    public void excluir(UUID id) {
        materiaRepository.deleteById(id);
    }

    public Materia salvar(Materia materia) {
        return materiaRepository.save(materia);
    }

    public Materia atualizar(Materia materia) {
        return materiaRepository.save(materia);
    }

    public List<Materia> buscarMateriasPorUsuario(UUID usuarioId) {
        usuarioService.buscarUsuarioPorId(usuarioId);
        return materiaRepository.findByUsuarioId(usuarioId);
    }

}
