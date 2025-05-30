package api.endeavorbackend.models.DTOs;

import java.util.UUID;

public record CriacaoGrupoDeEstudoDTO(
        String titulo,
        String descricao,
        int capacidade,
        boolean privado,
        UUID areaEstudoId,
        UUID usuarioCriadorId
) {
}
