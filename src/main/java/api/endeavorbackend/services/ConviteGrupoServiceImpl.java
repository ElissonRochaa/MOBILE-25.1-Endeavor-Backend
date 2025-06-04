package api.endeavorbackend.services;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.stereotype.Service;
import api.endeavorbackend.models.ConviteGrupo;
import api.endeavorbackend.models.GrupoDeEstudo;
import api.endeavorbackend.repositorios.ConviteGrupoRepository;
import api.endeavorbackend.repositorios.GrupoDeEstudoRepository;

@Service
public class ConviteGrupoServiceImpl implements ConviteGrupoService {

    private final GrupoDeEstudoRepository grupoRepository;
    private final ConviteGrupoRepository conviteRepository;
    private final GrupoDeEstudoService grupoService;

    public ConviteGrupoServiceImpl(GrupoDeEstudoRepository grupoRepository, ConviteGrupoRepository conviteRepository, GrupoDeEstudoService grupoService) {
        this.grupoService = grupoService;
        this.conviteRepository = conviteRepository;
        this.grupoRepository = grupoRepository;
    }

    @Override
    public String criarConvite(UUID grupoId) {
       GrupoDeEstudo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new RuntimeException("Grupo de estudo não encontrado"));

        String token = UUID.randomUUID().toString();

        ConviteGrupo convite = new ConviteGrupo();
        convite.setGrupo(grupo);
        convite.setToken(token);
        convite.setCriadoEm(LocalDateTime.now());
        convite.setExpiraEm(LocalDateTime.now().plusDays(1));
        convite.setAtivo(true);

        conviteRepository.save(convite);

        return "https://localhost:8080/api/convite/aceitar-convite/" + token;

    }

    @Override
    public void aceitarConvite(String token, UUID usuarioId) {
        ConviteGrupo convite = conviteRepository.findByToken(token);
        
        if (convite == null || !convite.isAtivo() || convite.getExpiraEm().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Convite inválido ou expirado");
        }

        boolean usuarioJaExiste = convite.getGrupo().getParticipantes()
                .stream()
                .anyMatch(usuario -> usuario.getId().equals(usuarioId));
        
        if (usuarioJaExiste) {
            throw new RuntimeException("Usuário já é membro deste grupo");
        }

        GrupoDeEstudo grupo = convite.getGrupo();
        grupoService.adicionarUsuarioAoGrupo(grupo.getId(), usuarioId);

    }
    
}
