package api.endeavorbackend.services;

import api.endeavorbackend.models.Meta;

import java.util.List;

public interface MetaService {
    public Meta buscarMeta(Long id);
    public Meta atualizarMeta(Meta meta);
    public Meta adicionarMeta(Meta meta);
    public void removerMeta(Long id);
    public List<Meta> buscarMetas();
}
