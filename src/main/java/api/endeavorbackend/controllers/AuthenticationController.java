package api.endeavorbackend.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.endeavorbackend.config.TokenService;
import api.endeavorbackend.models.DTOs.AuthenticationDTO;
import api.endeavorbackend.models.DTOs.LoginResponseDTO;
import api.endeavorbackend.models.DTOs.UsuarioDTO;
import api.endeavorbackend.models.enuns.Role;
import api.endeavorbackend.models.Usuario;
import api.endeavorbackend.repositorios.UsuarioRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @RequestMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO authenticationDTO) {
        var userSenha = new UsernamePasswordAuthenticationToken(authenticationDTO.email(), authenticationDTO.senha());
        var authentication = authenticationManager.authenticate(userSenha);

        var token = tokenService.generateToken((Usuario) authentication.getPrincipal());
        var userId = ((Usuario) authentication.getPrincipal()).getId();


        return ResponseEntity.ok(new LoginResponseDTO(userId ,token));
    }

    @RequestMapping("/registro")
    public ResponseEntity<String> register(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        if (this.usuarioRepository.findByEmail(usuarioDTO.email()) != null) {
            return ResponseEntity.badRequest().body("Email já cadastrado");
        }

        if (usuarioDTO.nome() == null || usuarioDTO.email() == null || usuarioDTO.senha() == null || usuarioDTO.escolaridade() == null) {
            return ResponseEntity.badRequest().body("Nome, email, senha, escolaridade e área de estudo são obrigatórios");
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
