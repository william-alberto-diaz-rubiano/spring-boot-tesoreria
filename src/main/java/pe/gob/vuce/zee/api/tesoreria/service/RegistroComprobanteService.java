package pe.gob.vuce.zee.api.tesoreria.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.vuce.zee.api.tesoreria.dto.RegistroComprobanteDTO;

import java.util.List;
import java.util.UUID;

public interface RegistroComprobanteService {
    RegistroComprobanteDTO guardar (RegistroComprobanteDTO registroComprobanteDTO);
    RegistroComprobanteDTO modificar(UUID id, RegistroComprobanteDTO registroComprobanteDTO);
    Page<RegistroComprobanteDTO> busquedaPorFiltros(UUID id, UUID estado, Integer activo, String serie, UUID comprobante, Pageable paginador);
    List<RegistroComprobanteDTO> busquedaPorFiltros(UUID id, UUID estado, Integer activo, String serie, UUID comprobante, int offset, int size);
    List<RegistroComprobanteDTO> busquedaPorFiltros(UUID id, UUID estado, Integer activo, String serie, UUID comprobante);
    RegistroComprobanteDTO modificarEstado(UUID id, UUID nuevoEstado);
}
