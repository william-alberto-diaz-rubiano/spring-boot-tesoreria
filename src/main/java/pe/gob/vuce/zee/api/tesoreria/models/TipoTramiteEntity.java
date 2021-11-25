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

    @Column(name = "vepg_ttrm_cod_tipoca", nullable = false)
    private Integer tipoCalculo;

    @Column(name = "vepg_ttrm_cod_destin", nullable = false)
    private Integer codigoDestino;

    @Column(name = "vepg_ttrm_cod_moneda", nullable = false, length = 20)
    private Integer codigoMoneda;

    @Column(name = "vepg_ttrm_porcen_uit", length = 10, scale = 2)
    private BigDecimal porcentajeUIT;

    @Column(name = "vepg_ttrm_monto_pago",nullable = false, length = 10, scale = 2)
    private BigDecimal montoPago;

    @Column(name = "vepg_ttrm_cant_inici", length = 10, scale = 2)
    private BigDecimal cantidadInicial;

    @Column(name = "vepg_ttrm_cant_final", length = 10, scale = 2)
    private BigDecimal cantidadFinal;

    @Column(name = "vepg_ttrm_cod_modulo",nullable = false, length = 20)
    private Integer codigoModulo;

    @Column(name = "vepg_ttrm_cod_proces", nullable = false)
    private Integer codigoProceso;

    @Column(name = "vepg_ttrm_cod_formul", nullable = false)
    private Integer codigoFormulario;

    @Column(name = "vepg_ttrm_cod_accion", nullable = false)
    private Integer codigoAccion;

    @Column(name = "vepg_ttrm_preg_datoi",nullable = false, length = 100)
    private String preguntaDatoInformado;

    @Column(name = "vepg_tram_cliente_fk",nullable = false)
    private Integer clienteId;

    @Column(name = "vepg_tram_organiz_fk", nullable = false)
    private Integer organizacionId;

    @Column(name = "vepg_tram_cod_estado", nullable = false)
    private Integer estado;

    @Column(name = "vepg_tram_cod_active",nullable = false)
    private Integer activo;

    @Column(name = "vepg_tram_datecreate", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "vepg_tram_usr_create",nullable = false)
    private Integer usuarioCreacionId;

    @Column(name = "vepg_tram_dateupdate")
    private LocalDateTime fechaModificacion;

    @Column(name = "vepg_tram_usr_update")
    private Integer usuarioModificacionId;
}
