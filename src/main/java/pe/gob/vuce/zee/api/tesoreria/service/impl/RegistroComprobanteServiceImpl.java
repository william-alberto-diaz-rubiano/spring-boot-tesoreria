package pe.gob.vuce.zee.api.tesoreria.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pe.gob.vuce.zee.api.tesoreria.base.Constantes;
import pe.gob.vuce.zee.api.tesoreria.dto.RegistroComprobanteDTO;
import pe.gob.vuce.zee.api.tesoreria.exceptions.BadRequestException;
import pe.gob.vuce.zee.api.tesoreria.exceptions.EntityNotFoundException;
import pe.gob.vuce.zee.api.tesoreria.models.RegistroComprobanteEntity;
import pe.gob.vuce.zee.api.tesoreria.repository.RegistroComprobanteRepository;
import pe.gob.vuce.zee.api.tesoreria.service.RegistroComprobanteService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RegistroComprobanteServiceImpl implements RegistroComprobanteService {

    @Autowired
    public RegistroComprobanteRepository registroComprobanteRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RegistroComprobanteDTO guardar(RegistroComprobanteDTO registroComprobanteDTO) {

        List<RegistroComprobanteDTO> litaSerieAsociada= busquedaPorFiltros(null,null,null,registroComprobanteDTO.getCodigoSerie(),registroComprobanteDTO.getCodigoComprobanteId());

        if(!litaSerieAsociada.isEmpty()){
            throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST,"La serie ya se encuentra registrada");
        }

        registroComprobanteDTO.setCodigoComprobanteDescripcion(null);
        registroComprobanteDTO.setEstadoDescripcion(null);
        registroComprobanteDTO.setCorrelativoInicial("0001");
        registroComprobanteDTO.setActivo(Constantes.HABILITADO);
        registroComprobanteDTO.setEstadoId(UUID.fromString(Constantes.getSingleKeyFromValue(Constantes.ESTADOS_REGISTRO_COMPROBANTES,"ACTIVO")));
        registroComprobanteDTO.setClienteId(1);
        registroComprobanteDTO.setOrganizacionId(1);
        registroComprobanteDTO.setUsuarioCreacionId(UUID.randomUUID());
        registroComprobanteDTO.setFechaCreacion(LocalDateTime.now());
        registroComprobanteDTO.setFechaModificacion(LocalDateTime.now());
        registroComprobanteDTO.setUsuarioModificacionId(UUID.randomUUID());

        RegistroComprobanteEntity registroComprobanteEntity = modelMapper.map(registroComprobanteDTO, RegistroComprobanteEntity.class);
        registroComprobanteEntity = registroComprobanteRepository.save(registroComprobanteEntity);

        return modelMapper.map(registroComprobanteEntity, RegistroComprobanteDTO.class);
    }

    @Override
    public RegistroComprobanteDTO modificar(UUID id, RegistroComprobanteDTO registroComprobanteDTO) {

        RegistroComprobanteEntity registroComprobanteEntity = null;

        List<RegistroComprobanteDTO> listaPorId = busquedaPorFiltros(id, null, null, null, null);

        if (listaPorId.isEmpty()) {
            throw new EntityNotFoundException("El registro de comprobante ingresado no existe");
        } else {
            for (RegistroComprobanteDTO registroComprobanteDTO1 : listaPorId) {

                if (registroComprobanteDTO1.getEstadoDescripcion().equals("Activo")) {

                    registroComprobanteDTO1.setCodigoComprobanteDescripcion(null);
                    registroComprobanteDTO1.setEstadoDescripcion(null);
                    registroComprobanteDTO1.setCodigoComprobanteId(registroComprobanteDTO.getCodigoComprobanteId());
                    registroComprobanteDTO1.setCodigoSerie(registroComprobanteDTO.getCodigoSerie());
                    registroComprobanteDTO1.setCorrelativoAnual(registroComprobanteDTO.getCorrelativoAnual());
                    registroComprobanteDTO1.setFechaModificacion(LocalDateTime.now());
                    registroComprobanteDTO1.setUsuarioModificacionId(UUID.randomUUID());

                    registroComprobanteEntity = modelMapper.map(registroComprobanteDTO1, RegistroComprobanteEntity.class);
                    registroComprobanteEntity = registroComprobanteRepository.save(registroComprobanteEntity);
                } else {
                    throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST, "No se puede realizar la modificacion del Registro de comprobante, se encuentra en estado Inactivo");
                }
            }
            return modelMapper.map(registroComprobanteEntity, RegistroComprobanteDTO.class);
        }
    }

    @Override
    public Page<RegistroComprobanteDTO> busquedaPorFiltros(UUID id, UUID estado, Integer activo, String serie, UUID comprobante, Pageable paginador) {

        var result = registroComprobanteRepository.busquedaPageable(id,estado, activo,serie,comprobante, paginador);
        var resultDTO = result.stream().map(x -> modelMapper.map(x, RegistroComprobanteDTO.class)).collect(Collectors.toList());
        return new PageImpl<>(resultDTO, paginador, result.getTotalElements());
    }

    @Override
    public List<RegistroComprobanteDTO> busquedaPorFiltros(UUID id, UUID estado, Integer activo, String serie, UUID comprobante) {

        var result = registroComprobanteRepository.busqueda(id,estado,activo,serie,comprobante);
        return result.stream().map(x -> modelMapper.map(x, RegistroComprobanteDTO.class)).collect(Collectors.toList());
    }

    @Override
    public RegistroComprobanteDTO modificarEstado(UUID id, UUID nuevoEstado) {

        List<RegistroComprobanteDTO> listadoRegistroComprobantes = busquedaPorFiltros(id,null, null, null, null);

        if (listadoRegistroComprobantes.isEmpty()) {
            throw new EntityNotFoundException("El concepto pago ingresado no existe");
        }
        RegistroComprobanteDTO registroComprobanteDTO= listadoRegistroComprobantes.get(0);
        UUID estadoGuardado = registroComprobanteDTO.getEstadoId();

        if(estadoGuardado.equals(nuevoEstado)){
            throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST, "No se puede realizar el cambio de estado, el estado actual y el estado ingresado son el mismo");
        }

        registroComprobanteDTO.setEstadoId(nuevoEstado);
        registroComprobanteDTO.setCodigoComprobanteDescripcion(null);
        registroComprobanteDTO.setEstadoDescripcion(null);

        RegistroComprobanteEntity registroComprobanteEntity = modelMapper.map(registroComprobanteDTO, RegistroComprobanteEntity.class);
        registroComprobanteEntity = registroComprobanteRepository.save(registroComprobanteEntity);

        return modelMapper.map(registroComprobanteEntity, RegistroComprobanteDTO.class);
    }

    @Override
    public List<RegistroComprobanteDTO> busquedaPorFiltros(UUID id, UUID estado, Integer activo, String serie, UUID comprobante, int offset, int size) {
        return null;
    }
}
