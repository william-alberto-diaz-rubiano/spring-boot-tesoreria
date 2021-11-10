package pe.gob.vuce.zee.api.maestros.handlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import pe.gob.vuce.zee.api.maestros.dto.ResponseDTO;
import pe.gob.vuce.zee.api.maestros.exceptions.EntityNotFoundException;
import pe.gob.vuce.zee.api.maestros.exceptions.IdentityException;

import java.util.Optional;


@RestController
@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseDTO<?>> handleEntityNotFound(EntityNotFoundException ex) {
        var responseBody = new ResponseDTO<>(-1, ex.getMessage());
        return ResponseEntity.ok(responseBody);
    }

    @ExceptionHandler(IdentityException.class)
    public ResponseEntity<ResponseDTO<?>> handleIdentityException(IdentityException ex) {
        String code = Optional.ofNullable(ex.getCode()).orElse("NOCODE");
        var message = String.format("Error al consultar identidad: %s - %s", code, ex.getMessage());
        var responseBody = new ResponseDTO<>(-1, message);
        return ResponseEntity.ok(responseBody);
    }
}
