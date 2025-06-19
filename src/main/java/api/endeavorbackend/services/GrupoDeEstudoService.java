package api.endeavorbackend.services;

import api.endeavorbackend.models.DTOs.MembroComTempoDTO;
import api.endeavorbackend.models.DTOs.UsuarioDTO;
import api.endeavorbackend.models.GrupoDeEstudo;
import api.endeavorbackend.models.DTOs.CriacaoGrupoDeEstudoDTO;
import api.endeavorbackend.models.DTOs.GrupoDeEstudoDTO;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface GrupoDeEstudoService {
    GrupoDeEstudoDTO create(CriacaoGrupoDeEstudoDTO dto);
    List<GrupoDeEstudoDTO> getAll();
    GrupoDeEstudo getById(UUID id);
    List<GrupoDeEstudo> getAllById(Set<UUID> ids);
    GrupoDeEstudoDTO update(UUID id, CriacaoGrupoDeEstudoDTO dto);
    void delete(UUID id);
    List<GrupoDeEstudoDTO> getByAreaEstudo(UUID areaEstudoId);
    GrupoDeEstudoDTO adicionarUsuarioAoGrupo(UUID grupoId, UUID usuarioId);
    GrupoDeEstudoDTO removerUsuarioDoGrupo(UUID grupoId, UUID usuarioId);
    List<GrupoDeEstudoDTO> getAllFromUsuario(UUID usuarioId);
    List<MembroComTempoDTO> getMembrosFromGrupo(UUID grupoId);
}
