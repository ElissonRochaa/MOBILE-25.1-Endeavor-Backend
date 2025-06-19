package api.endeavorbackend.services;

import java.util.List;
import java.util.UUID;

import api.endeavorbackend.models.DTOs.UsuarioDTO;
import org.springframework.stereotype.Service;

import api.endeavorbackend.models.Usuario;

public interface UsuarioService {
    void cadastrarUsuario(Usuario usuario);
    Usuario buscarUsuarioPorId(UUID id);
    void atualizarUsuario(UsuarioDTO usuario);
    void excluirUsuario(UUID id);
    boolean usuarioJaCadastrado(String email);
    List<UsuarioDTO> buscarUsuariosPorNome(String nome);
    List<UsuarioDTO> listarUsuarios();
    void recuperarSenha(String email);
    
    
}
