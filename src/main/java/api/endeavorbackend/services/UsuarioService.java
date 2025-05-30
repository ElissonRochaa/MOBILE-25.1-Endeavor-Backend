package api.endeavorbackend.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import api.endeavorbackend.models.Usuario;

public interface UsuarioService {
    void cadastrarUsuario(Usuario usuario);
    Usuario buscarUsuarioPorId(UUID id);
    Usuario buscarUsuarioPorEmail(String email);
    void atualizarUsuario(Usuario usuario);
    void excluirUsuario(UUID id);
    boolean usuarioJaCadastrado(String email);
    List<Usuario> buscarUsuariosPorNome(String nome);
    List<Usuario> listarUsuarios();
    
}
