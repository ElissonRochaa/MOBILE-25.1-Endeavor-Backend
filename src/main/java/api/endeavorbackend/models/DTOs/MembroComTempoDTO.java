package api.endeavorbackend.models.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MembroComTempoDTO {
    private UsuarioDTO usuario;
    private TempoMateriaDTO tempoMateria;
}