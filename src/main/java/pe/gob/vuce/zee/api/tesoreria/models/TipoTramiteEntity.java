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
@Table(name = "vepg_ttrm",schema = "vuce_zee", catalog = "zee_db")
public class TipoTramiteEntity {

    @Id
    @Column(name = "vepg_ttrm_idllave_pk",nullable = false)
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vepg_ttrm_id_tram_fk", referencedColumnName = "vepg_tram_idllave_pk", nullable = false)
    private TramitePagoEntity tramitePago;

    @ManyToOne
    @JoinColumn(name = "vepg_ttrm_cod_tipoca", referencedColumnName = "vems_gcon_idllave_pk")
    private MaestroEntity tipoCalculo;

    @Column(name = "vepg_ttrm_valor", length = 20)
    private String valor;

    @ManyToOne
    @JoinColumn(name = "vepg_ttrm_cod_destin", referencedColumnName = "vems_gcon_idllave_pk")
    private MaestroEntity codigoDestino;

    @ManyToOne
    @JoinColumn(name = "vepg_ttrm_cod_moneda", referencedColumnName = "vems_gcon_idllave_pk")
    private MaestroEntity codigoMoneda;

    @Column(name = "vepg_ttrm_porcen_uit", length = 10, scale = 2)
    private BigDecimal porcentajeUIT;

    @Column(name = "vepg_ttrm_monto_pago",nullable = false, length = 10, scale = 2)
    private BigDecimal montoPago;

    @Column(name = "vepg_ttrm_cant_inici", length = 10, scale = 2)
    private BigDecimal cantidadInicial;

    @Column(name = "vepg_ttrm_cant_final", length = 10, scale = 2)
    private BigDecimal cantidadFinal;

    @ManyToOne
    @JoinColumn(name = "vepg_ttrm_cod_modulo", referencedColumnName = "vems_gcon_idllave_pk")
    private MaestroEntity codigoModulo;

    @ManyToOne
    @JoinColumn(name = "vepg_ttrm_cod_proces", referencedColumnName = "vems_gcon_idllave_pk")
    private MaestroEntity codigoProceso;

    @ManyToOne
    @JoinColumn(name = "vepg_ttrm_cod_formul", referencedColumnName = "vems_gcon_idllave_pk")
    private MaestroEntity codigoFormulario;

    @ManyToOne
    @JoinColumn(name = "vepg_ttrm_cod_accion", referencedColumnName = "vems_gcon_idllave_pk")
    private MaestroEntity codigoAccion;

    @Column(name = "vepg_ttrm_preg_datoi", length = 100)
    private String preguntaDatoInformado;

    @Column(name = "vepg_ttrm_cliente_fk",nullable = false)
    private Integer clienteId;

    @Column(name = "vepg_ttrm_organiz_fk", nullable = false)
    private Integer organizacionId;

    @ManyToOne
    @JoinColumn(name = "vepg_ttrm_cod_estado", referencedColumnName = "vems_gcon_idllave_pk")
    private MaestroEntity estado;

    @Column(name = "vepg_ttrm_cod_active",nullable = false)
    private Integer activo;

    @Column(name = "vepg_ttrm_datecreate", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "vepg_ttrm_usr_create",nullable = false)
    private UUID usuarioCreacionId;

    @Column(name = "vepg_ttrm_dateupdate")
    private LocalDateTime fechaModificacion;

    @Column(name = "vepg_ttrm_usr_update")
    private UUID usuarioModificacionId;
}
