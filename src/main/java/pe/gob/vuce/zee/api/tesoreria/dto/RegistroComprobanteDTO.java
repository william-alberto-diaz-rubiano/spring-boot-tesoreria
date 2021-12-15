package pe.gob.vuce.zee.api.tesoreria.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistroComprobanteDTO {

    private UUID id;

    @NotNull(message = "El UUID del comprobante no puede ser nulo")
    private UUID codigoComprobanteId;

    private String codigoComprobanteDescripcion;

    @NotNull(message = "El codigo de la serie no puede ser nulo")

    @Size(max = 10,message = "El codigo de serie soporta maximo 10 caracteres")
    private String codigoSerie;

    private String correlativoInicial;

    @Size(max = 10,message = "El correlativo inicial soporta maximo 10 caracteres")
    @NotNull(message = "El correlativo inicial no puede ser nulo")
    private String correlativoAnual;

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
