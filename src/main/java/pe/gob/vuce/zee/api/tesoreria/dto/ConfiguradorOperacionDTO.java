package pe.gob.vuce.zee.api.tesoreria.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfiguradorOperacionDTO {

    private UUID id;
    private Integer tramite;
    private Integer operacion;
    private Integer clienteId;
    private Integer organizacionId;
    private Integer estado;
    private Integer activo;
    private LocalDateTime fechaCreacion;
    private Integer usuarioCreacionId;
    private LocalDateTime fechaModificacion;
    private Integer usuarioModificacionId;
}
