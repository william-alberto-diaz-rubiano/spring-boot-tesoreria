package pe.gob.vuce.zee.api.tesoreria.models;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "vepg_pago",schema = "vuce_zee", catalog = "zee_db")
public class ConceptoPagoEntity {
    @Id
    @Column(name = "vepg_pago_idllave_pk",nullable = false)
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;

    @Column(name = "vepg_pago_cod_sistem",nullable = false, length = 20)
    private String codigoSistema;

    @Column(name = "vepg_pago_cod_genera",nullable = false, length = 10)
    private String codigoGenerado;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vepg_pago_cod_tramit", referencedColumnName = "vems_copr_idllave_pk", nullable = false)
    private ConfiguradorOperacionEntity configuradorOperacion;

    @Column(name = "vepg_pago_nombre_con", nullable = false, length = 20)
    private String nombreConcepto;

    @Column(name = "vepg_pago_dias_plazo",nullable = false)
    private Integer diazPlazo;

    @ManyToOne
    @JoinColumn(name = "vepg_pago_cod_criter", referencedColumnName = "vems_gcon_idllave_pk")
    private MaestroEntity codigoCriterio;

    @ManyToOne
    @JoinColumn(name = "vepg_pago_cod_moneda", referencedColumnName = "vems_gcon_idllave_pk")
    private MaestroEntity codigoMoneda;

    @ManyToOne
    @JoinColumn(name = "vepg_pago_cod_operac", referencedColumnName = "vems_gcon_idllave_pk")
    private MaestroEntity codigoOperacion;

    @Column(name = "vepg_pago_porcen_uit", length = 10, scale = 2)
    private BigDecimal porcentajeUIT;

    @Column(name = "vepg_pago_monto_pago", length = 10, scale = 2)
    private BigDecimal montoPago;

    @Column(name = "vepg_pago_cliente_fk",nullable = false)
    private Integer clienteId;

    @Column(name = "vepg_pago_organiz_fk", nullable = false)
    private Integer organizacionId;

    @ManyToOne
    @JoinColumn(name = "vepg_pago_cod_estado", referencedColumnName = "vems_gcon_idllave_pk")
    private MaestroEntity estado;

    @Column(name = "vepg_pago_cod_active",nullable = false)
    private Integer activo;

    @Column(name = "vepg_pago_datecreate", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "vepg_pago_usr_create",nullable = false)
    private UUID usuarioCreacionId;

    @Column(name = "vepg_pago_dateupdate")
    private LocalDateTime fechaModificacion;

    @Column(name = "vepg_pago_usr_update")
    private UUID usuarioModificacionId;
}
