package api.endeavorbackend.repositorios;

import api.endeavorbackend.models.GrupoDeEstudo;
import api.endeavorbackend.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GrupoDeEstudoRepository extends JpaRepository<GrupoDeEstudo, UUID> {
    List<GrupoDeEstudo> findByAreaEstudoId(UUID areaEstudoId);
    List<GrupoDeEstudo> findByParticipantesContains(Usuario participante);
}
