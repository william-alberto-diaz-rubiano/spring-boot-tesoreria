package pe.gob.vuce.zee.api.tesoreria.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.vuce.zee.api.tesoreria.models.ConfiguradorOperacionEntity;
import java.util.List;
import java.util.UUID;

public interface ConfiguradorOperacionCustomRepository {
    List<ConfiguradorOperacionEntity> busqueda(UUID id,UUID estado, Integer activo, UUID tramite, UUID operacion);
    List<ConfiguradorOperacionEntity> busqueda(UUID id,UUID estado, Integer activo, UUID tramite, UUID operacion,int offset, int size);
    Page<ConfiguradorOperacionEntity> busquedaPageable(UUID id,UUID estado, Integer activo, UUID tramite, UUID operacion, Pageable pageable);
    Long contar(UUID id,UUID estado, Integer activo, UUID tramite, UUID operacion);
}
