package api.endeavorbackend.controllers;

import java.util.List;
import java.util.UUID;

import api.endeavorbackend.models.DTOs.UsuarioDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.endeavorbackend.models.Usuario;
import api.endeavorbackend.services.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        try {
            List<UsuarioDTO> usuarios = usuarioService.listarUsuarios();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    

    @GetMapping("/{id}")
    ResponseEntity<UsuarioDTO> buscarUsuarioPorId(@PathVariable UUID id) {
        try {
            UsuarioDTO usuario = UsuarioDTO.from(usuarioService.buscarUsuarioPorId(id));
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/atualizar")
    ResponseEntity<UsuarioDTO> atualizarUsuario(@RequestBody UsuarioDTO usuario) {
        try {
            usuarioService.atualizarUsuario(usuario);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> excluirUsuario(@PathVariable UUID id) {
        try {
            usuarioService.excluirUsuario(id);
            return ResponseEntity.ok("Usuário excluído com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }
    }

    @GetMapping("/usuarioJaCadastrado/{email}")
    ResponseEntity<Boolean> usuarioJaCadastrado(@PathVariable String email) {
        try {
            boolean existe = usuarioService.usuarioJaCadastrado(email);
            return ResponseEntity.ok(existe);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/buscarUsuariosPorNome/{nome}")
    ResponseEntity<List<UsuarioDTO>> buscarUsuariosPorNome(@PathVariable String nome) {
        try {
            List<UsuarioDTO> usuarios = usuarioService.buscarUsuariosPorNome(nome);
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
