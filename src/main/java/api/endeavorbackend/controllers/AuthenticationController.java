package api.endeavorbackend.controllers;

import api.endeavorbackend.common.exceptions.ExceptionBody;
import api.endeavorbackend.models.exceptions.UsuarioNaoEncontradoException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.endeavorbackend.config.TokenService;
import api.endeavorbackend.models.DTOs.AuthenticationDTO;
import api.endeavorbackend.models.DTOs.LoginResponseDTO;
import api.endeavorbackend.models.DTOs.UsuarioDTO;
import api.endeavorbackend.models.DTOs.UsuarioRegistroDTO;
import api.endeavorbackend.models.enuns.Role;
import api.endeavorbackend.models.Usuario;
import api.endeavorbackend.repositorios.UsuarioRepository;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody @Valid AuthenticationDTO authenticationDTO) {

        usuarioRepository.findByEmail(authenticationDTO.email())
                .orElseThrow(() -> new UsuarioNaoEncontradoException(authenticationDTO.email()));

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationDTO.email(),
                        authenticationDTO.senha()
                )
        );

        String token = tokenService.generateToken((Usuario) auth.getPrincipal());
        var userId = tokenService.getUserId(token);

        return ResponseEntity.ok(new LoginResponseDTO(userId, token));
    }

    @RequestMapping("/registro")
    public ResponseEntity<String> register(@RequestBody @Valid UsuarioRegistroDTO usuarioDTO) {
        if (this.usuarioRepository.findByEmail(usuarioDTO.email()).isPresent()) {
            return ResponseEntity.badRequest().body("Email já cadastrado");
        }


        String senhaCriptografada = new BCryptPasswordEncoder().encode(usuarioDTO.senha());
        Usuario usuario = new Usuario(
                usuarioDTO.nome(),
                usuarioDTO.email(),
                senhaCriptografada,
                usuarioDTO.idade(),
                usuarioDTO.escolaridade(),
                Role.USER
        );

        this.usuarioRepository.save(usuario);
        return ResponseEntity.ok().body("Usuário cadastrado com sucesso");
    }

}
