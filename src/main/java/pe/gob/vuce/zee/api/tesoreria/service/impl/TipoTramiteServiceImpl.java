package pe.gob.vuce.zee.api.tesoreria.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pe.gob.vuce.zee.api.tesoreria.base.Constantes;
import pe.gob.vuce.zee.api.tesoreria.dto.TipoTramiteDTO;
import pe.gob.vuce.zee.api.tesoreria.exceptions.BadRequestException;
import pe.gob.vuce.zee.api.tesoreria.exceptions.EntityNotFoundException;
import pe.gob.vuce.zee.api.tesoreria.models.TipoTramiteEntity;
import pe.gob.vuce.zee.api.tesoreria.repository.TipoTramiteRepository;
import pe.gob.vuce.zee.api.tesoreria.service.TipoTramiteService;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TipoTramiteServiceImpl implements TipoTramiteService {

    @Autowired
    private TipoTramiteRepository tipoTramiteRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TipoTramiteDTO guardar(TipoTramiteDTO tipoTramiteDTO) {

        //tipoTramiteDTO.setEstado(Constantes.getSingleKeyFromValue(Constantes. ESTADOS_TRAMITE_PAGO,"GUARDADO"));
        tipoTramiteDTO.setActivo(Constantes.HABILITADO);
        tipoTramiteDTO.setClienteId(1);
        tipoTramiteDTO.setOrganizacionId(1);
        tipoTramiteDTO.setUsuarioCreacionId(UUID.randomUUID());
        tipoTramiteDTO.setFechaCreacion(LocalDateTime.now());
        tipoTramiteDTO.setFechaModificacion(LocalDateTime.now());
        tipoTramiteDTO.setUsuarioModificacionId(UUID.randomUUID());

        TipoTramiteEntity tipoTramiteEntity = modelMapper.map(tipoTramiteDTO, TipoTramiteEntity.class);
        tipoTramiteEntity = tipoTramiteRepository.save(tipoTramiteEntity);

        return modelMapper.map(tipoTramiteEntity, TipoTramiteDTO.class);
    }

    @Override
    public TipoTramiteDTO modificar(UUID id, TipoTramiteDTO tipoTramiteDTO) {

        TipoTramiteEntity tipoTramiteModificar= tipoTramiteRepository.findById(id).orElse(null);

        if(tipoTramiteModificar == null){
            throw new EntityNotFoundException("El tipo de tramite no existe");
        }
        //if(tipoTramiteModificar.getEstado() != Constantes.getSingleKeyFromValue(Constantes. ESTADOS_TRAMITE_PAGO,"GUARDADO")){
          //  throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST,"No se puede realizar la modificacion, el tipo tramite no se encuentra en estado guardado");
        //}
        tipoTramiteModificar.setTipoCalculo(tipoTramiteDTO.getTipoCalculo());
        tipoTramiteModificar.setCodigoDestino(tipoTramiteDTO.getCodigoDestino());
        tipoTramiteModificar.setCodigoMoneda(tipoTramiteDTO.getCodigoMoneda());
        tipoTramiteModificar.setPorcentajeUIT(tipoTramiteDTO.getPorcentajeUIT());
        tipoTramiteModificar.setMontoPago(tipoTramiteDTO.getMontoPago());
        tipoTramiteModificar.setCantidadInicial(tipoTramiteDTO.getCantidadInicial());
        tipoTramiteModificar.setCantidadFinal(tipoTramiteDTO.getCantidadFinal());
        tipoTramiteModificar.setCodigoModulo(tipoTramiteDTO.getCodigoModulo());
        tipoTramiteModificar.setCodigoProceso(tipoTramiteDTO.getCodigoProceso());
        tipoTramiteModificar.setCodigoFormulario(tipoTramiteDTO.getCodigoFormulario());
        tipoTramiteModificar.setCodigoAccion(tipoTramiteDTO.getCodigoAccion());
        tipoTramiteModificar.setPreguntaDatoInformado(tipoTramiteDTO.getPreguntaDatoInformado());
        tipoTramiteModificar.setFechaModificacion(LocalDateTime.now());
        tipoTramiteModificar.setUsuarioModificacionId(UUID.randomUUID());

        TipoTramiteEntity tipoTramiteModificado = modelMapper.map(tipoTramiteModificar, TipoTramiteEntity.class);
        tipoTramiteModificado = tipoTramiteRepository.save(tipoTramiteModificado);

        return modelMapper.map(tipoTramiteModificado, TipoTramiteDTO.class);
    }
}
