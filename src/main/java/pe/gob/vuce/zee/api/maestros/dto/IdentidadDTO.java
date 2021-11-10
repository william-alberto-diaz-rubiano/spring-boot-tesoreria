package pe.gob.vuce.zee.api.maestros.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IdentidadDTO {
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombre;
    private String razonSocial;
    private String direccion;
    private String fotoBase64;
    private String ubigeo;
    private String estadoCivil;
}
