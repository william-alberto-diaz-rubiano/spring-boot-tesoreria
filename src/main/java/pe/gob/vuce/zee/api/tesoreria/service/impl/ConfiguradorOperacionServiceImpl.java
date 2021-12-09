package pe.gob.vuce.zee.api.tesoreria.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pe.gob.vuce.zee.api.tesoreria.base.Constantes;
import pe.gob.vuce.zee.api.tesoreria.dto.ConfiguradorOperacionDTO;
import pe.gob.vuce.zee.api.tesoreria.exceptions.BadRequestException;
import pe.gob.vuce.zee.api.tesoreria.exceptions.EntityNotFoundException;
import pe.gob.vuce.zee.api.tesoreria.models.ConfiguradorOperacionEntity;
import pe.gob.vuce.zee.api.tesoreria.models.MaestroEntity;
import pe.gob.vuce.zee.api.tesoreria.repository.ConfiguradorOperacionRepository;
import pe.gob.vuce.zee.api.tesoreria.service.ConfiguradorOperacionService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ConfiguradorOperacionServiceImpl implements ConfiguradorOperacionService {


    @Autowired
    private ConfiguradorOperacionRepository configuradorOperacionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ConfiguradorOperacionDTO> busquedaPorFiltros(UUID id,UUID estado, Integer activo, UUID tramite, UUID operacion) {
        var result = configuradorOperacionRepository.busqueda(id,estado,activo,tramite,operacion);
        return result.stream().map(x -> modelMapper.map(x, ConfiguradorOperacionDTO.class)).collect(Collectors.toList());
    }

    @Override
    public ConfiguradorOperacionDTO guardar(ConfiguradorOperacionDTO configuradorOperacionDTO) {

        List<ConfiguradorOperacionDTO> listaPorTramiteConcepto= busquedaPorFiltros(null,null,null,configuradorOperacionDTO.getTramiteId(),null);

        if(!listaPorTramiteConcepto.isEmpty()){
            throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST,"El tipo de tramite/concepto ya se encuentra registrado");
        }
        configuradorOperacionDTO.setEstadoId(UUID.fromString(Constantes.getSingleKeyFromValue(Constantes.ESTADOS_CONFIGURADOR,"ACTIVO")));
        configuradorOperacionDTO.setActivo(Constantes.HABILITADO);
        configuradorOperacionDTO.setClienteId(1);
        configuradorOperacionDTO.setOrganizacionId(1);
        configuradorOperacionDTO.setUsuarioCreacionId(UUID.randomUUID());
        configuradorOperacionDTO.setFechaCreacion(LocalDateTime.now());
        configuradorOperacionDTO.setFechaModificacion(LocalDateTime.now());
        configuradorOperacionDTO.setUsuarioModificacionId(UUID.randomUUID());

        ConfiguradorOperacionEntity configuradorOperacionEntity = modelMapper.map(configuradorOperacionDTO, ConfiguradorOperacionEntity.class);
        configuradorOperacionEntity = configuradorOperacionRepository.save(configuradorOperacionEntity);

        return modelMapper.map(configuradorOperacionEntity, ConfiguradorOperacionDTO.class);
    }

    @Override
    public ConfiguradorOperacionDTO modificar(UUID id,ConfiguradorOperacionDTO configuradorOperacionDTO) {


        ConfiguradorOperacionEntity configuradorOperacionEntity = null;

        List<ConfiguradorOperacionDTO> listaConfigurador= busquedaPorFiltros(id,null, null,null,null);

        List<ConfiguradorOperacionDTO> listaPorTramiteConcepto= busquedaPorFiltros(null,null,null,configuradorOperacionDTO.getTramiteId(),null);


        if(listaConfigurador.isEmpty()){
            throw new EntityNotFoundException("El configurador de operaciones no existe");
        }

        ConfiguradorOperacionDTO configuradorOperacionDTO1 = listaConfigurador.get(0);

            if(configuradorOperacionDTO1.getEstadoDescripcion().equals("ACTIVO") ){

                if((listaPorTramiteConcepto.isEmpty()) || configuradorOperacionDTO1.getTramiteId().equals(configuradorOperacionDTO.getTramiteId())){
                    configuradorOperacionDTO1.setTramiteId(configuradorOperacionDTO.getTramiteId());
                    configuradorOperacionDTO1.setOperacionId(configuradorOperacionDTO.getOperacionId());
                    configuradorOperacionDTO1.setEstadoDescripcion(null);
                    configuradorOperacionDTO1.setTramiteDescripcion(null);
                    configuradorOperacionDTO1.setOperacionDescripcion(null);
                    configuradorOperacionEntity = modelMapper.map(configuradorOperacionDTO1, ConfiguradorOperacionEntity.class);
                    configuradorOperacionEntity = configuradorOperacionRepository.save(configuradorOperacionEntity);
                }else{
                    throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST,"Ya se encuentra registrado un configurador de operaciones con el tipo de tramite/concepto ingresado");
                }

            }else{
                throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST,"No se puede realizar la modificacion, el configurador se encuentra en estado Inactivo");
                }
        return modelMapper.map(configuradorOperacionEntity, ConfiguradorOperacionDTO.class);
    }

    @Override
    public Page<ConfiguradorOperacionDTO> busquedaPorFiltros(UUID id,UUID estado, Integer activo,UUID tramite, UUID operacion, Pageable paginador) {
        var result = configuradorOperacionRepository.busquedaPageable(id,estado,activo,tramite,operacion,paginador);

        var resultDTO = result.stream().map(x -> modelMapper.map(x, ConfiguradorOperacionDTO.class)).collect(Collectors.toList());

        return new PageImpl<>(resultDTO, paginador, result.getTotalElements());
    }

    @Override
    public List<ConfiguradorOperacionDTO> busquedaPorFiltros(UUID id,UUID estado, Integer activo,UUID tramite,UUID operacion,int offset, int size) {
        return Collections.emptyList();
    }


}
