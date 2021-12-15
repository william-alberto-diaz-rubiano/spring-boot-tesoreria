package pe.gob.vuce.zee.api.tesoreria.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.vuce.zee.api.tesoreria.models.ConceptoPagoEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ConceptoPagoCustomRepository {
    List<ConceptoPagoEntity> busqueda(UUID id, UUID estado, Integer activo, UUID concepto, String nombreConcepto, LocalDateTime fechaInicio, LocalDateTime fechaFin);
    List<ConceptoPagoEntity> busqueda(UUID id, UUID estado, Integer activo, UUID concepto, String nombreConcepto, LocalDateTime fechaInicio, LocalDateTime fechaFin,int offset, int size);
    Page<ConceptoPagoEntity> busquedaPageable(UUID id, UUID estado, Integer activo, UUID concepto, String nombreConcepto, LocalDateTime fechaInicio, LocalDateTime fechaFin, Pageable pageable);
    Long contar(UUID id, UUID estado, Integer activo, UUID concepto, String nombreConcepto, LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
