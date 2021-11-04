package pe.gob.vuce.zee.api.maestros.service;

import pe.gob.vuce.zee.api.maestros.dto.MaestroDTO;

import java.util.Set;

public interface MaestroService {
    Set<MaestroDTO> buscarPorPrefijo(Integer prefijo);
    MaestroDTO buscarPorPrefijoYCorrelativo(Integer prefijo, Integer correlativo);
}
