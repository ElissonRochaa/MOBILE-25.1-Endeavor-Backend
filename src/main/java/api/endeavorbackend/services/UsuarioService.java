package api.endeavorbackend.services;

import java.util.List;
import api.endeavorbackend.models.Usuario;

public interface UsuarioService {
    void cadastrarUsuario(Usuario usuario);
    Usuario buscarUsuarioPorId(Long id);
    void atualizarUsuario(Usuario usuario);
    void excluirUsuario(Long id);
    boolean usuarioJaCadastrado(String email);
    List<Usuario> buscarUsuariosPorNome(String nome);
    List<Usuario> listarUsuarios();
    
}
