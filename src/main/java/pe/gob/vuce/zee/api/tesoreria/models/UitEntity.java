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
@Table(name = "vepg_vuit",schema = "vuce_zee", catalog = "zee_db")
public class UitEntity {

    @Id
    @Column(name = "vepg_vuit_idllave_pk",nullable = false)
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;
    @Column(name = "vepg_vuit_anio_activ",nullable = false)
    private String anioUit;
    @Column(name = "vepg_vuit_valor_sols",nullable = false)
    private Double valorSolesUit;
    @Column(name = "vepg_vuit_cliente_fk",nullable = false)
    private Integer clienteId;
    @Column(name = "vepg_vuit_organiz_fk", nullable = false)
    private Integer organizacionId;
    @Column(name = "vepg_vuit_cod_estado", nullable = false)
    private Integer estado;
    @Column(name = "vepg_vuit_cod_active",nullable = false)
    private Integer activo;
    @Column(name = "vepg_vuit_datecreate", nullable = false)
    private LocalDateTime fechaCreacion;
    @Column(name = "vepg_vuit_usr_create",nullable = false)
    private UUID usuarioCreacionId;
    @Column(name = "vepg_vuit_dateupdate")
    private LocalDateTime fechaModificacion;
    @Column(name = "vepg_vuit_usr_update")
    private UUID usuarioModificacionId;

}
