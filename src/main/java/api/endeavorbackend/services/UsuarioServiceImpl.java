package api.endeavorbackend.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import api.endeavorbackend.models.DTOs.UsuarioDTO;
import api.endeavorbackend.models.Usuario;
import api.endeavorbackend.repositorios.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Autowired
    private CodigoVerificacaoService codigoVerificacaoService;

    @Override
    public void cadastrarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    @Override
    public Usuario buscarUsuarioPorId(UUID id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            return usuario.get();
        } else {
            throw new RuntimeException("Usuário não encontrado com o ID: " + id);
        }
    }

    @Override
    public void atualizarUsuario(UsuarioDTO dto) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(dto.id());
        if (usuarioExistente.isPresent()) {
            Usuario usuarioAtual = usuarioExistente.get();
            usuarioAtual.setNome(dto.nome());
            usuarioAtual.setEmail(dto.email());
            usuarioAtual.setIdade(dto.idade());
            usuarioAtual.setEscolaridade(dto.escolaridade());

            usuarioRepository.save(usuarioAtual);
        } else {
            throw new RuntimeException("Usuário não encontrado com o ID: " + dto.id());
        }
    }


    @Override
    public void excluirUsuario(UUID id) {
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
    public List<UsuarioDTO> buscarUsuariosPorNome(String nome) {
        List<UsuarioDTO> usuarios = usuarioRepository.findByNome(nome).stream().map(UsuarioDTO::from).toList();
        if (usuarios.isEmpty()) {
            throw new RuntimeException("Nenhum usuário encontrado com o nome: " + nome);
        }
        return usuarios;
    }

    @Override
    public List<UsuarioDTO> listarUsuarios() {
        List<UsuarioDTO> usuarios = usuarioRepository.findAll().stream().map(UsuarioDTO::from).toList();
        if (usuarios.isEmpty()) {
            throw new RuntimeException("Nenhum usuário encontrado.");
        }
        return usuarios;
    }


}
