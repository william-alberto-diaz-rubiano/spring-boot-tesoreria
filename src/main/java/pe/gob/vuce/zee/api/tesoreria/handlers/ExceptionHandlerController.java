package pe.gob.vuce.zee.api.tesoreria.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import pe.gob.vuce.zee.api.tesoreria.dto.ResponseDTO;
import pe.gob.vuce.zee.api.tesoreria.exceptions.EntityNotFoundException;
import pe.gob.vuce.zee.api.tesoreria.exceptions.IdentityException;

import java.util.Optional;

@RestController
@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseDTO> handleEntityNotFound(EntityNotFoundException ex) {
        var responseBody = new ResponseDTO(null, ex.getMessage());
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(IdentityException.class)
    public ResponseEntity<ResponseDTO> handleIdentityException(IdentityException ex) {
        String code = Optional.ofNullable(ex.getCode()).orElse("NOCODE");
        var message = String.format("Error al consultar identidad: %s - %s", code, ex.getMessage());
        var responseBody = new ResponseDTO(null, message);
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleExceptions(Exception ex) {
        var message = String.format("Excepción encontrada: %s - %s", ex.getCause(), ex.getMessage());
        var responseBody = new ResponseDTO("400" , message);
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.BAD_REQUEST);
    }

}
