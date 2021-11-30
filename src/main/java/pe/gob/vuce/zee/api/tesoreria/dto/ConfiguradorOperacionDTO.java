package pe.gob.vuce.zee.api.tesoreria.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.gob.vuce.zee.api.tesoreria.models.ConfiguradorOperacionEntity;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfiguradorOperacionDTO {

    private UUID id;

    @NotNull(message = "El tramite no puede ser nulo")
    private Integer tramite;

    @NotNull(message = "la operacion no puede ser nula")
    private Integer operacion;
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

    public ConfiguradorOperacionDTO(ConfiguradorOperacionEntity configuradorOperacionEntity) {
        this.id = configuradorOperacionEntity.getId();
        this.tramite = configuradorOperacionEntity.getTramite();
        this.operacion = configuradorOperacionEntity.getOperacion();
        this.clienteId = configuradorOperacionEntity.getClienteId();
        this.organizacionId = configuradorOperacionEntity.getOrganizacionId();
        this.estado = configuradorOperacionEntity.getEstado();
        this.activo = configuradorOperacionEntity.getActivo();
        this.fechaCreacion = configuradorOperacionEntity.getFechaCreacion();
        this.usuarioCreacionId = configuradorOperacionEntity.getUsuarioCreacionId();
        this.fechaModificacion = configuradorOperacionEntity.getFechaModificacion();
        this.usuarioModificacionId = configuradorOperacionEntity.getUsuarioModificacionId();
    }

    public ConfiguradorOperacionEntity toEntity(){
        var configuradorOperacion = new ConfiguradorOperacionEntity();
        configuradorOperacion.setId(this.id);
        configuradorOperacion.setTramite(this.tramite);
        configuradorOperacion.setOperacion(this.operacion);
        configuradorOperacion.setClienteId(this.clienteId);
        configuradorOperacion.setOrganizacionId(this.organizacionId);
        configuradorOperacion.setEstado(this.estado);
        configuradorOperacion.setActivo(this.activo);
        configuradorOperacion.setFechaCreacion(this.fechaCreacion);
        configuradorOperacion.setUsuarioCreacionId(this.usuarioCreacionId);
        configuradorOperacion.setFechaModificacion(this.fechaModificacion);
        configuradorOperacion.setUsuarioModificacionId(this.usuarioModificacionId);

        return configuradorOperacion;
    }

}
