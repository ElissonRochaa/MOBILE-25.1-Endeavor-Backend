package api.endeavorbackend.services;

import java.util.List;
import java.util.Optional;
import api.endeavorbackend.models.Usuario;
import api.endeavorbackend.repositorios.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void cadastrarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    @Override
    public Usuario buscarUsuarioPorId(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            return usuario.get();
        } else {
            throw new RuntimeException("Usuário não encontrado com o ID: " + id);
        }
    }

    @Override
    public Usuario buscarUsuarioPorEmail(String email) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (usuario.isPresent()) {
            return usuario.get();
        } else {
            throw new RuntimeException("Usuário não encontrado com o email: " + email);
        }
    }

    @Override
    public void atualizarUsuario(Usuario usuario) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(usuario.getId());
        if (usuarioExistente.isPresent()) {
            usuarioRepository.save(usuario);
        } else {
            throw new RuntimeException("Usuário não encontrado com o ID: " + usuario.getId());
        }
    }

    @Override
    public void excluirUsuario(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            usuarioRepository.delete(usuario.get());
        } else {
            throw new RuntimeException("Usuário não encontrado com o ID: " + id);
        }
    }

    @Override
    public boolean usuarioJaCadastrado(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Override
    public List<Usuario> buscarUsuariosPorNome(String nome) {
        List<Usuario> usuarios = usuarioRepository.findByNome(nome);
        if (usuarios.isEmpty()) {
            throw new RuntimeException("Nenhum usuário encontrado com o nome: " + nome);
        }
        return usuarios;
    }

    @Override
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        if (usuarios.isEmpty()) {
            throw new RuntimeException("Nenhum usuário encontrado.");
        }
        return usuarios;
    }
}
