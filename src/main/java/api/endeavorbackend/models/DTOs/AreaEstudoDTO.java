package api.endeavorbackend.models.DTOs;

import api.endeavorbackend.models.AreaEstudo;
import api.endeavorbackend.models.GrupoDeEstudo;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public record AreaEstudoDTO(UUID id, String nome, List<UUID> grupoDeEstudosIds) {

    public static AreaEstudoDTO from(AreaEstudo entity) {
        return new AreaEstudoDTO(
                entity.getId(),
                entity.getNome(),
                entity.getGrupoDeEstudos()
                      .stream()
                      .map(GrupoDeEstudo::getId)
                      .toList()
        );
    }

    public AreaEstudo toEntity(Set<GrupoDeEstudo> grupos) {
        AreaEstudo entity = new AreaEstudo();
        entity.setId(this.id);
        entity.setNome(this.nome);
        entity.setGrupoDeEstudos(grupos);
        return entity;
    }
}
