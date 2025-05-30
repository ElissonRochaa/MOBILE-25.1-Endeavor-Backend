package api.endeavorbackend.config;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import api.endeavorbackend.models.Usuario;
@Service
public class TokenService {
    
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            System.out.println("Gerando token JWT para o usuário: " + usuario.getEmail());
            String token = JWT.create()
                              .withIssuer("Api-Endeavor")
                              .withSubject(usuario.getEmail())
                              .withExpiresAt(expirationDate())
                              .sign(algorithm);
            
            return token;

        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar o token JWT", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);


            var email = JWT.require(algorithm)
                      .withIssuer("Api-Endeavor")
                      .build()
                      .verify(token)
                      .getSubject();

            System.out.println("Token válido para o usuário: " + email);
            if (email == null || email.isEmpty()) {
                throw new JWTVerificationException("Token inválido: usuário não encontrado");
            }
            return email;

        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    private Instant expirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
