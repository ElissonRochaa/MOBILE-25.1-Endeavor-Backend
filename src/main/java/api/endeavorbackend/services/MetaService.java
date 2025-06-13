package api.endeavorbackend.services;

import api.endeavorbackend.models.Meta;

import java.util.List;
import java.util.UUID;

public interface MetaService {
     Meta buscarMeta(UUID id);
     Meta atualizarMeta(Meta meta);
     Meta adicionarMeta(Meta meta);
     void removerMeta(UUID id);
     List<Meta> buscarMetas();
     List<Meta> buscarMetasPorMateria(UUID materiaId);
}
