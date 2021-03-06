package pe.gob.vuce.zee.api.tesoreria.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class IdentityException extends RuntimeException{
    private String code;
    private HttpStatus status;

    public IdentityException(String message, String code, HttpStatus status) {
        super(message);
        this.code = code;
        this.status = status;
    }
}
