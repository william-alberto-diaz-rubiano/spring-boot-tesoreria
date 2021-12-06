package pe.gob.vuce.zee.api.tesoreria.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.vuce.zee.api.tesoreria.models.TipoCambioEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TipoCambioCustomRepository {
    List<TipoCambioEntity> busqueda(UUID estado, Integer activo, BigDecimal cambioCompra,
                                    BigDecimal cambioVenta, LocalDateTime fechaInicio, LocalDateTime fechaFin);
    List<TipoCambioEntity> busqueda(UUID estado, Integer activo, BigDecimal cambioCompra,
                                    BigDecimal cambioVenta, LocalDateTime fechaInicio, LocalDateTime fechaFin,int offset, int size);
    Page<TipoCambioEntity> busquedaPageable(UUID estado, Integer activo, BigDecimal cambioCompra,
                                            BigDecimal cambioVenta, LocalDateTime fechaInicio, LocalDateTime fechaFin, Pageable pageable);
    Long contar(UUID estado, Integer activo, BigDecimal cambioCompra,BigDecimal cambioVenta, LocalDateTime fechaInicio, LocalDateTime fechaFin);

}
