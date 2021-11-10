package pe.gob.vuce.zee.api.maestros.dto;

import lombok.Data;

@Data
public class SunatResponseDTO {
    private String ruc;
    private Boolean esActivo;
    private Boolean esHabido;
    private String razonSocial;
    private String domicioLegal;
    private String representanteLegal;
    private String correo;
    private String telefono;
}
