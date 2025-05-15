package api.endeavorbackend.services;

import api.endeavorbackend.models.Materia;

import java.util.List;
import java.util.Optional;

public interface MateriaService {
    List<Materia> listar();
    Optional<Materia> buscar(Long id);
    void excluir(Long id);
    void salvar(Materia materia);
    Materia atualizar(Materia materia);
}
