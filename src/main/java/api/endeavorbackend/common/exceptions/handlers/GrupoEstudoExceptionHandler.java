package api.endeavorbackend.common.exceptions.handlers;

import api.endeavorbackend.common.exceptions.ExceptionBody;
import api.endeavorbackend.common.utils.RequestUtils;
import api.endeavorbackend.models.exceptions.grupoEstudo.GrupoEstudoNaoEncontradoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
@Order(1)
public class GrupoEstudoExceptionHandler {

    @ExceptionHandler(GrupoEstudoNaoEncontradoException.class)
    public ResponseEntity<ExceptionBody> handleGrupoEstudoNaoEncontrado(
            GrupoEstudoNaoEncontradoException ex, HttpServletRequest req) {
        System.out.println("Handler chamado");
        ExceptionBody body = ExceptionBody.builder()
                .httpStatus(HttpStatus.NOT_FOUND.value())
                .error("Grupo de Estudo não encontrado")
                .message(ex.getMessage())
                .request(RequestUtils.getFullRequestURL(req))
                .timeStamp(Instant.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
}