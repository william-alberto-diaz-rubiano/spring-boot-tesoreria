package pe.gob.vuce.zee.api.maestros.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MaestroDTO {
    private UUID id;
    private Integer prefijo;
    private Integer correlativo;
    private String descripcion;
    private String abreviatura;
}
