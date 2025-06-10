package api.endeavorbackend.repositorios;

import api.endeavorbackend.models.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, UUID> {
    List<Materia> findByUsuarioId(UUID usuarioId);

    @Query("SELECT m FROM Materia m LEFT JOIN FETCH m.tempoMaterias WHERE m.id = :id")
    Optional<Materia> findByIdWithTempoMaterias(@Param("id") UUID id);
}
