package api.endeavorbackend.common.exceptions.handlers;

import api.endeavorbackend.common.exceptions.ExceptionBody;
import api.endeavorbackend.common.utils.RequestUtils;
import api.endeavorbackend.models.exceptions.UsuarioNaoEncontradoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.Instant;

@ControllerAdvice
@Order(1)
public class AuthExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionBody> handleResponseStatusException(
            ResponseStatusException ex, HttpServletRequest req) {

        ExceptionBody body = ExceptionBody.builder()
                .httpStatus(ex.getStatusCode().value())
                .error("Erro de autenticação")
                .message(ex.getReason() != null ? ex.getReason() : "Erro não especificado")
                .request(RequestUtils.getFullRequestURL(req))
                .timeStamp(Instant.now())
                .build();

        return ResponseEntity.status(ex.getStatusCode()).body(body);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionBody> handleUsernameNotFound(
            UsernameNotFoundException ex, HttpServletRequest req) {

        ExceptionBody body = ExceptionBody.builder()
                .httpStatus(HttpStatus.UNAUTHORIZED.value())
                .error("Usuário não encontrado")
                .message(ex.getMessage())
                .request(RequestUtils.getFullRequestURL(req))
                .timeStamp(Instant.now())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }
    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<ExceptionBody> handleUsuarioNaoEncontrado(
            UsuarioNaoEncontradoException ex,
            HttpServletRequest req) {

        var body = ExceptionBody.builder()
                .httpStatus(HttpStatus.UNAUTHORIZED.value())
                .error("Usuário não encontrado")
                .message(ex.getMessage())
                .request(RequestUtils.getFullRequestURL(req))
                .timeStamp(Instant.now())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }
}
