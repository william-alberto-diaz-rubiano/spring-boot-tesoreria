package pe.gob.vuce.zee.api.tesoreria.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.gob.vuce.zee.api.tesoreria.models.ConfiguradorOperacionEntity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConceptoPagoDTO {

    private UUID id;

    @NotNull(message = "El UUID del configurador de operaciones no puede ser nulo")
    private UUID configuradorOperacionId;

    @NotNull(message = "La descripción del configurador de operaciones no puede ser nulo")
    private String configuradorOperacionTramiteDescripcion;


    private UUID codigoOperacionId;

    private UUID codigoOperacionDescripcion;

    @Size(max = 20,message = "La base legal soporta maximo 20 caracteres")
    @NotNull(message = "El codigo del sistema no puede ser nulo")
    private String codigoSistema;

    @Size(max = 10,message = "La base legal soporta maximo 10 caracteres")
    @NotNull(message = "El codigo generado no puede ser nulo")
    private String codigoGenerado;

    @NotNull(message = "El nombre del concepto no puede ser nulo")
    private String nombreConcepto;

    @NotNull(message = "Los dias de plazo no puede ser nulo")
    private Integer diazPlazo;

    @NotNull(message = "El Id del codigo de criterio no puede ser nulo")
    private UUID codigoCriterioId;

    private String codigoCriterioDescripcion;

    @NotNull(message = "El Id del codigo de la moneda no puede ser nulo")
    private UUID codigoMonedaId;

    private String codigoMonedaDescripcion;

    private BigDecimal porcentajeUIT;

    @NotNull(message = "El monto no puede ser nulo")
    private BigDecimal montoPago;

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
