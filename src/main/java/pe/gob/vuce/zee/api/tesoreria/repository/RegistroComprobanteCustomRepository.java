package pe.gob.vuce.zee.api.tesoreria.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.vuce.zee.api.tesoreria.models.RegistroComprobanteEntity;

import java.util.List;
import java.util.UUID;

public interface RegistroComprobanteCustomRepository {
    List<RegistroComprobanteEntity> busqueda(UUID id, UUID estado, Integer activo, String serie, UUID comprobante);
    List<RegistroComprobanteEntity> busqueda(UUID id, UUID estado, Integer activo,String serie, UUID comprobante,int offset, int size);
    Page<RegistroComprobanteEntity> busquedaPageable(UUID id, UUID estado, Integer activo,String serie, UUID comprobante, Pageable pageable);
    Long contar(UUID id, UUID estado, Integer activo,String serie, UUID comprobante);
}
