package api.endeavorbackend.common.exceptions.handlers;

import api.endeavorbackend.common.exceptions.ExceptionBody;
import api.endeavorbackend.common.utils.RequestUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class UnhandledExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionBody> handleUnresolvedException(Exception ex, HttpServletRequest req) {

        var response = ExceptionBody.builder()
            .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .error("Erro Interno do Servidor")
            .message("Ocorreu um erro inesperado no servidor, tente novamente mais tarde")
            .request(RequestUtils.getFullRequestURL(req))
            .timeStamp(Instant.now())
            .build();

        return ResponseEntity.internalServerError().body(response);
    }

}