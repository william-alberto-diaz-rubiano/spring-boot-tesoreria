package pe.gob.vuce.zee.api.tesoreria.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "vepg_tram",schema = "vuce_zee", catalog = "zee_db")
public class TramitePagoEntity {

    @Id
    @Column(name = "vepg_tram_idllave_pk",nullable = false)
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vepg_tram_id_copr_fk", referencedColumnName = "vems_copr_idllave_pk", nullable = false)
    private ConfiguradorOperacionEntity configuradorOperacion;

    @Column(name = "vepg_tram_cod_sistem",nullable = false, length = 20)
    private String codigoSistema;

    @Column(name = "vepg_tram_cod_proces", nullable = false)
    private Integer codigoProceso;

    @Column(name = "vepg_tram_flag_destn", nullable = false)
    private boolean flagDestinos;

    @Column(name = "vepg_tram_codig_pago", nullable = false, length = 20)
    private String codigoPago;

    @Column(name = "vepg_tram_nombre_pag",nullable = false, length = 50)
    private String nombrePago;

    @Column(name = "vepg_tram_base_legal",nullable = false, length = 50)
    private String baseLegal;

    @Column(name = "vepg_tram_dias_plazo",nullable = false)
    private Integer diazPlazo;

    @Column(name = "vepg_tram_id_pacc_fk", nullable = false)
    private Integer tipoAccionPago;

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
