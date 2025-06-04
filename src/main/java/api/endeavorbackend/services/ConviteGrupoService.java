package api.endeavorbackend.services;

import java.util.UUID;

public interface ConviteGrupoService {
    String criarConvite(UUID grupoId);
    void aceitarConvite(String token, UUID usuarioId);
}
