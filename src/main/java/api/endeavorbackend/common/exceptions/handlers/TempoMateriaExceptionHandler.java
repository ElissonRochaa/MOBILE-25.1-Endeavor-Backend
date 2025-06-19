package api.endeavorbackend.common.exceptions.handlers;

import api.endeavorbackend.common.exceptions.ExceptionBody;
import api.endeavorbackend.common.utils.RequestUtils;
import api.endeavorbackend.models.exceptions.tempoMateria.SessaoEmAndamentoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
@ControllerAdvice
@Order(1)
public class TempoMateriaExceptionHandler {
    @ExceptionHandler(SessaoEmAndamentoException.class)
    public ResponseEntity<ExceptionBody> handleSessaoEmAndamento(
            SessaoEmAndamentoException ex, HttpServletRequest req) {
        ExceptionBody body = ExceptionBody.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .error("Já existe sessão em andamento")
                .message(ex.getMessage())
                .request(RequestUtils.getFullRequestURL(req))
                .timeStamp(Instant.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}
