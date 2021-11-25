package pe.gob.vuce.zee.api.tesoreria.dto;

import lombok.*;
import pe.gob.vuce.zee.api.tesoreria.models.AccionPagoEntity;
import pe.gob.vuce.zee.api.tesoreria.models.ConfiguradorOperacionEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TramitePagoDTO {

    private UUID id;
    private ConfiguradorOperacionEntity configuradorOperacion;
    private String codigoSistema;
    private Integer codigoProceso;
    private boolean flagDestinos;
    private String codigoPago;
    private String nombrePago;
    private String baseLegal;
    private Integer diazPlazo;
    private List<AccionPagoEntity> accionPagos;
    private Integer clienteId;
    private Integer organizacionId;
    private Integer estado;
    private Integer activo;
    private LocalDateTime fechaCreacion;
    private Integer usuarioCreacionId;
    private LocalDateTime fechaModificacion;
    private Integer usuarioModificacionId;
}
