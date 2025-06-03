package api.endeavorbackend.repositorios;

import api.endeavorbackend.models.ConviteGrupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ConviteGrupoRepository extends JpaRepository<ConviteGrupo, UUID> {
    ConviteGrupo findByToken(String token);
}