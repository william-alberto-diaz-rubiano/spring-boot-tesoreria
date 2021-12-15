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
@Table(name = "vems_comp",schema = "vuce_zee", catalog = "zee_db")
public class RegistroComprobanteEntity {

    @Id
    @Column(name = "vems_comp_idllave_pk",nullable = false)
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "vems_comp_cod_tipocb", referencedColumnName = "vems_gcon_idllave_pk")
    private MaestroEntity codigoComprobante;

    @Column(name = "vems_comp_codi_serie", nullable = false, length = 10)
    private String codigoSerie;

    @Column(name = "vems_comp_correlativ",nullable = false, length = 10)
    private String correlativoInicial;

    @Column(name = "vems_comp_correl_act",nullable = false, length = 10)
    private String correlativoAnual;

    @Column(name = "vems_comp_cliente_fk",nullable = false)
    private Integer clienteId;

    @Column(name = "vems_comp_organiz_fk", nullable = false)
    private Integer organizacionId;

    @ManyToOne
    @JoinColumn(name = "vems_comp_cod_estado", referencedColumnName = "vems_gcon_idllave_pk")
    private MaestroEntity estado;

    @Column(name = "vems_comp_cod_active",nullable = false)
    private Integer activo;

    @Column(name = "vems_comp_datecreate", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "vems_comp_usr_create",nullable = false)
    private UUID usuarioCreacionId;

    @Column(name = "vems_comp_dateupdate")
    private LocalDateTime fechaModificacion;

    @Column(name = "vems_comp_usr_update")
    private UUID usuarioModificacionId;
}
