package pe.gob.vuce.zee.api.maestros.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdentityException extends RuntimeException {
    private String code;
    public IdentityException(String message){
        super(message);
    }

    public IdentityException(String code, String message){
        super(message);
        this.code = code;
    }
}
