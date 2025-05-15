package api.endeavorbackend.repositorios;

import api.endeavorbackend.models.AreaEstudo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaEstudoRepository extends JpaRepository<AreaEstudo, Long> {

}
