package api.endeavorbackend.repositorios;

import api.endeavorbackend.models.AreaEstudo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AreaEstudoRepository extends JpaRepository<AreaEstudo, UUID> {
    List<AreaEstudo> findByNomeContainingIgnoreCase(String nome);
}
