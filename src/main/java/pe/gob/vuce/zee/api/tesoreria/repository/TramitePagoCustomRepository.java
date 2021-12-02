package pe.gob.vuce.zee.api.tesoreria.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.vuce.zee.api.tesoreria.models.TramitePagoEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TramitePagoCustomRepository {
    List<TramitePagoEntity> busqueda(UUID id,Integer estado, Integer activo, Integer tipoTramite, String nombreTramite, LocalDateTime fechaInicio, LocalDateTime fechaFin);
    List<TramitePagoEntity> busqueda(UUID id,Integer estado, Integer activo, Integer tipoTramite,String nombreTramite, LocalDateTime fechaInicio, LocalDateTime fechaFin,int offset, int size);
    Page<TramitePagoEntity> busquedaPageable(UUID id,Integer estado, Integer activo, Integer tipoTramite,String nombreTramite, LocalDateTime fechaInicio, LocalDateTime fechaFin, Pageable pageable);
    Long contar(UUID id,Integer estado, Integer activo, Integer tipoTramite,String nombreTramite, LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
