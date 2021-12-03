package pe.gob.vuce.zee.api.tesoreria.service;

import pe.gob.vuce.zee.api.tesoreria.dto.TipoTramiteDTO;

import java.util.UUID;

public interface TipoTramiteService {
    TipoTramiteDTO guardar(TipoTramiteDTO tipoTramiteDTO);
    TipoTramiteDTO modificar(UUID id, TipoTramiteDTO tipoTramiteDTO);
}
