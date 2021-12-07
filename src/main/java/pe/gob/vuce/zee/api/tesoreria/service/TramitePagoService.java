package pe.gob.vuce.zee.api.tesoreria.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.vuce.zee.api.tesoreria.dto.TramitePagoDTO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TramitePagoService {
    TramitePagoDTO guardar(TramitePagoDTO tramitePagoDTO);
    TramitePagoDTO modificar(UUID id, TramitePagoDTO tramitePagoDTO);
    Page<TramitePagoDTO> busquedaPorFiltros(UUID id,UUID estado, Integer activo, UUID tipoTramite,String nombreTramite, LocalDateTime fechaInicio, LocalDateTime fechaFin, Pageable paginador);
    List<TramitePagoDTO> busquedaPorFiltros(UUID id,UUID estado, Integer activo, UUID tipoTramite,String nombreTramite, LocalDateTime fechaInicio, LocalDateTime fechaFin, int offset, int size);
    List<TramitePagoDTO> busquedaPorFiltros(UUID id,UUID estado, Integer activo, UUID tipoTramite,String nombreTramite, LocalDateTime fechaInicio, LocalDateTime fechaFin);

}
