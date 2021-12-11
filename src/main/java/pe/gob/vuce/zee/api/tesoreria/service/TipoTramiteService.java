package pe.gob.vuce.zee.api.tesoreria.service;

import pe.gob.vuce.zee.api.tesoreria.dto.TipoTramiteDTO;

import java.util.List;
import java.util.UUID;

public interface TipoTramiteService {
    TipoTramiteDTO guardar(TipoTramiteDTO tipoTramiteDTO);
    TipoTramiteDTO modificar(UUID id, TipoTramiteDTO tipoTramiteDTO);
    TipoTramiteDTO buscarId(UUID id);
    List<TipoTramiteDTO> buscarTramitePago(UUID id);
    List<TipoTramiteDTO> guardarAll (List<TipoTramiteDTO> listaObjetos);
    List<TipoTramiteDTO> modificarAll (List<TipoTramiteDTO> listaObjetos);
}
