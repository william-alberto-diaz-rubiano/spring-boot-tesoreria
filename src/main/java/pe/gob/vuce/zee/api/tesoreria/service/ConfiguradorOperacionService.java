package pe.gob.vuce.zee.api.tesoreria.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.vuce.zee.api.tesoreria.dto.ConfiguradorOperacionDTO;

import java.util.List;
import java.util.UUID;

public interface ConfiguradorOperacionService {
    ConfiguradorOperacionDTO guardar(ConfiguradorOperacionDTO configuradorOperacionDTO);
    ConfiguradorOperacionDTO modificar(UUID id, ConfiguradorOperacionDTO configuradorOperacionDTO);
    Page<ConfiguradorOperacionDTO> busquedaPorFiltros(UUID id,UUID estado, Integer activo, UUID tramite, UUID operacion, Pageable paginador);
    List<ConfiguradorOperacionDTO> busquedaPorFiltros(UUID id,UUID estado, Integer activo, UUID tramite, UUID operacion, int offset, int size );
    List<ConfiguradorOperacionDTO> busquedaPorFiltros(UUID id,UUID estado, Integer activo, UUID tramite, UUID operacion);
}
