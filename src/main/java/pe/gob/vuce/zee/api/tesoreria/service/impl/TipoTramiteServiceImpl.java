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
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TipoTramiteServiceImpl implements TipoTramiteService {

    @Autowired
    private TipoTramiteRepository tipoTramiteRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TipoTramiteDTO guardar(TipoTramiteDTO tipoTramiteDTO) {

        tipoTramiteDTO.setTipoCalculoDescripcion(null);
        tipoTramiteDTO.setCodigoDestinoDescripcion(null);
        tipoTramiteDTO.setCodigoMonedaDescripcion(null);
        tipoTramiteDTO.setCodigoModuloDescripcion(null);
        tipoTramiteDTO.setCodigoProcesoDescripcion(null);
        tipoTramiteDTO.setCodigoFormularioDescripcion(null);
        tipoTramiteDTO.setCodigoAccionDescripcion(null);
        tipoTramiteDTO.setEstadoDescripcion(null);
        tipoTramiteDTO.setEstadoId(UUID.fromString(Constantes.getSingleKeyFromValue(Constantes. ESTADOS_TRAMITE_PAGO,"ACTIVO")));
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

        TipoTramiteDTO tipoTramiteModificar = buscarId(id);

        if(tipoTramiteModificar == null){
            throw new EntityNotFoundException("El tipo de tramite no existe");
        }
        if(tipoTramiteModificar.getEstadoId() != UUID.fromString(Constantes.getSingleKeyFromValue(Constantes.ESTADOS_TRAMITE_PAGO,"ACTIVO"))){

            throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST,"No se puede realizar la modificacion, el tipo tramite no se encuentra en estado activo");
        }

        tipoTramiteModificar.setTipoCalculoDescripcion(null);
        tipoTramiteModificar.setCodigoDestinoDescripcion(null);
        tipoTramiteModificar.setCodigoMonedaDescripcion(null);
        tipoTramiteModificar.setCodigoModuloDescripcion(null);
        tipoTramiteModificar.setCodigoProcesoDescripcion(null);
        tipoTramiteModificar.setCodigoFormularioDescripcion(null);
        tipoTramiteModificar.setCodigoAccionDescripcion(null);
        tipoTramiteModificar.setEstadoDescripcion(null);
        tipoTramiteModificar.setTipoCalculoId(tipoTramiteDTO.getTipoCalculoId());
        tipoTramiteModificar.setCodigoDestinoId(tipoTramiteDTO.getCodigoDestinoId());
        tipoTramiteModificar.setCodigoMonedaId(tipoTramiteDTO.getCodigoMonedaId());
        tipoTramiteModificar.setPorcentajeUIT(tipoTramiteDTO.getPorcentajeUIT());
        tipoTramiteModificar.setMontoPago(tipoTramiteDTO.getMontoPago());
        tipoTramiteModificar.setCantidadInicial(tipoTramiteDTO.getCantidadInicial());
        tipoTramiteModificar.setCantidadFinal(tipoTramiteDTO.getCantidadFinal());
        tipoTramiteModificar.setCodigoModuloId(tipoTramiteDTO.getCodigoModuloId());
        tipoTramiteModificar.setCodigoProcesoId(tipoTramiteDTO.getCodigoProcesoId());
        tipoTramiteModificar.setCodigoFormularioId(tipoTramiteDTO.getCodigoFormularioId());
        tipoTramiteModificar.setCodigoAccionId(tipoTramiteDTO.getCodigoAccionId());
        tipoTramiteModificar.setPreguntaDatoInformado(tipoTramiteDTO.getPreguntaDatoInformado());
        tipoTramiteModificar.setFechaModificacion(LocalDateTime.now());
        tipoTramiteModificar.setUsuarioModificacionId(UUID.randomUUID());

        TipoTramiteEntity tipoTramiteModificado = modelMapper.map(tipoTramiteModificar, TipoTramiteEntity.class);
        tipoTramiteModificado = tipoTramiteRepository.save(tipoTramiteModificado);

        return modelMapper.map(tipoTramiteModificado, TipoTramiteDTO.class);
    }

    @Override
    public TipoTramiteDTO buscarId(UUID id) {
        TipoTramiteEntity tipoTramite= tipoTramiteRepository.findById(id).orElse(null);
        return modelMapper.map(tipoTramite, TipoTramiteDTO.class);
    }

    @Override
    public List<TipoTramiteDTO> buscarTramitePago(UUID id) {

        var result = tipoTramiteRepository.findByTramitePagoId(id);

        return result.stream().map(x -> modelMapper.map(x, TipoTramiteDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<TipoTramiteDTO> guardarAll(List<TipoTramiteDTO> listaObjetos) {

        List<TipoTramiteEntity> listaobjetosEntity = new ArrayList<>();

        for(TipoTramiteDTO tipoTramiteDTO : listaObjetos){

            tipoTramiteDTO.setTipoCalculoDescripcion(null);
            tipoTramiteDTO.setCodigoDestinoDescripcion(null);
            tipoTramiteDTO.setCodigoMonedaDescripcion(null);
            tipoTramiteDTO.setCodigoModuloDescripcion(null);
            tipoTramiteDTO.setCodigoProcesoDescripcion(null);
            tipoTramiteDTO.setCodigoFormularioDescripcion(null);
            tipoTramiteDTO.setCodigoAccionDescripcion(null);
            tipoTramiteDTO.setEstadoDescripcion(null);
            tipoTramiteDTO.setEstadoId(UUID.fromString(Constantes.getSingleKeyFromValue(Constantes. ESTADOS_TRAMITE_PAGO,"ACTIVO")));
            tipoTramiteDTO.setActivo(Constantes.HABILITADO);
            tipoTramiteDTO.setClienteId(1);
            tipoTramiteDTO.setOrganizacionId(1);
            tipoTramiteDTO.setUsuarioCreacionId(UUID.randomUUID());
            tipoTramiteDTO.setFechaCreacion(LocalDateTime.now());
            tipoTramiteDTO.setFechaModificacion(LocalDateTime.now());
            tipoTramiteDTO.setUsuarioModificacionId(UUID.randomUUID());

            TipoTramiteEntity tipoTramiteEntity = modelMapper.map(tipoTramiteDTO, TipoTramiteEntity.class);

            listaobjetosEntity.add(tipoTramiteEntity);
        }

        listaobjetosEntity = tipoTramiteRepository.saveAll(listaobjetosEntity);

        return listaobjetosEntity.stream().map(x -> modelMapper.map(x, TipoTramiteDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<TipoTramiteDTO> modificarAll(List<TipoTramiteDTO> listaObjetos) {

        List<TipoTramiteEntity> listaTiposTramiteDataBase = tipoTramiteRepository.findByTramitePagoId(listaObjetos.get(0).getTramitePagoId());
        tipoTramiteRepository.deleteAll(listaTiposTramiteDataBase);

        for(TipoTramiteDTO tipoTramiteDTO : listaObjetos){

            if(tipoTramiteDTO.getId() == null){
                tipoTramiteDTO.setTipoCalculoDescripcion(null);
                tipoTramiteDTO.setCodigoDestinoDescripcion(null);
                tipoTramiteDTO.setCodigoMonedaDescripcion(null);
                tipoTramiteDTO.setCodigoModuloDescripcion(null);
                tipoTramiteDTO.setCodigoProcesoDescripcion(null);
                tipoTramiteDTO.setCodigoFormularioDescripcion(null);
                tipoTramiteDTO.setCodigoAccionDescripcion(null);
                tipoTramiteDTO.setEstadoDescripcion(null);
                tipoTramiteDTO.setEstadoId(UUID.fromString(Constantes.getSingleKeyFromValue(Constantes. ESTADOS_TRAMITE_PAGO,"ACTIVO")));
                tipoTramiteDTO.setActivo(Constantes.HABILITADO);
                tipoTramiteDTO.setClienteId(1);
                tipoTramiteDTO.setOrganizacionId(1);
                tipoTramiteDTO.setUsuarioCreacionId(UUID.randomUUID());
                tipoTramiteDTO.setFechaCreacion(LocalDateTime.now());
                tipoTramiteDTO.setFechaModificacion(LocalDateTime.now());
                tipoTramiteDTO.setUsuarioModificacionId(UUID.randomUUID());
            }else{
                tipoTramiteDTO.setTipoCalculoDescripcion(null);
                tipoTramiteDTO.setCodigoDestinoDescripcion(null);
                tipoTramiteDTO.setCodigoMonedaDescripcion(null);
                tipoTramiteDTO.setCodigoModuloDescripcion(null);
                tipoTramiteDTO.setCodigoProcesoDescripcion(null);
                tipoTramiteDTO.setCodigoFormularioDescripcion(null);
                tipoTramiteDTO.setCodigoAccionDescripcion(null);
                tipoTramiteDTO.setEstadoDescripcion(null);
            }

        }

        List<TipoTramiteEntity>  listaModificadaEntity =listaObjetos.stream().map(x -> modelMapper.map(x, TipoTramiteEntity.class)).collect(Collectors.toList());
        listaModificadaEntity = tipoTramiteRepository.saveAll(listaModificadaEntity);

        return listaModificadaEntity.stream().map(x -> modelMapper.map(x, TipoTramiteDTO.class)).collect(Collectors.toList());
    }
}

