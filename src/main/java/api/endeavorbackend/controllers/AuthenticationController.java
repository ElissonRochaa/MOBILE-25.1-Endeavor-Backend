package api.endeavorbackend.controllers;

import api.endeavorbackend.models.exceptions.UsuarioNaoEncontradoException;
import api.endeavorbackend.repositorios.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import api.endeavorbackend.config.TokenService;
import api.endeavorbackend.models.DTOs.AuthenticationDTO;
import api.endeavorbackend.models.DTOs.LoginResponseDTO;
import api.endeavorbackend.models.DTOs.TokenDTO;
import api.endeavorbackend.models.DTOs.UsuarioRegistroDTO;
import api.endeavorbackend.models.enuns.Role;
import api.endeavorbackend.models.Usuario;


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
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO authenticationDTO) {
        var userSenha = new UsernamePasswordAuthenticationToken(authenticationDTO.email(), authenticationDTO.senha());
        var authentication = authenticationManager.authenticate(userSenha);

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

    @PostMapping("/registro")
    public ResponseEntity<String> register(@RequestBody @Valid UsuarioRegistroDTO usuarioDTO) {
        if (this.usuarioRepository.findByEmail(usuarioDTO.email()).isPresent()) {
            return ResponseEntity.badRequest().body("Email já cadastrado");
        }


        String senhaCriptografada = new BCryptPasswordEncoder().encode(usuarioDTO.senha());
        Usuario usuario = new Usuario(
                usuarioDTO.nome(),
                usuarioDTO.email().toLowerCase(),
                senhaCriptografada,
                usuarioDTO.idade(),
                usuarioDTO.escolaridade(),
                Role.USER
        );

        this.usuarioRepository.save(usuario);
        return ResponseEntity.ok().body("Usuário cadastrado com sucesso");
    }

    @PostMapping("/token-valido")
    public ResponseEntity<Boolean> tokenValido(@RequestBody TokenDTO auth) {
        try {
            if (auth.getToken() == null) {
                return ResponseEntity.ok(false);
            }

            String tokenJwt = auth.getToken().replace("Bearer ", "");

            String email = tokenService.validateToken(tokenJwt);

            if (email == null || email.isEmpty()) {
                return ResponseEntity.ok(false);
            }

            return ResponseEntity.ok(true);

        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }


}
