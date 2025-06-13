package api.endeavorbackend.services;

import api.endeavorbackend.models.Meta;
import api.endeavorbackend.repositorios.MateriaRepository;
import api.endeavorbackend.repositorios.MetaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MetaServiceImpl implements MetaService {
    private final MetaRepository metaRepository;

    public MetaServiceImpl(MetaRepository metaRepository) {
        this.metaRepository = metaRepository;
    }

    @Override
    public Meta buscarMeta(UUID id) {
        if (metaRepository.findById(id).isPresent()) {
            return metaRepository.findById(id).get();
        } else {
            throw new EntityNotFoundException("Meta no encontrada");
        }
    }

    @Override
    public Meta atualizarMeta(Meta meta) {
        if (metaRepository.findById(meta.getId()).isPresent()) {
            metaRepository.save(meta);
        } else {
            throw new EntityNotFoundException("Meta no encontrada");
        }
        return meta;

    }

    @Override
    public Meta adicionarMeta(Meta meta) {
        return metaRepository.save(meta);
    }

    @Override
    public void removerMeta(UUID id) {
        if (metaRepository.findById(id).isPresent()) {
            metaRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Meta não encontrada");
        }
    }

    @Override
    public List<Meta> buscarMetas() {
        return metaRepository.findAll();
    }

    @Override
    public List<Meta> buscarMetasPorMateria(UUID materiaId) {
        return metaRepository.findMetaByMateriaId(materiaId);
    }
}
