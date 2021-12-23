package pe.gob.vuce.zee.api.tesoreria.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TramitePagoDTO {

    private UUID id;

    @NotNull(message = "El UUID del configurador de operaciones no puede ser nulo")
    private UUID configuradorOperacionId;

    private UUID configuradorOperacionTramiteId;

    @NotNull(message = "La descripción del configurador de operaciones no puede ser nulo")
    private String configuradorOperacionTramiteDescripcion;

    private UUID configuradorOperacionOperacionId;

    private String configuradorOperacionOperacionDescripcion;

    @Size(max = 20,message = "La base legal soporta maximo 20 caracteres")
    private String codigoSistema;

    @NotNull(message = "El codigo de proceso no puede ser nulo")
    private UUID codigoProcesoId;

    private String codigoProcesoDescripcion;

    private boolean flagDestinos;
    @NotNull(message = "El codigo del tramite no puede ser nulo")
    @Size(max = 20,message = "El codigo del tramite soporta maximo 20 caracteres")
    private String codigoPago;

    @NotNull(message = "El nombre del no puede ser nulo")
    @Size(max = 50,message = "El nombre del tramite soporta maximo 50 caracteres")
    private String nombrePago;

    @NotNull(message = "La base legal no puede ser nula")
    @Size(max = 50,message = "La base legal soporta maximo 50 caracteres")
    private String baseLegal;
    private Integer diazPlazo;
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
