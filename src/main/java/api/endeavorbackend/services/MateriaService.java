package api.endeavorbackend.services;

import api.endeavorbackend.models.Materia;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MateriaService {
    List<Materia> listar();
    Optional<Materia> buscar(UUID id);
    void excluir(UUID id);
    Materia salvar(Materia materia);
    Materia atualizar(Materia materia);
    List<Materia> buscarMateriasPorUsuario(UUID usuarioId);
}
