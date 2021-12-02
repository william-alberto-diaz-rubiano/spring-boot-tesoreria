package pe.gob.vuce.zee.api.tesoreria.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.vuce.zee.api.tesoreria.models.ConfiguradorOperacionEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ConfiguradorOperacionCustomRepository {
    List<ConfiguradorOperacionEntity> busqueda(UUID id,Integer estado, Integer activo, Integer tramite, Integer operacion, LocalDateTime fechaCreacion);
    List<ConfiguradorOperacionEntity> busqueda(UUID id,Integer estado, Integer activo, Integer tramite, Integer operacion, LocalDateTime fechaCreacion,int offset, int size);
    Page<ConfiguradorOperacionEntity> busquedaPageable(UUID id,Integer estado, Integer activo, Integer tramite, Integer operacion, LocalDateTime fechaCreacion, Pageable pageable);
    Long contar(UUID id,Integer estado, Integer activo, Integer tramite, Integer operacion, LocalDateTime fechaCreacion);
}
