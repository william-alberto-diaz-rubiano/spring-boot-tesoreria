package pe.gob.vuce.zee.api.tesoreria.dto;

import lombok.*;
import pe.gob.vuce.zee.api.tesoreria.models.AccionPagoEntity;
import pe.gob.vuce.zee.api.tesoreria.models.ConfiguradorOperacionEntity;
import pe.gob.vuce.zee.api.tesoreria.models.TramitePagoEntity;

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
    private boolean tipoAccionPago;
    private Integer clienteId;
    private Integer organizacionId;
    private Integer estado;
    private Integer activo;
    private LocalDateTime fechaCreacion;
    private Integer usuarioCreacionId;
    private LocalDateTime fechaModificacion;
    private Integer usuarioModificacionId;

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
