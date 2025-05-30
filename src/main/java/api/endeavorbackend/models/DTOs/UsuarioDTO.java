package api.endeavorbackend.models.DTOs;

import api.endeavorbackend.models.enuns.Escolaridade;
import api.endeavorbackend.models.enuns.Role;
import api.endeavorbackend.models.AreaEstudo;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

public record UsuarioDTO(
    String nome,
    String email,
    String senha,
    int idade,
    Escolaridade escolaridade,
    AreaEstudo areaEstudo,
    Role role
) {}

