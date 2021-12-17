package pe.gob.vuce.zee.api.tesoreria.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.gob.vuce.zee.api.tesoreria.dto.TipoCambioDTO;
import pe.gob.vuce.zee.api.tesoreria.models.TipoCambioEntity;
import pe.gob.vuce.zee.api.tesoreria.repository.MaestroRepository;
import pe.gob.vuce.zee.api.tesoreria.repository.TipoCambioRepository;
import pe.gob.vuce.zee.api.tesoreria.service.TipoCambioService;
import pe.gob.vuce.zee.api.tesoreria.base.Constantes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class TipoCambioServiceImpl implements TipoCambioService {
    @Autowired
    private TipoCambioRepository tipoCambioRepository;

    @Autowired
    private MaestroRepository maestroRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<TipoCambioDTO> busquedaPorFiltros(UUID estado, Integer activo,BigDecimal cambioCompra,BigDecimal cambioVenta, LocalDateTime fechaInicio, LocalDateTime fechaFin){


        if(fechaInicio != null && fechaFin != null){

            LocalTime horaInicio = LocalTime.of(00,00,00);
            LocalTime horaFin = LocalTime.of(23,59,59);

            LocalDate fechaInicioDate = fechaInicio.toLocalDate();
            LocalDate fechaFinDate = fechaFin.toLocalDate();

            fechaInicio = LocalDateTime.of(fechaInicioDate,horaInicio);
            fechaFin = LocalDateTime.of(fechaFinDate,horaFin);
        }


        var result = tipoCambioRepository.busqueda(estado,activo,cambioCompra,cambioVenta,fechaInicio,fechaFin);

        return result.stream().map(x -> modelMapper.map(x, TipoCambioDTO.class)).collect(Collectors.toList());
    }

    @Override
    public TipoCambioDTO guardar(TipoCambioDTO tipoCambioDTO) {

        UUID estadoActivo=maestroRepository.findByPrefijoAndCorrelativo(30,1).getId();
        UUID estadoInactivo=maestroRepository.findByPrefijoAndCorrelativo(30,2).getId();

        List<TipoCambioDTO> listaPorEstado= busquedaPorFiltros(estadoActivo, null,null,null,null,null);

        for(TipoCambioDTO tipoCambioDTO1 : listaPorEstado){
            tipoCambioDTO1.setEstadoId(estadoInactivo);
            tipoCambioDTO1.setEstadoDescripcion(null);

            TipoCambioEntity tipoCambioEntity1 = modelMapper.map(tipoCambioDTO1, TipoCambioEntity.class);

            tipoCambioRepository.save(tipoCambioEntity1);
        }

        tipoCambioDTO.setActivo(Constantes.HABILITADO);
        tipoCambioDTO.setEstadoId(estadoActivo);
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

        if(fechaInicio != null && fechaFin != null){

            LocalTime horaInicio = LocalTime.of(00,00,00);
            LocalTime horaFin = LocalTime.of(23,59,59);

            LocalDate fechaInicioDate = fechaInicio.toLocalDate();
            LocalDate fechaFinDate = fechaFin.toLocalDate();

            fechaInicio = LocalDateTime.of(fechaInicioDate,horaInicio);
            fechaFin = LocalDateTime.of(fechaFinDate,horaFin);
        }

        var result = tipoCambioRepository.busquedaPageable(estado,activo,cambioCompra,cambioVenta,fechaInicio,fechaFin,paginador);

        var resultDTO = result.stream().map(x -> modelMapper.map(x, TipoCambioDTO.class)).collect(Collectors.toList());

        return new PageImpl<>(resultDTO, paginador, result.getTotalElements());
    }

    @Override
    public List<TipoCambioDTO> busquedaPorFiltros(UUID estado, Integer activo, BigDecimal cambioCompra,BigDecimal cambioVenta,LocalDateTime fechaInicio, LocalDateTime fechaFin, int offset, int size) {
        return Collections.emptyList();
    }


}
