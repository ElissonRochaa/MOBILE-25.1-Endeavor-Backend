package api.endeavorbackend.services;

import api.endeavorbackend.models.AreaEstudo;
import api.endeavorbackend.models.DTOs.CriacaoAreaEstudoDTO;
import api.endeavorbackend.models.DTOs.GrupoDeEstudoDTO;
import api.endeavorbackend.models.GrupoDeEstudo;
import api.endeavorbackend.models.exceptions.areaEstudo.AreaEstudoNaoEncontradaException;
import api.endeavorbackend.repositorios.AreaEstudoRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AreaEstudoService {

    private final AreaEstudoRepository repository;
    private final GrupoDeEstudoService grupoService;

    public AreaEstudoService(AreaEstudoRepository repository, GrupoDeEstudoService grupoService) {
        this.repository = repository;
        this.grupoService = grupoService;
    }

    public AreaEstudo create(CriacaoAreaEstudoDTO dto) {
        AreaEstudo entity = new AreaEstudo();
        entity.setNome(dto.nome());
        return repository.save(entity);
    }

    public List<AreaEstudo> getAll() {
        return repository.findAll();
    }


    public AreaEstudo getById(UUID id) {
        return repository.findById(id).orElseThrow(AreaEstudoNaoEncontradaException::new);
    }

    public AreaEstudo update(UUID id, String novoNome) {
        AreaEstudo entity = repository.findById(id)
                .orElseThrow(AreaEstudoNaoEncontradaException::new);

        entity.setNome(novoNome);

        return repository.save(entity);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public List<AreaEstudo> findByNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
    }

    public AreaEstudo adicionarGrupo(UUID areaEstudoId, UUID grupoId) {
        AreaEstudo areaEstudo = repository.findById(areaEstudoId)
                .orElseThrow(AreaEstudoNaoEncontradaException::new);

        GrupoDeEstudo grupo = grupoService.getById(grupoId);
        areaEstudo.getGrupoDeEstudos().add(grupo);
        return repository.save(areaEstudo);
    }

    public AreaEstudo removerGrupo(UUID areaEstudoId, UUID grupoId) {
        AreaEstudo areaEstudo = repository.findById(areaEstudoId)
                .orElseThrow(AreaEstudoNaoEncontradaException::new);

        GrupoDeEstudo grupo = grupoService.getById(grupoId);
        areaEstudo.getGrupoDeEstudos().remove(grupo);
        return repository.save(areaEstudo);
    }

}
