package pe.gob.vuce.zee.api.maestros.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vems_gcon", schema = "vuce_zee", catalog = "zee_db")
public class MaestroEntity {
    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "vems_gcon_idllave_pk", nullable = false)
    private UUID id;

    @Column(name = "vems_gcon_numprefijo", nullable = false)
    private Integer prefijo;

    @Column(name = "vems_gcon_correlativ", nullable = false)
    private Integer correlativo;

    @Column(name = "vems_gcon_descripcin", nullable = false, length = 50)
    private String descripcion;

    @Column(name = "vems_gcon_abreviatur", length = 10)
    private String abreviatura;

    @Column(name = "vems_gcon_cliente_fk", nullable = false)
    private Integer clienteId;

    @Column(name = "vems_gcon_organiz_fk", nullable = false)
    private Integer organizacionId;

    @Column(name = "vems_gcon_cod_estado", nullable = false)
    private Integer estado;

    @Column(name = "vems_gcon_cod_active", nullable = false)
    private Integer activo;

    @Column(name = "vems_gcon_datecreate")
    private LocalDateTime fechaCreacion;

    @Column(name = "vems_gcon_usr_create", nullable = false)
    private Integer uidCreacion;

    @Column(name = "vems_gcon_dateupdate")
    private LocalDateTime fechaModificacion;

    @Column(name = "vems_gcon_usr_update", nullable = false)
    private Integer uidModificacion;

}
