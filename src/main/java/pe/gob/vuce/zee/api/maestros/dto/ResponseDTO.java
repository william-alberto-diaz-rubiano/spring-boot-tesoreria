package pe.gob.vuce.zee.api.maestros.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO<T> {
    private int codigo;
    private String mensaje;
    private T data;

    public ResponseDTO(int codigo, T data) {
        this.codigo = codigo;
        this.data = data;
    }

    public ResponseDTO(int codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }
}
