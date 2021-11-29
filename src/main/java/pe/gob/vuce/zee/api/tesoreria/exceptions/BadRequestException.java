package pe.gob.vuce.zee.api.tesoreria.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class BadRequestException extends RuntimeException {

    private String code;
    private HttpStatus status;
    private List<String> errors;

    public BadRequestException(String code, HttpStatus status, String message) {
        super(message);
        this.code = code;
        this.status = status;
    }
    public BadRequestException(String code, HttpStatus status,List<String> errors, String message) {
        super(message);
        this.code = code;
        this.status = status;
        this.errors = errors;
    }

}
