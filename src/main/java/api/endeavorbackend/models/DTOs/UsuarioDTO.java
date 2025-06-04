package api.endeavorbackend.models.DTOs;

import api.endeavorbackend.models.Usuario;
import api.endeavorbackend.models.enuns.Escolaridade;


import java.util.UUID;

public record UsuarioDTO(
        UUID id,
        String nome,
        String email,
        int idade,
        Escolaridade escolaridade
                 ) {
    public static UsuarioDTO from(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getIdade(),
                usuario.getEscolaridade()
        );
}}
