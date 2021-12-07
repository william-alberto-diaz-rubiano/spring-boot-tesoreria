package pe.gob.vuce.zee.api.tesoreria.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pe.gob.vuce.zee.api.tesoreria.base.Constantes;
import pe.gob.vuce.zee.api.tesoreria.dto.TramitePagoDTO;
import pe.gob.vuce.zee.api.tesoreria.exceptions.BadRequestException;
import pe.gob.vuce.zee.api.tesoreria.exceptions.EntityNotFoundException;
import pe.gob.vuce.zee.api.tesoreria.models.TramitePagoEntity;
import pe.gob.vuce.zee.api.tesoreria.repository.TramitePagoRepository;
import pe.gob.vuce.zee.api.tesoreria.service.TramitePagoService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TramitePagoServiceImpl implements TramitePagoService {

    @Autowired
    private TramitePagoRepository tramitePagoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TramitePagoDTO guardar(TramitePagoDTO tramitePagoDTO) {

        tramitePagoDTO.setActivo(Constantes.HABILITADO);
        tramitePagoDTO.setEstadoId(UUID.fromString(Constantes.getSingleKeyFromValue(Constantes.ESTADOS_TRAMITE_PAGO,"GUARDADO")));
        tramitePagoDTO.setClienteId(1);
        tramitePagoDTO.setOrganizacionId(1);
        tramitePagoDTO.setUsuarioCreacionId(UUID.randomUUID());
        tramitePagoDTO.setFechaCreacion(LocalDateTime.now());
        tramitePagoDTO.setFechaModificacion(LocalDateTime.now());
        tramitePagoDTO.setUsuarioModificacionId(UUID.randomUUID());
        TramitePagoEntity tramitePagoEntity = modelMapper.map(tramitePagoDTO, TramitePagoEntity.class);
        tramitePagoEntity = tramitePagoRepository.save(tramitePagoEntity);

        return modelMapper.map(tramitePagoEntity, TramitePagoDTO.class);
    }

    @Override
    public TramitePagoDTO modificar(UUID id, TramitePagoDTO tramitePagoDTO) {

        TramitePagoEntity tramitePagoEntity = null;

        List<TramitePagoDTO> listaPorNombreTramite = busquedaPorFiltros(id,null, null, null, null, null, null);
        if (listaPorNombreTramite.isEmpty()) {
            throw new EntityNotFoundException("El nombre de tramite ingresado no existe");
        } else {
            for (TramitePagoDTO tramitePagoDTO1 : listaPorNombreTramite) {

                if (tramitePagoDTO1.getEstadoDescripcion().equals("ACTIVO")) {
                    tramitePagoDTO1.setConfiguradorOperacion(tramitePagoDTO.getConfiguradorOperacion());
                    tramitePagoDTO1.setCodigoProcesoId(tramitePagoDTO.getCodigoProcesoId());
                    tramitePagoDTO1.setFlagDestinos(tramitePagoDTO.isFlagDestinos());
                    tramitePagoDTO1.setCodigoPago(tramitePagoDTO.getCodigoPago());
                    tramitePagoDTO1.setNombrePago(tramitePagoDTO.getNombrePago());
                    tramitePagoDTO1.setBaseLegal(tramitePagoDTO.getBaseLegal());
                    tramitePagoDTO1.setDiazPlazo(tramitePagoDTO.getDiazPlazo());
                    tramitePagoDTO1.setEstadoDescripcion(null);
                    tramitePagoDTO1.setCodigoProcesoDescripcion(null);
                    tramitePagoEntity = modelMapper.map(tramitePagoDTO1, TramitePagoEntity.class);
                    tramitePagoEntity = tramitePagoRepository.save(tramitePagoEntity);
                } else {
                    throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST, "No se puede realizar la modificacion de la informacion principal del tramite ya que se encuentra en estado Inactivo");
                }
            }

            return modelMapper.map(tramitePagoEntity, TramitePagoDTO.class);
        }
    }

    @Override
    public Page<TramitePagoDTO> busquedaPorFiltros(UUID id,UUID estado, Integer activo, UUID tipoTramite, String nombreTramite, LocalDateTime fechaInicio, LocalDateTime fechaFin, Pageable paginador) {
        var result = tramitePagoRepository.busquedaPageable(id,estado, activo, tipoTramite, nombreTramite, fechaInicio, fechaFin, paginador);
        var resultDTO = result.stream().map(x -> modelMapper.map(x, TramitePagoDTO.class)).collect(Collectors.toList());
        return new PageImpl<>(resultDTO, paginador, result.getTotalElements());
    }

    @Override
    public List<TramitePagoDTO> busquedaPorFiltros(UUID id,UUID estado, Integer activo, UUID tipoTramite, String nombreTramite, LocalDateTime fechaInicio, LocalDateTime fechaFin) {

        var result = tramitePagoRepository.busqueda(id,estado,activo,tipoTramite,nombreTramite,fechaInicio,fechaFin);
        return result.stream().map(x -> modelMapper.map(x, TramitePagoDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<TramitePagoDTO> busquedaPorFiltros(UUID id,UUID estado, Integer activo, UUID tipoTramite, String nombreTramite, LocalDateTime fechaInicio, LocalDateTime fechaFin, int offset, int size) {
        return Collections.emptyList();
    }
}