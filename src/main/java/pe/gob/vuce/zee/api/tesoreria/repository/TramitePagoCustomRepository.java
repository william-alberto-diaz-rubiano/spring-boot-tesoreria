package pe.gob.vuce.zee.api.tesoreria.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.vuce.zee.api.tesoreria.models.TramitePagoEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TramitePagoCustomRepository {
    List<TramitePagoEntity> busqueda(Integer estado, Integer activo, Integer tipoTramite,String nombreTramite, LocalDateTime fechaInicio, LocalDateTime fechaFin);
    List<TramitePagoEntity> busqueda(Integer estado, Integer activo, Integer tipoTramite,String nombreTramite, LocalDateTime fechaInicio, LocalDateTime fechaFin,int offset, int size);
    Page<TramitePagoEntity> busquedaPageable(Integer estado, Integer activo, Integer tipoTramite,String nombreTramite, LocalDateTime fechaInicio, LocalDateTime fechaFin, Pageable pageable);
    Long contar(Integer estado, Integer activo, Integer tipoTramite,String nombreTramite, LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
