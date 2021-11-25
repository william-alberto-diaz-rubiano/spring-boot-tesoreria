package pe.gob.vuce.zee.api.tesoreria.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.gob.vuce.zee.api.tesoreria.models.ConfiguradorOperacionEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConceptoPagoDTO {

    private UUID id;
    private String codigoSistema;
    private String codigoGenerado;
    private ConfiguradorOperacionEntity configuradorOperacion;
    private String nombreConcepto;
    private Integer diazPlazo;
    private Integer codigoCriterio;
    private Integer codigoMoneda;
    private BigDecimal porcentajeUIT;
    private Integer codigoOperacion;
    private BigDecimal montoPago;
    private Integer clienteId;
    private Integer organizacionId;
    private Integer estado;
    private Integer activo;
    private LocalDateTime fechaCreacion;
    private Integer usuarioCreacionId;
    private LocalDateTime fechaModificacion;
    private Integer usuarioModificacionId;
}
