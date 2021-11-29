package pe.gob.vuce.zee.api.tesoreria.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ErrorDTO {
   private Integer statusValue;
   private String code;
   private String message;
   private String path;
   private List<String> errors;
}
