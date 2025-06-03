package api.endeavorbackend.services;

import api.endeavorbackend.models.*;
import api.endeavorbackend.models.DTOs.CriacaoGrupoDeEstudoDTO;
import api.endeavorbackend.models.DTOs.GrupoDeEstudoDTO;
import api.endeavorbackend.models.exceptions.areaEstudo.AreaEstudoNaoEncontradaException;
import api.endeavorbackend.models.exceptions.grupoEstudo.GrupoEstudoNaoEncontradoException;
import api.endeavorbackend.repositorios.AreaEstudoRepository;
import api.endeavorbackend.repositorios.GrupoDeEstudoRepository;
import api.endeavorbackend.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class GrupoDeEstudoServiceImpl implements GrupoDeEstudoService{
    private GrupoDeEstudoRepository grupoRepository;
    private AreaEstudoRepository areaRepository;
    private UsuarioRepository usuarioRepository;

    @Autowired
    public GrupoDeEstudoServiceImpl(GrupoDeEstudoRepository grupoRepository, AreaEstudoRepository areaRepository, UsuarioRepository usuarioRepository) {
        this.grupoRepository = grupoRepository;
        this.areaRepository = areaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public GrupoDeEstudoDTO create(CriacaoGrupoDeEstudoDTO dto) {
        AreaEstudo area = areaRepository.findById(dto.areaEstudoId())
                .orElseThrow(AreaEstudoNaoEncontradaException::new);
//        Usuario criador = usuarioRepository.findById(dto.usuarioCriadorId())
//                .orElseThrow(() -> new RuntimeException("Usuário criador não encontrado"));
        Usuario criador = new Usuario();
        GrupoDeEstudo grupo = new GrupoDeEstudo();
        grupo.setTitulo(dto.titulo());
        grupo.setDescricao(dto.descricao());
        grupo.setCapacidade(dto.capacidade());
        grupo.setPrivado(dto.privado());
        grupo.setAreaEstudo(area);
        grupo.setParticipantes(Set.of(criador));

        GrupoDeEstudo saved = grupoRepository.save(grupo);
        return GrupoDeEstudoDTO.from(saved);
    }

    public List<GrupoDeEstudoDTO> getAll() {
        return grupoRepository.findAll().stream().map(GrupoDeEstudoDTO::from).toList();
    }

    public GrupoDeEstudo getById(UUID id) {
        return grupoRepository.findById(id)
                .orElseThrow(GrupoEstudoNaoEncontradoException::new);

    }

    public List<GrupoDeEstudo> getAllById(Set<UUID> ids) {
        return grupoRepository.findAllById(ids);
    }

    public GrupoDeEstudoDTO update(UUID id, CriacaoGrupoDeEstudoDTO dto) {
        GrupoDeEstudo grupo = grupoRepository.findById(id)
                .orElseThrow(GrupoEstudoNaoEncontradoException::new);

        AreaEstudo area = areaRepository.findById(dto.areaEstudoId())
                .orElseThrow(AreaEstudoNaoEncontradaException::new);

        grupo.setTitulo(dto.titulo());
        grupo.setDescricao(dto.descricao());
        grupo.setCapacidade(dto.capacidade());
        grupo.setPrivado(dto.privado());
        grupo.setAreaEstudo(area);

        GrupoDeEstudo updated = grupoRepository.save(grupo);
        return GrupoDeEstudoDTO.from(updated);
    }

    public void delete(UUID id) {
        grupoRepository.deleteById(id);
    }

    public List<GrupoDeEstudoDTO> getByAreaEstudo(UUID areaEstudoId) {
        return grupoRepository.findByAreaEstudoId(areaEstudoId)
                .stream()
                .map(GrupoDeEstudoDTO::from)
                .toList();
    }
    public GrupoDeEstudoDTO adicionarUsuarioAoGrupo(UUID grupoId, UUID usuarioId) {
        GrupoDeEstudo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(GrupoEstudoNaoEncontradoException::new);

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!grupo.getParticipantes().contains(usuario)) {
            grupo.getParticipantes().add(usuario);
            grupoRepository.save(grupo);
        }

        return GrupoDeEstudoDTO.from(grupo);
    }

    public GrupoDeEstudoDTO removerUsuarioDoGrupo(UUID grupoId, UUID usuarioId) {
        GrupoDeEstudo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(GrupoEstudoNaoEncontradoException::new);

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        grupo.getParticipantes().remove(usuario);
        grupoRepository.save(grupo);

        return GrupoDeEstudoDTO.from(grupo);
    }

    @Override
    public String conviteGrupo(UUID grupoId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'conviteGrupo'");
    }
}
