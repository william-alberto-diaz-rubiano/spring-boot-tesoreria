package pe.gob.vuce.zee.api.tesoreria.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoCambioDTO {

    private UUID id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "La fecha de registro no puede ser nula")
    private LocalDateTime fechaRegistro;

    @NotNull(message = "El cambio de compra no puede ser nulo")
    @Digits(integer = 10, fraction = 2, message = "El cambio de compra debe ser de maximo 10 digitos y maximo 2 fraciones")
    private BigDecimal cambioCompra;

    @Digits(integer = 10, fraction = 2, message = "El cambio de venta debe ser de maximo 10 digitos y maximo 2 fraciones")
    @NotNull(message = "El cambio de venta no puede ser nulo")
    private BigDecimal cambioVenta;

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

    @JsonFormat(pattern = "yyyy")
    public LocalDateTime getAnio(){
        return fechaRegistro;
    }

    @JsonFormat(pattern = "MM")
    public LocalDateTime getMes(){
        return fechaRegistro;
    }

    @JsonFormat(pattern = "dd")
    public LocalDateTime getDia(){
        return fechaRegistro;
    }

}