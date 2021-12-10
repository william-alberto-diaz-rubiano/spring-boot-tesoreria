package pe.gob.vuce.zee.api.tesoreria.service;

import pe.gob.vuce.zee.api.tesoreria.dto.AccionPagoDTO;

import java.util.List;
import java.util.UUID;

public interface AccionPagoService {
    AccionPagoDTO guardar(AccionPagoDTO accionPagoDTO);
    AccionPagoDTO modificar(UUID id, AccionPagoDTO accionPagoDTO);
    AccionPagoDTO buscarId(UUID id);
    List<AccionPagoDTO> buscarPorTramitePago(UUID id);
}
