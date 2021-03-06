package pe.gob.vuce.zee.api.tesoreria.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.gob.vuce.zee.api.tesoreria.models.ConfiguradorOperacionEntity;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfiguradorOperacionDTO {

    private UUID id;

    @NotNull(message = "El tramite no puede ser nulo")
    private UUID tramiteId;

    private String tramiteDescripcion;

    @NotNull(message = "la operacion no puede ser nula")
    private UUID operacionId;

    private String operacionDescripcion;

    private Integer clienteId;
    private Integer organizacionId;

    private UUID estadoId;
    private String estadoDescripcion;

    private Integer activo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaCreacion;
    private UUID usuarioCreacionId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaModificacion;
    private UUID usuarioModificacionId;

}
