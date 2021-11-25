package pe.gob.vuce.zee.api.tesoreria.dto;

import lombok.Getter;
import lombok.Setter;
import okhttp3.internal.http2.Http2Stream;
import org.springframework.http.HttpStatus;
import pe.gob.vuce.zee.api.tesoreria.base.FechasUtil;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ResponseDTO {
    private Object data; //Devuelve el Object
    private String message;//Mensaje
    private List<?> list;//Retorna la lista siempre y cuando sea Success

    private String status; // success, info, error
    private Date timestamp;  //La fecha y hora de consulta o transaccion
    private UUID id; //Id del objeto persistido cuando la transaccion es success

    public ResponseDTO() {
        super();
    }

    public ResponseDTO(Object data, String message, String status, UUID id) {
        this.data = data;
        this.message = message;
        this.status = status;
        this.id = id;
    }


    public ResponseDTO(String status, Object data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public ResponseDTO(String status, List<?> list, String message) {
        this.status = status;
        this.list = list;
        this.message = message;
    }

    public ResponseDTO(String status, String message, UUID id) {
        this.status = status;
        this.message = message;
        this.id = id;
    }

    public ResponseDTO(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp != null ? timestamp : FechasUtil.getToFullDay();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
