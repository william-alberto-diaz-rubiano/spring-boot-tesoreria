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
@Table(name = "vems_copr",schema = "vuce_zee", catalog = "zee_db")
public class ConfiguradorOperacionEntity {

    @Id
    @Column(name = "vems_copr_idllave_pk",nullable = false)
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "vems_copr_cod_tipotr",referencedColumnName = "vems_gcon_idllave_pk")
    private MaestroEntity tramite;

    @ManyToOne
    @JoinColumn(name = "vems_copr_cod_tipoop", referencedColumnName = "vems_gcon_idllave_pk")
    private MaestroEntity operacion;

    @Column(name = "vems_copr_cliente_fk", nullable = false)
    private Integer clienteId;

    @Column(name = "vems_copr_organiz_fk", nullable = false)
    private Integer organizacionId;

    @ManyToOne
    @JoinColumn(name = "vems_copr_cod_estado", referencedColumnName = "vems_gcon_idllave_pk")
    private MaestroEntity estado;

    @Column(name = "vems_copr_cod_active",nullable = false)
    private Integer activo;

    @Column(name = "vems_copr_datecreate", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "vems_copr_usr_create",nullable = false)
    private UUID usuarioCreacionId;

    @Column(name = "vems_copr_dateupdate")
    private LocalDateTime fechaModificacion;

    @Column(name = "vems_copr_usr_update")
    private UUID usuarioModificacionId;
}
