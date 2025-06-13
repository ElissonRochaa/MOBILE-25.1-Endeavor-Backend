package api.endeavorbackend.models.DTOs;

import api.endeavorbackend.models.Meta;
import api.endeavorbackend.services.MateriaService;

import java.time.LocalDateTime;
import java.util.UUID;

public record MetaDTO(
        UUID id,
        String nome,
        String descricao,
        UUID materiaId,
        LocalDateTime data,
        Boolean concluida
)
{

    public static MetaDTO from(Meta meta) {
        return new MetaDTO(
                meta.getId(),
                meta.getNome(),
                meta.getDescricao(),
                meta.getMateria().getId(),
                meta.getData(),
                meta.isConcluida()
        );
    }

    public static Meta to(MetaDTO dto, MateriaService materiaService) {
        return new Meta(
                dto.id,
                dto.nome,
                dto.descricao(),
                materiaService.buscar(dto.materiaId),
                dto.data(),
                dto.concluida
        );
    }

}
