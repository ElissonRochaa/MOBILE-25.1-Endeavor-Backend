package api.endeavorbackend.common.exceptions;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Builder
@AllArgsConstructor
@Getter @Setter
public class ExceptionBody {
    private int httpStatus;
    private String error;
    private String message;
    private String request;
    private Instant timeStamp;
}
