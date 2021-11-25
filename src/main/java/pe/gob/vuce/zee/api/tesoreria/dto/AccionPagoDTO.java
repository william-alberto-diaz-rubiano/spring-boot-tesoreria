package pe.gob.vuce.zee.api.tesoreria.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.gob.vuce.zee.api.tesoreria.models.TramitePagoEntity;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccionPagoDTO {

    private UUID id;
    private TramitePagoEntity tramitePago;
    private Integer codigoModulo;
    private Integer codigoProceso;
    private Integer codigoFormulario;
    private Integer codigoAccion;
    private Integer clienteId;
    private Integer organizacionId;
    private Integer estado;
    private Integer activo;
    private LocalDateTime fechaCreacion;
    private Integer usuarioCreacionId;
    private LocalDateTime fechaModificacion;
    private Integer usuarioModificacionId;
}
