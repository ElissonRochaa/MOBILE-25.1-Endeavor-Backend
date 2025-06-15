package api.endeavorbackend.services;
import api.endeavorbackend.models.Materia;
import api.endeavorbackend.models.TempoMateria;
import api.endeavorbackend.models.enuns.StatusCronometro;

import java.sql.Timestamp;
import java.util.List;
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

    UUID deleteSessao(UUID id);

    List<TempoMateria> buscarPorStatusUsuario(StatusCronometro status, UUID usuarioId);

    List<TempoMateria> buscarSessoesDeHojePorUsuario(UUID usuarioId);

    List<TempoMateria> buscarPorUsuario(UUID usuarioId);

    void finalizarSessaoComFimPersonalizado(TempoMateria sessao, Timestamp fimPersonalizado);

}

