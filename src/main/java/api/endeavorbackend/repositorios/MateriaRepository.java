package api.endeavorbackend.repositorios;

import api.endeavorbackend.models.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, UUID> {

}
