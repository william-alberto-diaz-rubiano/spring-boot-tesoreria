package pe.gob.vuce.zee.api.tesoreria.models;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "vepg_acci",schema = "vuce_zee", catalog = "zee_db")
public class AccionPagoEntity {
    @Id
    @Column(name = "vepg_acci_idllave_pk",nullable = false)
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vepg_acci_id_tram_fk", referencedColumnName = "vepg_tram_idllave_pk", nullable = false)
    private TramitePagoEntity  tramitePago;

    @ManyToOne
    @JoinColumn(name = "vepg_acci_cod_modulo", referencedColumnName = "vems_gcon_idllave_pk")
    private MaestroEntity codigoModulo;

    @ManyToOne
    @JoinColumn(name = "vepg_acci_cod_proces", referencedColumnName = "vems_gcon_idllave_pk")
    private MaestroEntity codigoProceso;

    @ManyToOne
    @JoinColumn(name = "vepg_acci_cod_formul", referencedColumnName = "vems_gcon_idllave_pk")
    private MaestroEntity codigoFormulario;

    @ManyToOne
    @JoinColumn(name = "vepg_acci_cod_accion", referencedColumnName = "vems_gcon_idllave_pk")
    private MaestroEntity codigoAccion;

    @Column(name = "vepg_acci_cliente_fk",nullable = false)
    private Integer clienteId;

    @Column(name = "vepg_acci_organiz_fk", nullable = false)
    private Integer organizacionId;

    @ManyToOne
    @JoinColumn(name = "vepg_acci_cod_estado", referencedColumnName = "vems_gcon_idllave_pk")
    private MaestroEntity estado;

    @Column(name = "vepg_acci_cod_active",nullable = false)
    private Integer activo;

    @Column(name = "vepg_acci_datecreate", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "vepg_acci_usr_create",nullable = false)
    private UUID usuarioCreacionId;

    @Column(name = "vepg_acci_dateupdate")
    private LocalDateTime fechaModificacion;

    @Column(name = "vepg_acci_usr_update")
    private UUID usuarioModificacionId;

}
