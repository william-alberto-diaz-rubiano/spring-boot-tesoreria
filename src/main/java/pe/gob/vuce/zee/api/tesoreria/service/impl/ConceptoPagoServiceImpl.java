package pe.gob.vuce.zee.api.tesoreria.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pe.gob.vuce.zee.api.tesoreria.base.Constantes;
import pe.gob.vuce.zee.api.tesoreria.dto.ConceptoPagoDTO;
import pe.gob.vuce.zee.api.tesoreria.exceptions.BadRequestException;
import pe.gob.vuce.zee.api.tesoreria.exceptions.EntityNotFoundException;
import pe.gob.vuce.zee.api.tesoreria.models.ConceptoPagoEntity;
import pe.gob.vuce.zee.api.tesoreria.repository.ConceptosPagoRepository;
import pe.gob.vuce.zee.api.tesoreria.service.ConceptoPagoService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ConceptoPagoServiceImpl implements ConceptoPagoService {

    @Autowired
    private ConceptosPagoRepository conceptosPagoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ConceptoPagoDTO guardar(ConceptoPagoDTO conceptoPagoDTO) {

        conceptoPagoDTO.setCodigoCriterioDescripcion(null);
        conceptoPagoDTO.setCodigoMonedaDescripcion(null);
        conceptoPagoDTO.setEstadoDescripcion(null);
        conceptoPagoDTO.setConfiguradorOperacionTramiteDescripcion(null);
        conceptoPagoDTO.setCodigoOperacionDescripcion(null);
        conceptoPagoDTO.setActivo(Constantes.HABILITADO);
        conceptoPagoDTO.setEstadoId(UUID.fromString(Constantes.getSingleKeyFromValue(Constantes.ESTADOS_CONCEPTOS_PAGO,"ACTIVO")));
        conceptoPagoDTO.setClienteId(1);
        conceptoPagoDTO.setOrganizacionId(1);
        conceptoPagoDTO.setUsuarioCreacionId(UUID.randomUUID());
        conceptoPagoDTO.setFechaCreacion(LocalDateTime.now());
        conceptoPagoDTO.setFechaModificacion(LocalDateTime.now());
        conceptoPagoDTO.setUsuarioModificacionId(UUID.randomUUID());

        ConceptoPagoEntity conceptoPagoEntity = modelMapper.map(conceptoPagoDTO, ConceptoPagoEntity.class);
        conceptoPagoEntity = conceptosPagoRepository.save(conceptoPagoEntity);

        return modelMapper.map(conceptoPagoEntity, ConceptoPagoDTO.class);
    }

    @Override
    public String codigoSistema() {

        Integer codigoMayor;
        Integer codigoNumero;

        List<ConceptoPagoDTO> listadoConceptoPago = busquedaPorFiltros(null,null, null, null, null, null, null);

        List<Integer> listadoCodigos = new ArrayList<>();

        for(ConceptoPagoDTO conceptoPagoDTO : listadoConceptoPago){
            String cod = conceptoPagoDTO.getCodigoSistema();
            String cadenaNumerica = cod.substring(6);
            Integer codigoInteger = Integer.parseInt(cadenaNumerica);
            listadoCodigos.add(codigoInteger);
        }

        if(listadoCodigos.isEmpty()){
            codigoMayor = 1;
            codigoNumero = codigoMayor;
        }else{
            codigoMayor= Collections.max(listadoCodigos);
            codigoNumero = codigoMayor + 1;
        }


        String codigoFormateado=String.format("%05d", codigoNumero);

        return "CONCP" +"-" + codigoFormateado;
    }

    @Override
    public ConceptoPagoDTO modificar(UUID id, ConceptoPagoDTO conceptoPagoDTO) {

        ConceptoPagoEntity conceptoPagoEntity = null;

        List<ConceptoPagoDTO> listaPorIdConcepto = busquedaPorFiltros(id,null, null, null, null, null, null);

        if (listaPorIdConcepto.isEmpty()) {
            throw new EntityNotFoundException("El concepto de pago ingresado no existe");
        } else {
            for (ConceptoPagoDTO conceptoPagoDTO1 : listaPorIdConcepto) {

                if (conceptoPagoDTO1.getEstadoDescripcion().equals("Activo")) {

                    conceptoPagoDTO1.setCodigoCriterioDescripcion(null);
                    conceptoPagoDTO1.setCodigoMonedaDescripcion(null);
                    conceptoPagoDTO1.setEstadoDescripcion(null);
                    conceptoPagoDTO1.setConfiguradorOperacionTramiteDescripcion(null);
                    conceptoPagoDTO1.setCodigoOperacionDescripcion(null);
                    conceptoPagoDTO1.setCodigoOperacionId(conceptoPagoDTO.getCodigoOperacionId());
                    conceptoPagoDTO1.setCodigoGenerado(conceptoPagoDTO.getCodigoGenerado());
                    conceptoPagoDTO1.setDiazPlazo(conceptoPagoDTO.getDiazPlazo());
                    conceptoPagoDTO1.setCodigoCriterioId(conceptoPagoDTO.getCodigoCriterioId());
                    conceptoPagoDTO1.setCodigoMonedaId(conceptoPagoDTO.getCodigoMonedaId());
                    conceptoPagoDTO1.setPorcentajeUIT(conceptoPagoDTO.getPorcentajeUIT());
                    conceptoPagoDTO1.setMontoPago(conceptoPagoDTO.getMontoPago());
                    conceptoPagoDTO1.setNombreConcepto(conceptoPagoDTO.getNombreConcepto());
                    conceptoPagoDTO1.setFechaModificacion(LocalDateTime.now());
                    conceptoPagoDTO1.setUsuarioModificacionId(UUID.randomUUID());

                    conceptoPagoEntity = modelMapper.map(conceptoPagoDTO1, ConceptoPagoEntity.class);
                    conceptoPagoEntity = conceptosPagoRepository.save(conceptoPagoEntity);
                } else {
                    throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST, "No se puede realizar la modificacion del concepto de pago, se encuentra en estado Inactivo");
                }
            }

            return modelMapper.map(conceptoPagoEntity, ConceptoPagoDTO.class);
        }
    }

    @Override
    public Page<ConceptoPagoDTO> busquedaPorFiltros(UUID id, UUID estado, Integer activo, UUID concepto, String nombreConcepto, LocalDateTime fechaInicio, LocalDateTime fechaFin, Pageable paginador) {

        if(fechaInicio != null && fechaFin != null){

            LocalTime horaInicio = LocalTime.of(00,00,10);
            LocalTime horaFin = LocalTime.of(23,59,59);

            LocalDate fechaInicioDate = fechaInicio.toLocalDate();
            LocalDate fechaFinDate = fechaFin.toLocalDate();

            fechaInicio = LocalDateTime.of(fechaInicioDate,horaInicio);
            fechaFin = LocalDateTime.of(fechaFinDate,horaFin);
        }

        var result = conceptosPagoRepository.busquedaPageable(id,estado, activo, concepto, nombreConcepto, fechaInicio, fechaFin, paginador);
        var resultDTO = result.stream().map(x -> modelMapper.map(x, ConceptoPagoDTO.class)).collect(Collectors.toList());
        return new PageImpl<>(resultDTO, paginador, result.getTotalElements());
    }

    @Override
    public List<ConceptoPagoDTO> busquedaPorFiltros(UUID id, UUID estado, Integer activo, UUID concepto, String nombreConcepto, LocalDateTime fechaInicio, LocalDateTime fechaFin) {

        if(fechaInicio != null && fechaFin != null){

            LocalTime horaInicio = LocalTime.of(00,00,00);
            LocalTime horaFin = LocalTime.of(23,59,59);

            LocalDate fechaInicioDate = fechaInicio.toLocalDate();
            LocalDate fechaFinDate = fechaFin.toLocalDate();

            fechaInicio = LocalDateTime.of(fechaInicioDate,horaInicio);
            fechaFin = LocalDateTime.of(fechaFinDate,horaFin);
        }
        var result = conceptosPagoRepository.busqueda(id,estado,activo,concepto,nombreConcepto,fechaInicio,fechaFin);
        return result.stream().map(x -> modelMapper.map(x, ConceptoPagoDTO.class)).collect(Collectors.toList());
    }

    @Override
    public ConceptoPagoDTO modificarEstado(UUID id, UUID nuevoEstado) {

        List<ConceptoPagoDTO> listadoConceptoPago = busquedaPorFiltros(id,null, null, null, null, null, null);

        if (listadoConceptoPago.isEmpty()) {
            throw new EntityNotFoundException("El concepto pago ingresado no existe");
        }
        ConceptoPagoDTO conceptoPagoDTO = listadoConceptoPago.get(0);
        UUID estadoGuardado = conceptoPagoDTO.getEstadoId();

        if(estadoGuardado.equals(nuevoEstado)){
            throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST, "No se puede realizar el cambio de estado, el estado actual y el estado ingresado son el mismo");
        }

        conceptoPagoDTO.setEstadoId(nuevoEstado);
        conceptoPagoDTO.setConfiguradorOperacionTramiteDescripcion(null);
        conceptoPagoDTO.setEstadoDescripcion(null);
        conceptoPagoDTO.setCodigoCriterioDescripcion(null);
        conceptoPagoDTO.setCodigoMonedaDescripcion(null);
        conceptoPagoDTO.setCodigoOperacionDescripcion(null);


        ConceptoPagoEntity conceptoPagoEntity = modelMapper.map(conceptoPagoDTO, ConceptoPagoEntity.class);
        conceptoPagoEntity = conceptosPagoRepository.save(conceptoPagoEntity);

        return modelMapper.map(conceptoPagoEntity, ConceptoPagoDTO.class);
    }

    @Override
    public List<ConceptoPagoDTO> busquedaPorFiltros(UUID id, UUID estado, Integer activo, UUID concepto, String nombreConcepto, LocalDateTime fechaInicio, LocalDateTime fechaFin, int offset, int size) {
        return null;
    }

}
