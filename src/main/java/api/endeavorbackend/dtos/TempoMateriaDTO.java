package api.endeavorbackend.dtos;

import api.endeavorbackend.enuns.StatusCronometro;
import api.endeavorbackend.models.TempoMateria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TempoMateriaDTO {
    private long id;
    private Long usuarioId;
    private Long materiaId;
    private Timestamp inicio;
    private Timestamp fim;
    private StatusCronometro status;
    private Long tempoTotalAcumulado;

    public TempoMateriaDTO(TempoMateria tempoMateria) {
        this.id = tempoMateria.getId();
        this.usuarioId = tempoMateria.getUsuario().getId();
        this.materiaId = tempoMateria.getMateria().getId();
        this.inicio = tempoMateria.getInicio();
        this.fim = tempoMateria.getFim();
        this.status = tempoMateria.getStatus();
        this.tempoTotalAcumulado = tempoMateria.getTempoTotalAcumulado();
    }

}
