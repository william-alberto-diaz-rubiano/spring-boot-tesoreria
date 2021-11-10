package pe.gob.vuce.zee.api.maestros.service;

import pe.gob.vuce.zee.api.maestros.dto.IdentidadDTO;

import java.io.IOException;

public interface IdentidadService {
    IdentidadDTO consultarDni(String numeroDocumento) throws IOException;
    IdentidadDTO consultarRuc(String numeroDocumento);
    IdentidadDTO consultarCe(String numeroDocumento);
}
