package api.endeavorbackend.repositorios;

import api.endeavorbackend.models.AreaEstudo;
import api.endeavorbackend.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
}
