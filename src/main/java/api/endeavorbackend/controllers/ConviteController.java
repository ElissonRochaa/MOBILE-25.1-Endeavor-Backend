package api.endeavorbackend.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.endeavorbackend.models.DTOs.CriacaoConviteDTO;
import api.endeavorbackend.services.ConviteGrupoService;

@RestController
@RequestMapping("/api/convite")
public class ConviteController {
    private final ConviteGrupoService conviteGrupoService;

    public ConviteController(ConviteGrupoService conviteGrupoService) {
        this.conviteGrupoService = conviteGrupoService;
    }

    @PostMapping("/criar-convite")
    public ResponseEntity<String> criarConvite(@RequestBody CriacaoConviteDTO criacaoConviteDTO) {
        if (criacaoConviteDTO.grupoId() == null) {
            return ResponseEntity.badRequest().body("ID do grupo não pode ser nulo");
        }

        String link = conviteGrupoService.criarConvite(criacaoConviteDTO.grupoId());

        return ResponseEntity.ok(link);
    }

    @PostMapping("/aceitar-convite/{token}")
    public ResponseEntity<String> aceitarConvite(@PathVariable String token, @RequestBody UUID usuarioId) {
        if (token == null || token.isEmpty()) {
            return ResponseEntity.badRequest().body("Token não pode ser nulo ou vazio");
        }
        if (usuarioId == null) {
            return ResponseEntity.badRequest().body("ID do usuário não pode ser nulo");
        }

        try {
            conviteGrupoService.aceitarConvite(token, usuarioId);
            return ResponseEntity.ok("Convite aceito com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    
}
