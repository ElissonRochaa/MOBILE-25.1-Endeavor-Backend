package api.endeavorbackend.dtos;

import api.endeavorbackend.models.Materia;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MateriaDTO {
    private Long id;
    private String nome;
    private String descricao;
    private Long usuarioId;

    public MateriaDTO(Materia materia) {
        this.id = materia.getId();
        this.nome = materia.getNome();
        this.descricao = materia.getDescricao();
        this.usuarioId = materia.getUsuario() != null? materia.getUsuario().getId() : null;
    }
}
