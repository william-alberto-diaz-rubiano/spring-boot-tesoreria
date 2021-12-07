package pe.gob.vuce.zee.api.tesoreria.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pe.gob.vuce.zee.api.tesoreria.base.Constantes;
import pe.gob.vuce.zee.api.tesoreria.dto.AccionPagoDTO;
import pe.gob.vuce.zee.api.tesoreria.exceptions.BadRequestException;
import pe.gob.vuce.zee.api.tesoreria.exceptions.EntityNotFoundException;
import pe.gob.vuce.zee.api.tesoreria.models.AccionPagoEntity;
import pe.gob.vuce.zee.api.tesoreria.repository.AccionPagoRepository;
import pe.gob.vuce.zee.api.tesoreria.service.AccionPagoService;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AccionPagoServiceImpl implements AccionPagoService {

    @Autowired
    private AccionPagoRepository accionPagoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AccionPagoDTO guardar(AccionPagoDTO accionPagoDTO) {

        //accionPagoDTO.setEstado(Constantes.getSingleKeyFromValue(Constantes. ESTADOS_TRAMITE_PAGO,"ACTIVO"));
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

        AccionPagoEntity accionPagoModificar= accionPagoRepository.findById(id).orElse(null);

        if(accionPagoModificar == null){
            throw new EntityNotFoundException("El tipo de tramite no existe");
        }
        //if(accionPagoModificar.getEstado() != Constantes.getSingleKeyFromValue(Constantes. ESTADOS_TRAMITE_PAGO,"ACTIVO")){
          //  throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST,"No se puede realizar la modificacion, la Accion de pago no se encuentra en estado activo");
        //}
        accionPagoModificar.setCodigoModulo(accionPagoDTO.getCodigoModulo());
        accionPagoModificar.setCodigoProceso(accionPagoDTO.getCodigoProceso());
        accionPagoModificar.setCodigoFormulario(accionPagoDTO.getCodigoFormulario());
        accionPagoModificar.setCodigoAccion(accionPagoDTO.getCodigoAccion());

        accionPagoModificar.setFechaModificacion(LocalDateTime.now());
        accionPagoModificar.setUsuarioModificacionId(UUID.randomUUID());

        AccionPagoEntity accionPagoModificado = modelMapper.map(accionPagoModificar, AccionPagoEntity.class);
        accionPagoModificado = accionPagoRepository.save(accionPagoModificado);

        return modelMapper.map(accionPagoModificado, AccionPagoDTO.class);
    }
}
