package pe.gob.vuce.zee.api.tesoreria.dto;

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
    @NotNull(message = "El objeto de tramite de pago no puede ser nulo")
    private TramitePagoEntity tramitePago;
    @NotNull(message = "El codigo del modulo no puede ser nulo")
    private Integer codigoModulo;
    @NotNull(message = "El codigo del proceso no puede ser nulo")
    private Integer codigoProceso;
    @NotNull(message = "El codigo de formulario no puede ser nulo")
    private Integer codigoFormulario;
    @NotNull(message = "El codigo de accion no puede ser nulo")
    private Integer codigoAccion;
    private Integer clienteId;
    private Integer organizacionId;
    private Integer estado;
    private Integer activo;
    private LocalDateTime fechaCreacion;
    private UUID usuarioCreacionId;
    private LocalDateTime fechaModificacion;
    private UUID usuarioModificacionId;
}
