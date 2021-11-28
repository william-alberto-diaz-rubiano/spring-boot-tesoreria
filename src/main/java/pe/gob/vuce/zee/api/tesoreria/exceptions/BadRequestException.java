package pe.gob.vuce.zee.api.tesoreria.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper=false)
public class BadRequestException extends RuntimeException {

    private String code;
    private HttpStatus status;

    public BadRequestException(String code, HttpStatus status, String message) {
        super(message);
        this.code = code;
        this.status = status;
    }
}
