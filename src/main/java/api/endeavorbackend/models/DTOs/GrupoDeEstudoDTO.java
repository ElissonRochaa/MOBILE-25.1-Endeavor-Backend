package api.endeavorbackend.models.DTOs;

import api.endeavorbackend.models.GrupoDeEstudo;
import api.endeavorbackend.models.Usuario;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record GrupoDeEstudoDTO(

        @NotNull()
        UUID id,
        @NotNull()
        String titulo,
        @NotNull()
        String descricao,
        @NotNull()
        int capacidade,
        @NotNull()
        boolean privado,
        @NotNull()
        UUID areaEstudoId,
        Set<UUID> usuariosIds
) {
    public static GrupoDeEstudoDTO from(GrupoDeEstudo entity) {
        return new GrupoDeEstudoDTO(
                entity.getId(),
                entity.getTitulo(),
                entity.getDescricao(),
                entity.getCapacidade(),
                entity.isPrivado(),
                entity.getAreaEstudo().getId(),
                entity.getUsuarios()
                      .stream()
                      .map(Usuario::getId)
                      .collect(Collectors.toSet()));

    }
}
