package api.endeavorbackend.dtos;

import api.endeavorbackend.enuns.Escolaridade;
import api.endeavorbackend.enuns.Role;
import api.endeavorbackend.models.AreaEstudo;

public record UsuarioDTO(
    String nome,
    String email,
    String senha,
    int idade,
    Escolaridade escolaridade,
    Role role
) {}

