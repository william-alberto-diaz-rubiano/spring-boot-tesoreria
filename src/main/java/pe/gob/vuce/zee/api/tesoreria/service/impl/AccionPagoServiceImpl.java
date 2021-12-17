package pe.gob.vuce.zee.api.tesoreria.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pe.gob.vuce.zee.api.tesoreria.base.Constantes;
import pe.gob.vuce.zee.api.tesoreria.dto.AccionPagoDTO;
import pe.gob.vuce.zee.api.tesoreria.dto.TramitePagoDTO;
import pe.gob.vuce.zee.api.tesoreria.exceptions.BadRequestException;
import pe.gob.vuce.zee.api.tesoreria.exceptions.EntityNotFoundException;
import pe.gob.vuce.zee.api.tesoreria.models.AccionPagoEntity;
import pe.gob.vuce.zee.api.tesoreria.repository.AccionPagoRepository;
import pe.gob.vuce.zee.api.tesoreria.service.AccionPagoService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccionPagoServiceImpl implements AccionPagoService {

    @Autowired
    private AccionPagoRepository accionPagoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AccionPagoDTO guardar(AccionPagoDTO accionPagoDTO) {

        accionPagoDTO.setCodigoModuloDescripcion(null);
        accionPagoDTO.setCodigoProcesoDescripcion(null);
        accionPagoDTO.setCodigoFormularioDescripcion(null);
        accionPagoDTO.setCodigoAccionDescripcion(null);
        accionPagoDTO.setEstadoDescripcion(null);
        accionPagoDTO.setEstadoId(UUID.fromString(Constantes.getSingleKeyFromValue(Constantes. ESTADOS_TRAMITE_PAGO,"ACTIVO")));
        accionPagoDTO.setActivo(Constantes.HABILITADO);
        accionPagoDTO.setClienteId(1);
        accionPagoDTO.setOrganizacionId(1);
        accionPagoDTO.setUsuarioCreacionId(UUID.randomUUID());
        accionPagoDTO.setFechaCreacion(LocalDateTime.now());
        accionPagoDTO.setFechaModificacion(LocalDateTime.now());
        accionPagoDTO.setUsuarioModificacionId(UUID.randomUUID());

       AccionPagoEntity accionPagoEntity = modelMapper.map(accionPagoDTO, AccionPagoEntity.class);
       accionPagoEntity = accionPagoRepository.save(accionPagoEntity);

        return modelMapper.map(accionPagoEntity, AccionPagoDTO.class);
    }

    @Override
    public AccionPagoDTO modificar(UUID id, AccionPagoDTO accionPagoDTO) {

        AccionPagoDTO accionPagoModificar= buscarId(id);

        if(accionPagoModificar == null){
            throw new EntityNotFoundException("La acción con la que se activara el tramite no existe");
        }
        if(accionPagoModificar.getEstadoId() != UUID.fromString(Constantes.getSingleKeyFromValue(Constantes. ESTADOS_TRAMITE_PAGO,"ACTIVO"))){
            throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST,"No se puede realizar la modificacion, la Accion de pago no se encuentra en estado activo");
        }
        accionPagoDTO.setCodigoModuloDescripcion(null);
        accionPagoDTO.setCodigoProcesoDescripcion(null);
        accionPagoDTO.setCodigoFormularioDescripcion(null);
        accionPagoDTO.setCodigoAccionDescripcion(null);
        accionPagoDTO.setEstadoDescripcion(null);
        accionPagoModificar.setCodigoModuloId(accionPagoDTO.getCodigoModuloId());
        accionPagoModificar.setCodigoProcesoId(accionPagoDTO.getCodigoProcesoId());
        accionPagoModificar.setCodigoFormularioId(accionPagoDTO.getCodigoFormularioId());
        accionPagoModificar.setCodigoAccionId(accionPagoDTO.getCodigoAccionId());

        accionPagoModificar.setFechaModificacion(LocalDateTime.now());
        accionPagoModificar.setUsuarioModificacionId(UUID.randomUUID());

        AccionPagoEntity accionPagoModificado = modelMapper.map(accionPagoModificar, AccionPagoEntity.class);
        accionPagoModificado = accionPagoRepository.save(accionPagoModificado);

        return modelMapper.map(accionPagoModificado, AccionPagoDTO.class);
    }

    @Override
    public AccionPagoDTO buscarId(UUID id) {

        AccionPagoEntity accionPago= accionPagoRepository.findById(id).orElse(null);
        return modelMapper.map(accionPago, AccionPagoDTO.class);
    }

    @Override
    public List<AccionPagoDTO> buscarPorTramitePago(UUID id) {
        var result = accionPagoRepository.findByTramitePagoId(id);
        return result.stream().map(x -> modelMapper.map(x, AccionPagoDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<AccionPagoDTO> guardarAll(List<AccionPagoDTO> listaObjetos) {

        List<AccionPagoEntity> listaobjetosEntity = new ArrayList<>();

        for(AccionPagoDTO accionPagoDTO : listaObjetos){

            accionPagoDTO.setCodigoModuloDescripcion(null);
            accionPagoDTO.setCodigoProcesoDescripcion(null);
            accionPagoDTO.setCodigoFormularioDescripcion(null);
            accionPagoDTO.setCodigoAccionDescripcion(null);
            accionPagoDTO.setEstadoDescripcion(null);
            accionPagoDTO.setEstadoId(UUID.fromString(Constantes.getSingleKeyFromValue(Constantes. ESTADOS_TRAMITE_PAGO,"ACTIVO")));
            accionPagoDTO.setActivo(Constantes.HABILITADO);
            accionPagoDTO.setClienteId(1);
            accionPagoDTO.setOrganizacionId(1);
            accionPagoDTO.setUsuarioCreacionId(UUID.randomUUID());
            accionPagoDTO.setFechaCreacion(LocalDateTime.now());
            accionPagoDTO.setFechaModificacion(LocalDateTime.now());
            accionPagoDTO.setUsuarioModificacionId(UUID.randomUUID());

           AccionPagoEntity accionPagoEntity = modelMapper.map(accionPagoDTO, AccionPagoEntity.class);

            listaobjetosEntity.add(accionPagoEntity);
        }

        listaobjetosEntity = accionPagoRepository.saveAll(listaobjetosEntity);

        return listaobjetosEntity.stream().map(x -> modelMapper.map(x, AccionPagoDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<AccionPagoDTO> modificarAll(List<AccionPagoDTO> listaObjetos) {

        List<AccionPagoEntity> listaAccionPagoDataBase = accionPagoRepository.findByTramitePagoId(listaObjetos.get(0).getTramitePagoId());
        accionPagoRepository.deleteAll(listaAccionPagoDataBase);

        for(AccionPagoDTO accionPagoDTO : listaObjetos){

            if(accionPagoDTO.getId() == null){

                accionPagoDTO.setCodigoModuloDescripcion(null);
                accionPagoDTO.setCodigoProcesoDescripcion(null);
                accionPagoDTO.setCodigoFormularioDescripcion(null);
                accionPagoDTO.setCodigoAccionDescripcion(null);
                accionPagoDTO.setEstadoDescripcion(null);
                accionPagoDTO.setEstadoId(UUID.fromString(Constantes.getSingleKeyFromValue(Constantes. ESTADOS_TRAMITE_PAGO,"ACTIVO")));
                accionPagoDTO.setActivo(Constantes.HABILITADO);
                accionPagoDTO.setClienteId(1);
                accionPagoDTO.setOrganizacionId(1);
                accionPagoDTO.setUsuarioCreacionId(UUID.randomUUID());
                accionPagoDTO.setFechaCreacion(LocalDateTime.now());
                accionPagoDTO.setFechaModificacion(LocalDateTime.now());
                accionPagoDTO.setUsuarioModificacionId(UUID.randomUUID());
            }else{
                accionPagoDTO.setCodigoModuloDescripcion(null);
                accionPagoDTO.setCodigoProcesoDescripcion(null);
                accionPagoDTO.setCodigoFormularioDescripcion(null);
                accionPagoDTO.setCodigoAccionDescripcion(null);
                accionPagoDTO.setEstadoDescripcion(null);
            }

        }

        List<AccionPagoEntity>  listaModificadaEntity =listaObjetos.stream().map(x -> modelMapper.map(x, AccionPagoEntity.class)).collect(Collectors.toList());
        listaModificadaEntity = accionPagoRepository.saveAll(listaModificadaEntity);

        return listaModificadaEntity.stream().map(x -> modelMapper.map(x, AccionPagoDTO.class)).collect(Collectors.toList());
    }
}
