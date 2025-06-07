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
import api.endeavorbackend.models.DTOs.RegistroDTO;
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
    public ResponseEntity<String> register(@RequestBody @Valid RegistroDTO registroDTO) {
        if (this.usuarioRepository.findByEmail(registroDTO.email()) != null) {
            return ResponseEntity.badRequest().body("Email já cadastrado");
        }

        if (registroDTO.nome() == null || registroDTO.email() == null || registroDTO.senha() == null || registroDTO.escolaridade() == null) {
            return ResponseEntity.badRequest().body("Nome, email, senha, escolaridade e área de estudo são obrigatórios");
        }



        String senhaCriptografada = new BCryptPasswordEncoder().encode(registroDTO.senha());
        Usuario usuario = new Usuario(
                registroDTO.nome(),
                registroDTO.email(),
                senhaCriptografada,
                registroDTO.idade(),
                registroDTO.escolaridade(),
                Role.USER
        );

        this.usuarioRepository.save(usuario);
        return ResponseEntity.ok().body("Usuário cadastrado com sucesso");
    }
    
}
