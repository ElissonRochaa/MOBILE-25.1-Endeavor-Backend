package api.endeavorbackend.repositorios;

import api.endeavorbackend.models.Materia;
import api.endeavorbackend.models.Meta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MetaRepository extends JpaRepository<Meta, UUID> {

    public List<Meta> findMetaByMateriaId(UUID materiaId);

}
