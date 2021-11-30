package pe.gob.vuce.zee.api.tesoreria.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.vuce.zee.api.tesoreria.dto.ConfiguradorOperacionDTO;
import pe.gob.vuce.zee.api.tesoreria.dto.TipoCambioDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ConfiguradorOperacionService {
    ConfiguradorOperacionDTO guardar(ConfiguradorOperacionDTO configuradorOperacionDTO);
    Page<ConfiguradorOperacionDTO> busquedaPorFiltros(Integer estado, Integer activo, Integer tramite, Integer operacion, LocalDateTime fechaCreacion, Pageable paginador);
    List<ConfiguradorOperacionDTO> busquedaPorFiltros(Integer estado, Integer activo, Integer tramite, Integer operacion, LocalDateTime fechaCreacion, int offset, int size );
    List<ConfiguradorOperacionDTO> busquedaPorFiltros(Integer estado, Integer activo, Integer tramite, Integer operacion,LocalDateTime fechaCreacion);
}
