package api.endeavorbackend.models.DTOs;
import java.time.LocalDateTime;
import java.util.UUID;

public record CreateMetaDTO(
        String nome,
        String descricao,
        UUID materiaId,
        LocalDateTime data,
        Boolean concluida
) {}

