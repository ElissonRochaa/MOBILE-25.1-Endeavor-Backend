package api.endeavorbackend.models.DTOs;

import api.endeavorbackend.models.Materia;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MateriaDTO {
    private UUID id;
    private String nome;
    private String descricao;
    private UUID usuarioId;
    TempoMateriaDTO tempoMateria;

    public MateriaDTO(Materia materia) {
        this.id = materia.getId();
        this.nome = materia.getNome();
        this.descricao = materia.getDescricao();
        this.usuarioId = materia.getUsuario() != null? materia.getUsuario().getId() : null;
        this.tempoMateria = materia.getSessaoAtivaOuPausada();
    }
}
