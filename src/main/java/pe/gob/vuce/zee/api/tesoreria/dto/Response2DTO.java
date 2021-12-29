package pe.gob.vuce.zee.api.tesoreria.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.gob.vuce.zee.api.tesoreria.base.Constantes;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response2DTO<T> {

    private int codigo;
    private String mensaje;
    private T data;

    public Response2DTO(T data){
        this.codigo = Constantes.NO_ERROR;
        this.data = data;
    }

    public Response2DTO(int codigo, T data) {
        this.codigo = codigo;
        this.data = data;
    }

    public Response2DTO(int codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }


}
