package pe.gob.vuce.zee.api.tesoreria.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.gob.vuce.zee.api.tesoreria.models.TramitePagoEntity;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccionPagoDTO {

    private UUID id;

    private UUID tramitePagoId;

    @NotNull(message = "El codigo del modulo no puede ser nulo")
    private UUID codigoModuloId;
    private String codigoModuloDescripcion;

    @NotNull(message = "El codigo del proceso no puede ser nulo")
    private UUID codigoProcesoId;
    private String codigoProcesoDescripcion;

    @NotNull(message = "El codigo de formulario no puede ser nulo")
    private UUID codigoFormularioId;
    private String codigoFormularioDescripcion;

    @NotNull(message = "El codigo de accion no puede ser nulo")
    private UUID codigoAccionId;
    private String codigoAccionDescripcion;

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
