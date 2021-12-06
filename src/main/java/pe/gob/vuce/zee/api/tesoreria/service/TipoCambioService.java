package pe.gob.vuce.zee.api.tesoreria.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.gob.vuce.zee.api.tesoreria.dto.TipoCambioDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public interface TipoCambioService {
    TipoCambioDTO guardar(TipoCambioDTO tipoCambioDTO);

    Page<TipoCambioDTO> busquedaPorFiltros(UUID estado, Integer activo, BigDecimal cambioCompra, BigDecimal cambioVenta, LocalDateTime fechaInicio, LocalDateTime fechaFin, Pageable paginador);

    List<TipoCambioDTO> busquedaPorFiltros(UUID estado, Integer activo, BigDecimal cambioCompra, BigDecimal cambioVenta, LocalDateTime fechaInicio, LocalDateTime fechaFin, int offset, int size);

    List<TipoCambioDTO> busquedaPorFiltros(UUID estado, Integer activo, BigDecimal cambioCompra, BigDecimal cambioVenta, LocalDateTime fechaInicio, LocalDateTime fechaFin);
}