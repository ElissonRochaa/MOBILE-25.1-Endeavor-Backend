package api.endeavorbackend.common.utils;

import api.endeavorbackend.common.exceptions.ExceptionBody;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

public class RequestUtils {

    public static String getFullRequestURL(HttpServletRequest request) {
        return request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
    }

}
