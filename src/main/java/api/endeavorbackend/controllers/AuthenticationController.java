package api.endeavorbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.endeavorbackend.config.TokenService;
import api.endeavorbackend.dtos.AuthenticationDTO;
import api.endeavorbackend.dtos.LoginResponseDTO;
import api.endeavorbackend.dtos.UsuarioDTO;
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
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Validated AuthenticationDTO authenticationDTO) {
        var userSenha = new UsernamePasswordAuthenticationToken(authenticationDTO.email(), authenticationDTO.senha());
        var authentication = authenticationManager.authenticate(userSenha);

        var token = tokenService.generateToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @RequestMapping("/registro")
    public ResponseEntity<String> register(@RequestBody @Validated UsuarioDTO usuarioDTO) {
        if (this.usuarioRepository.findByEmail(usuarioDTO.email()) != null) {
            return ResponseEntity.badRequest().body("Email já cadastrado");
        }

        String senhaCriptografada = new BCryptPasswordEncoder().encode(usuarioDTO.senha());
        Usuario usuario = new Usuario(
                usuarioDTO.nome(),
                usuarioDTO.email(),
                senhaCriptografada,
                usuarioDTO.idade(),
                usuarioDTO.escolaridade(),
                usuarioDTO.areaEstudo(),
                usuarioDTO.role()
        );

        this.usuarioRepository.save(usuario);
        return ResponseEntity.ok().body("Usuário cadastrado com sucesso");
    }
    
}
