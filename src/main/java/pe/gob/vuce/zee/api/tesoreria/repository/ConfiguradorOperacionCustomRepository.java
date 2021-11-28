package pe.gob.vuce.zee.api.tesoreria.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.vuce.zee.api.tesoreria.models.ConfiguradorOperacionEntity;

import java.util.List;

public interface ConfiguradorOperacionCustomRepository {
    List<ConfiguradorOperacionEntity> busqueda(Integer estado, Integer activo,Integer tramite,Integer operacion);
    List<ConfiguradorOperacionEntity> busqueda(Integer estado, Integer activo,Integer tramite,Integer operacion,int offset, int size);
    Page<ConfiguradorOperacionEntity> busquedaPageable(Integer estado, Integer activo,Integer tramite,Integer operacion, Pageable pageable);
    Long contar(Integer estado, Integer activo,Integer tramite,Integer operacion);
}
