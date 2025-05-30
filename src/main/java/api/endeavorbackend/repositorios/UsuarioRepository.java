package api.endeavorbackend.repositorios;

import api.endeavorbackend.models.AreaEstudo;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import api.endeavorbackend.models.Usuario;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findById(UUID id);
    boolean existsByEmail(String email);
    List<Usuario> findByNome(String nome);
    List<Usuario> findByAreaEstudo(AreaEstudo areaEstudo);
    
    
    
}
