package pe.gob.vuce.zee.api.tesoreria.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "vems_tcam",schema = "vuce_zee", catalog = "zee_db")
public class TipoCambioEntity {
    @Id
    @Column(name = "vems_tcam_idllave_pk",nullable = false)
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;

    @Column(name = "vems_tcam_fecha_regs", nullable = false)
    private LocalDateTime fechaRegistro;

    @Column(name = "vems_tcam_impo_compr",nullable = false,length = 10,scale = 2)
    private BigDecimal cambioCompra;

    @Column(name = "vems_tcam_impo_venta",nullable = false,length = 10,scale = 2)
    private BigDecimal cambioVenta;

    @Column(name = "vems_tcam_cliente_fk", nullable = false)
    private Integer clienteId;

    @Column(name = "vems_tcam_organiz_fk", nullable = false)
    private Integer organizacionId;

    @Column(name = "vems_tcam_cod_estado", nullable = false)
    private Integer estado;

    @Column(name = "vems_tcam_cod_active",nullable = false)
    private Integer activo;

    @Column(name = "vems_tcam_datecreate", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "vems_tcam_usr_create",nullable = false)
    private UUID usuarioCreacionId;

    @Column(name = "vems_tcam_dateupdate")
    private LocalDateTime fechaModificacion;

    @Column(name = "vems_tcam_usr_update")
    private UUID usuarioModificacionId;

}
