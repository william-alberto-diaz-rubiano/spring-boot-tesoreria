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
import pe.gob.vuce.zee.api.tesoreria.dto.TipoCambioDTO;
import pe.gob.vuce.zee.api.tesoreria.exceptions.BadRequestException;
import pe.gob.vuce.zee.api.tesoreria.models.ConfiguradorOperacionEntity;
import pe.gob.vuce.zee.api.tesoreria.models.TipoCambioEntity;
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
    public List<ConfiguradorOperacionDTO> busquedaPorFiltros(Integer estado, Integer activo, Integer tramite, Integer operacion, LocalDateTime fechaCreacion) {
        var result = configuradorOperacionRepository.busqueda(estado,activo,tramite,operacion,fechaCreacion);
        return result.stream().map(ConfiguradorOperacionDTO::new).collect(Collectors.toList());
    }

    @Override
    public ConfiguradorOperacionDTO guardar(ConfiguradorOperacionDTO configuradorOperacionDTO) {
        List<ConfiguradorOperacionDTO> listaPorTramiteConcepto= busquedaPorFiltros(Constantes.getSingleKeyFromValue(Constantes.ESTADOS_TIPO_CAMBIO,"ACTIVO"), Constantes.HABILITADO,configuradorOperacionDTO.getTramite(),null,null);
        if(!listaPorTramiteConcepto.isEmpty()){
            throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST,"El tipo de tramite/concepto ya se encuentra registrado");
        }
        configuradorOperacionDTO.setEstado(Constantes.getSingleKeyFromValue(Constantes.ESTADOS_TIPO_CAMBIO,"ACTIVO"));
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
    public Page<ConfiguradorOperacionDTO> busquedaPorFiltros(Integer estado, Integer activo, Integer tramite, Integer operacion,LocalDateTime fechaCreacion, Pageable paginador) {
        var result = configuradorOperacionRepository.busquedaPageable(estado,activo,tramite,operacion,fechaCreacion,paginador);
        var resultDTO = result.stream().map(ConfiguradorOperacionDTO::new).collect(Collectors.toList());

        return new PageImpl<>(resultDTO, paginador, result.getTotalElements());
    }

    @Override
    public List<ConfiguradorOperacionDTO> busquedaPorFiltros(Integer estado, Integer activo, Integer tramite, Integer operacion,LocalDateTime fechaCreacion, int offset, int size) {
        return Collections.emptyList();
    }


}
