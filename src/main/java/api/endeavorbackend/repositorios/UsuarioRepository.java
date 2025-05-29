package api.endeavorbackend.repositorios;

import api.endeavorbackend.models.AreaEstudo;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import api.endeavorbackend.models.Usuario;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    UserDetails findByEmail(String email);
    Optional<Usuario> findById(long id);
    boolean existsByEmail(String email);
    List<Usuario> findByNome(String nome);
    List<Usuario> findByAreaEstudo(AreaEstudo areaEstudo);
    
    
    
}
