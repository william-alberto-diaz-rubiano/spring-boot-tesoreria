package pe.gob.vuce.zee.api.tesoreria.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoTramiteListarDTO {

    private UUID tipoCalculoId;
    private String tipoCalculoDescripcion;

    private UUID codigoModuloId;
    private String codigoModuloDescripcion;

    private UUID codigoProcesoId;
    private String codigoProcesoDescripcion;

    private UUID codigoFormularioId;
    private String codigoFormularioDescripcion;

    private UUID codigoAccionId;
    private String codigoAccionDescripcion;

    private String preguntaDatoInformado;

    private List<TipoTramiteDTO> listadoTipoTramite;
}
