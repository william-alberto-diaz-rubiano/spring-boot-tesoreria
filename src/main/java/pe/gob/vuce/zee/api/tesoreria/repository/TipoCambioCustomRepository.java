package pe.gob.vuce.zee.api.tesoreria.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.vuce.zee.api.tesoreria.models.TipoCambioEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface TipoCambioCustomRepository {
    List<TipoCambioEntity> busqueda(Integer estado, Integer activo, LocalDateTime fechaInicio, LocalDateTime fechaFin);
    List<TipoCambioEntity> busqueda(Integer estado, Integer activo, LocalDateTime fechaInicio, LocalDateTime fechaFin,int offset, int size);
    Page<TipoCambioEntity> busquedaPageable(Integer estado, Integer activo, LocalDateTime fechaInicio, LocalDateTime fechaFin, Pageable pageable);
    Long contar(Integer estado, Integer activo, LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
