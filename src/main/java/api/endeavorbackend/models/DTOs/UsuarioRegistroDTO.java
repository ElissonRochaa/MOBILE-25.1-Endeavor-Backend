package api.endeavorbackend.models.DTOs;
import api.endeavorbackend.models.enuns.Escolaridade;

public record UsuarioRegistroDTO(
    String nome,
    String email,
    String senha,
    int idade,
    Escolaridade escolaridade
) {}
