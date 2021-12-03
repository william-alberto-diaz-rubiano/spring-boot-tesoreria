package pe.gob.vuce.zee.api.tesoreria.service;

import pe.gob.vuce.zee.api.tesoreria.dto.AccionPagoDTO;

import java.util.UUID;

public interface AccionPagoService {
    AccionPagoDTO guardar(AccionPagoDTO accionPagoDTO);
    AccionPagoDTO modificar(UUID id, AccionPagoDTO accionPagoDTO);
}
