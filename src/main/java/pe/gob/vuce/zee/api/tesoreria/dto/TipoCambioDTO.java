package pe.gob.vuce.zee.api.tesoreria.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.gob.vuce.zee.api.tesoreria.models.TipoCambioEntity;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoCambioDTO {

    private UUID id;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss")
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
    private Integer estado;
    private Integer activo;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaCreacion;
    private UUID usuarioCreacionId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaModificacion;
    private UUID usuarioModificacionId;

    public TipoCambioDTO(TipoCambioEntity tipoCambioEntity) {
        this.id = tipoCambioEntity.getId();
        this.fechaRegistro = tipoCambioEntity.getFechaRegistro();
        this.cambioCompra = tipoCambioEntity.getCambioCompra();
        this.cambioVenta = tipoCambioEntity.getCambioVenta();
        this.clienteId = tipoCambioEntity.getClienteId();
        this.organizacionId = tipoCambioEntity.getOrganizacionId();
        this.estado = tipoCambioEntity.getEstado();
        this.activo = tipoCambioEntity.getActivo();
        this.fechaCreacion = tipoCambioEntity.getFechaCreacion();
        this.usuarioCreacionId = tipoCambioEntity.getUsuarioCreacionId();
        this.fechaModificacion = tipoCambioEntity.getFechaModificacion();
        this.usuarioModificacionId = tipoCambioEntity.getUsuarioModificacionId();
    }

    public TipoCambioEntity toEntity() {
        var entity = new TipoCambioEntity();
        entity.setId(this.id);
        entity.setFechaRegistro(this.fechaRegistro);
        entity.setCambioCompra(this.cambioCompra);
        entity.setCambioVenta(this.cambioVenta);
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