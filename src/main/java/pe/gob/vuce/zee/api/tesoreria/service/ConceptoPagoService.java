package pe.gob.vuce.zee.api.tesoreria.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.vuce.zee.api.tesoreria.dto.ConceptoPagoDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ConceptoPagoService {
    ConceptoPagoDTO guardar (ConceptoPagoDTO conceptoPagoDTO);
    String codigoSistema();
    ConceptoPagoDTO modificar(UUID id, ConceptoPagoDTO conceptoPagoDTO);
    Page<ConceptoPagoDTO> busquedaPorFiltros(UUID id, UUID estado, Integer activo, UUID concepto, String nombreConcepto, LocalDateTime fechaInicio, LocalDateTime fechaFin, Pageable paginador);
    List<ConceptoPagoDTO> busquedaPorFiltros(UUID id, UUID estado, Integer activo, UUID concepto, String nombreConcepto, LocalDateTime fechaInicio, LocalDateTime fechaFin, int offset, int size);
    List<ConceptoPagoDTO> busquedaPorFiltros(UUID id,UUID estado, Integer activo, UUID concepto,String nombreConcepto, LocalDateTime fechaInicio, LocalDateTime fechaFin);
    ConceptoPagoDTO modificarEstado(UUID id, UUID nuevoEstado);
}
