package pe.gob.vuce.zee.api.tesoreria.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pe.gob.vuce.zee.api.tesoreria.dto.ErrorDTO;
import pe.gob.vuce.zee.api.tesoreria.dto.ResponseDTO;
import pe.gob.vuce.zee.api.tesoreria.exceptions.BadRequestException;
import pe.gob.vuce.zee.api.tesoreria.exceptions.EntityNotFoundException;
import pe.gob.vuce.zee.api.tesoreria.exceptions.IdentityException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ErrorDTO> badRequestExceptionHandler(HttpServletRequest request, BadRequestException ex) {
        ErrorDTO error = ErrorDTO.builder().code(ex.getCode()).path(request.getRequestURI()).statusValue(ex.getStatus().value()).message(ex.getMessage()).build();
        return new ResponseEntity<>(error, ex.getStatus());
    }

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


}
