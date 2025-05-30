package api.endeavorbackend.services;

import api.endeavorbackend.models.AreaEstudo;
import java.util.List;
import java.util.UUID;

public interface AreaEstudoService {
    AreaEstudo create(api.endeavorbackend.models.DTOs.CriacaoAreaEstudoDTO dto);
    List<AreaEstudo> getAll();
    AreaEstudo getById(UUID id);
    AreaEstudo update(UUID id, String novoNome);
    void delete(UUID id);
    List<AreaEstudo> findByNome(String nome);
    AreaEstudo adicionarGrupo(UUID areaEstudoId, UUID grupoId);
    AreaEstudo removerGrupo(UUID areaEstudoId, UUID grupoId);
}
