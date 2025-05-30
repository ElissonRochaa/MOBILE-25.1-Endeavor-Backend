package api.endeavorbackend.models.DTOs;

import api.endeavorbackend.models.enuns.StatusCronometro;
import api.endeavorbackend.models.TempoMateria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TempoMateriaDTO {
    private UUID id;
    private UUID usuarioId;
    private UUID materiaId;
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
