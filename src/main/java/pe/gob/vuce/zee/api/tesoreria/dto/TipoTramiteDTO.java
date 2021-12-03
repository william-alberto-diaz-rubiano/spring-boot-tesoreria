package pe.gob.vuce.zee.api.tesoreria.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.gob.vuce.zee.api.tesoreria.models.TramitePagoEntity;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoTramiteDTO {

    private UUID id;
    private TramitePagoEntity tramitePago;

    @NotNull(message = "El tipo de calculo no puede ser nulo")
    private Integer tipoCalculo;
    @NotNull(message = "El codigo del destino no puede ser nulo")
    private Integer codigoDestino;
    @NotNull(message = "El codigo de la moneda no puede ser nulo")
    private Integer codigoMoneda;
    @Digits(integer = 10, fraction = 2, message = "El porcentaje de UIT debe ser de maximo 10 digitos y maximo 2 fraciones")
    private BigDecimal porcentajeUIT;
    @NotNull(message = "El monto de pago no puede ser nulo")
    @Digits(integer = 10, fraction = 2, message = "El monto de pago debe ser de maximo 10 digitos y maximo 2 fraciones")
    private BigDecimal montoPago;
    @Digits(integer = 10, fraction = 2, message = "La cantidad inicial debe ser de maximo 10 digitos y maximo 2 fraciones")
    private BigDecimal cantidadInicial;
    @Digits(integer = 10, fraction = 2, message = "El cantidad final debe ser de maximo 10 digitos y maximo 2 fraciones")
    private BigDecimal cantidadFinal;
    private Integer codigoModulo;
    private Integer codigoProceso;
    private Integer codigoFormulario;
    private Integer codigoAccion;
    @Size(max = 100,message = "La pregunta con dato informado soporta maximo 100 caracteres")
    private String preguntaDatoInformado;
    private Integer clienteId;
    private Integer organizacionId;
    private Integer estado;
    private Integer activo;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaCreacion;
    private UUID usuarioCreacionId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaModificacion;
    private UUID usuarioModificacionId;
}
