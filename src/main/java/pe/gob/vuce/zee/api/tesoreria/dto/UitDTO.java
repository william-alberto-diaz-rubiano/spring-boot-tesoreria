package pe.gob.vuce.zee.api.tesoreria.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UitDTO {

    private UUID id;
    @NotNull(message = "El año de registro del UIT no puede ser nulo")
    private String anioUit;
    @NotNull(message = "La fecha de registro no puede ser nula")
    @Digits(integer = 10, fraction = 2, message = "El valor del UIT debe ser de maximo 10 digitos y maximo 2 fraciones")
    private Double valorSolesUit;
    private Integer clienteId;
    private Integer organizacionId;
    private Integer estado;
    private Integer activo;
    private LocalDateTime fechaCreacion;
    private UUID usuarioCreacionId;
    private LocalDateTime fechaModificacion;
    private UUID usuarioModificacionId;
}
