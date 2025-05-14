package api.endeavorbackend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import api.endeavorbackend.models.Usuario;

@Service
public interface UsuarioService {
    void cadastrarUsuario(Usuario usuario);
    Usuario buscarUsuarioPorId(Long id);
    Usuario buscarUsuarioPorEmail(String email);
    void atualizarUsuario(Usuario usuario);
    void excluirUsuario(Long id);
    boolean usuarioJaCadastrado(String email);
    List<Usuario> buscarUsuariosPorNome(String nome);
    List<Usuario> listarUsuarios();
    
}
