package pe.gob.vuce.zee.api.tesoreria.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.vuce.zee.api.tesoreria.dto.ConfiguradorOperacionDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ConfiguradorOperacionService {
    ConfiguradorOperacionDTO guardar(ConfiguradorOperacionDTO configuradorOperacionDTO);
    ConfiguradorOperacionDTO modificar(UUID id, ConfiguradorOperacionDTO configuradorOperacionDTO);
    Page<ConfiguradorOperacionDTO> busquedaPorFiltros(UUID id,Integer estado, Integer activo, Integer tramite, Integer operacion, LocalDateTime fechaCreacion, Pageable paginador);
    List<ConfiguradorOperacionDTO> busquedaPorFiltros(UUID id,Integer estado, Integer activo, Integer tramite, Integer operacion, LocalDateTime fechaCreacion, int offset, int size );
    List<ConfiguradorOperacionDTO> busquedaPorFiltros(UUID id,Integer estado, Integer activo, Integer tramite, Integer operacion,LocalDateTime fechaCreacion);
}
