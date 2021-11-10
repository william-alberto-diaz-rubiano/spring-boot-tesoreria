package pe.gob.vuce.zee.api.maestros.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReniecContentDTO {
    private String apPrimer;
    private String apSegundo;
    private String direccion;
    private String estadoCivil;
    private String foto;
    private String prenombres;
    private String restriccion;
    private String ubigeo;
}
