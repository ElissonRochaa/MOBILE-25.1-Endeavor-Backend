package api.endeavorbackend.services;
import api.endeavorbackend.models.TempoMateria;

import java.util.List;

public interface TempoMateriaService {

    TempoMateria iniciarSessao(Long usuarioId, Long materiaId);

    TempoMateria pausarSessao(Long Id);

    TempoMateria continuarSessao(Long Id);

    TempoMateria finalizarSessao(Long Id);

    TempoMateria buscar(Long tempoId);

    TempoMateria buscarSessaoPorUsuarioIdMateria(Long usuarioId, Long materiaId);

    List<TempoMateria> listar();

    void deleteSessao(Long id);


}

