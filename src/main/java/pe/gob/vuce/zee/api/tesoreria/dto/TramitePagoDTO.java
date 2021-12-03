package pe.gob.vuce.zee.api.tesoreria.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import pe.gob.vuce.zee.api.tesoreria.models.ConfiguradorOperacionEntity;
import pe.gob.vuce.zee.api.tesoreria.models.TramitePagoEntity;

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
    @NotNull(message = "El objeto de configurador de operaciones no puede ser nulo")
    private ConfiguradorOperacionEntity configuradorOperacion;

    @Size(max = 20,message = "La base legal soporta maximo 20 caracteres")
    private String codigoSistema;

    @NotNull(message = "El codigo de proceso no puede ser nulo")
    private Integer codigoProceso;

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
    private boolean tipoAccionPago;
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

    public TramitePagoDTO(TramitePagoEntity tramitePagoEntity){
        this.id = tramitePagoEntity.getId();
        this.configuradorOperacion = tramitePagoEntity.getConfiguradorOperacion();
        this.codigoSistema = tramitePagoEntity.getCodigoSistema();
        this.codigoProceso = tramitePagoEntity.getCodigoProceso();
        this.flagDestinos = tramitePagoEntity.isFlagDestinos();
        this.codigoPago = tramitePagoEntity.getNombrePago();
        this.nombrePago = tramitePagoEntity.getNombrePago();
        this.baseLegal = tramitePagoEntity.getBaseLegal();
        this.diazPlazo = tramitePagoEntity.getDiazPlazo();
        this.tipoAccionPago = tramitePagoEntity.isTipoAccionPago();
        this.clienteId = tramitePagoEntity.getClienteId();
        this.organizacionId = tramitePagoEntity.getOrganizacionId();
        this.estado = tramitePagoEntity.getEstado();
        this.activo = tramitePagoEntity.getActivo();
        this.fechaCreacion = tramitePagoEntity.getFechaCreacion();
        this.usuarioCreacionId = tramitePagoEntity.getUsuarioCreacionId();
        this.fechaModificacion = tramitePagoEntity.getFechaModificacion();
        this.usuarioCreacionId = tramitePagoEntity.getUsuarioModificacionId();
    }

    public TramitePagoEntity toEntity(){
        var entity = new TramitePagoEntity();
        entity.setId(this.id);
        entity.setConfiguradorOperacion(this.configuradorOperacion);
        entity.setCodigoSistema(this.codigoSistema);
        entity.setCodigoProceso(this.codigoProceso);
        entity.setFlagDestinos(this.flagDestinos);
        entity.setCodigoPago(this.codigoPago);
        entity.setNombrePago(this.nombrePago);
        entity.setBaseLegal(this.baseLegal);
        entity.setDiazPlazo(this.diazPlazo);
        entity.setTipoAccionPago(this.tipoAccionPago);
        entity.setClienteId(this.clienteId);
        entity.setOrganizacionId(this.organizacionId);
        entity.setEstado(this.estado);
        entity.setActivo(this.activo);
        entity.setFechaCreacion(this.fechaCreacion);
        entity.setUsuarioCreacionId(this.usuarioCreacionId);
        entity.setFechaModificacion(this.fechaModificacion);
        entity.setUsuarioModificacionId(this.usuarioModificacionId);

        return entity;
    }
}
