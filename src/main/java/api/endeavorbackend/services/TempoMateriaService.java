package api.endeavorbackend.services;
import api.endeavorbackend.models.TempoMateria;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TempoMateriaService {

    TempoMateria iniciarSessao(UUID usuarioId, UUID materiaId);

    TempoMateria pausarSessao(UUID Id);

    TempoMateria continuarSessao(UUID Id);

    TempoMateria finalizarSessao(UUID Id);

    TempoMateria buscar(UUID tempoId);

    TempoMateria buscarSessaoPorUsuarioIdMateria(UUID usuarioId, UUID materiaId);

    TempoMateria buscarSessaoPorUsuarioIdMateriaAtiva(UUID usuarioId, UUID materiaId);

    List<TempoMateria> listar();

    void deleteSessao(UUID id);


}

