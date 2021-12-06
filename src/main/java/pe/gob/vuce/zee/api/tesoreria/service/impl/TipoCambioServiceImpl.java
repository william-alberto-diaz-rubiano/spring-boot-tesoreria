package pe.gob.vuce.zee.api.tesoreria.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.gob.vuce.zee.api.tesoreria.dto.ConfiguradorOperacionDTO;
import pe.gob.vuce.zee.api.tesoreria.dto.TipoCambioDTO;
import pe.gob.vuce.zee.api.tesoreria.models.TipoCambioEntity;
import pe.gob.vuce.zee.api.tesoreria.repository.TipoCambioRepository;
import pe.gob.vuce.zee.api.tesoreria.service.TipoCambioService;
import pe.gob.vuce.zee.api.tesoreria.base.Constantes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class TipoCambioServiceImpl implements TipoCambioService {
    @Autowired
    private TipoCambioRepository tipoCambioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<TipoCambioDTO> busquedaPorFiltros(UUID estado, Integer activo,BigDecimal cambioCompra,BigDecimal cambioVenta, LocalDateTime fechaInicio, LocalDateTime fechaFin){

        var result = tipoCambioRepository.busqueda(estado,activo,cambioCompra,cambioVenta,fechaInicio,fechaFin);

        return result.stream().map(x -> modelMapper.map(x, TipoCambioDTO.class)).collect(Collectors.toList());
    }

    @Override
    public TipoCambioDTO guardar(TipoCambioDTO tipoCambioDTO) {

        List<TipoCambioDTO> listaPorEstado= busquedaPorFiltros(UUID.fromString(Constantes.getSingleKeyFromValue(Constantes.ESTADOS_TIPO_CAMBIO,"ACTIVO")), null,null,null,null,null);

        System.out.println(listaPorEstado);

        for(TipoCambioDTO tipoCambioDTO1 : listaPorEstado){

            tipoCambioDTO1.setEstadoId(UUID.fromString(Constantes.getSingleKeyFromValue(Constantes.ESTADOS_TIPO_CAMBIO,"INACTIVO")));
            tipoCambioDTO1.setEstadoDescripcion(null);

            System.out.println();
            System.out.println(tipoCambioDTO1);
            TipoCambioEntity tipoCambioEntity1 = modelMapper.map(tipoCambioDTO1, TipoCambioEntity.class);

            System.out.println();
            System.out.println(tipoCambioEntity1);
            tipoCambioRepository.save(tipoCambioEntity1);
        }

        tipoCambioDTO.setActivo(Constantes.HABILITADO);
        tipoCambioDTO.setEstadoId(UUID.fromString(Constantes.getSingleKeyFromValue(Constantes.ESTADOS_TIPO_CAMBIO,"ACTIVO")));
        tipoCambioDTO.setClienteId(1);
        tipoCambioDTO.setOrganizacionId(1);
        tipoCambioDTO.setUsuarioCreacionId(UUID.randomUUID());
        tipoCambioDTO.setFechaCreacion(LocalDateTime.now());
        tipoCambioDTO.setFechaModificacion(LocalDateTime.now());
        tipoCambioDTO.setUsuarioModificacionId(UUID.randomUUID());

        TipoCambioEntity tipoCambioEntity = modelMapper.map(tipoCambioDTO, TipoCambioEntity.class);
        tipoCambioEntity = tipoCambioRepository.save(tipoCambioEntity);

        return modelMapper.map(tipoCambioEntity, TipoCambioDTO.class);
    }


    @Override
    public Page<TipoCambioDTO> busquedaPorFiltros(UUID estado, Integer activo, BigDecimal cambioCompra, BigDecimal cambioVenta, LocalDateTime fechaInicio, LocalDateTime fechaFin, Pageable paginador) {
        var result = tipoCambioRepository.busquedaPageable(estado,activo,cambioCompra,cambioVenta,fechaInicio,fechaFin,paginador);

        var resultDTO = result.stream().map(x -> modelMapper.map(x, TipoCambioDTO.class)).collect(Collectors.toList());

        return new PageImpl<>(resultDTO, paginador, result.getTotalElements());
    }

    @Override
    public List<TipoCambioDTO> busquedaPorFiltros(UUID estado, Integer activo, BigDecimal cambioCompra,BigDecimal cambioVenta,LocalDateTime fechaInicio, LocalDateTime fechaFin, int offset, int size) {
        return Collections.emptyList();
    }


}
