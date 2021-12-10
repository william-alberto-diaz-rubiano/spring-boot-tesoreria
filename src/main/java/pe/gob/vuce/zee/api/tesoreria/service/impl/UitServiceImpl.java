package pe.gob.vuce.zee.api.tesoreria.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.gob.vuce.zee.api.tesoreria.base.Constantes;
import pe.gob.vuce.zee.api.tesoreria.dto.UitDTO;
import pe.gob.vuce.zee.api.tesoreria.models.UitEntity;
import pe.gob.vuce.zee.api.tesoreria.repository.UitRepository;
import pe.gob.vuce.zee.api.tesoreria.service.UitService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UitServiceImpl implements UitService {

    @Autowired
    private UitRepository uitRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UitDTO guardar(UitDTO uitDTO) {

        uitDTO.setEstadoDescripcion(null);
        uitDTO.setEstadoId(UUID.fromString(Constantes.getSingleKeyFromValue(Constantes.ESTADOS_UIT,"ACTIVO")));
        uitDTO.setActivo(Constantes.HABILITADO);
        uitDTO.setClienteId(1);
        uitDTO.setOrganizacionId(1);
        uitDTO.setUsuarioCreacionId(UUID.randomUUID());
        uitDTO.setFechaCreacion(LocalDateTime.now());
        uitDTO.setFechaModificacion(LocalDateTime.now());
        uitDTO.setUsuarioModificacionId(UUID.randomUUID());

        UitEntity uitEntity= modelMapper.map(uitDTO, UitEntity.class);
        uitEntity = uitRepository.save(uitEntity);

        return modelMapper.map(uitEntity, UitDTO.class);
    }

    @Override
    public BigDecimal valorMonto(String anio,BigDecimal porcentajeUit) {

        UitEntity uit=uitRepository.findByAnioUit(anio);
        var monto= uit.getValorSolesUit() / 100 * porcentajeUit.doubleValue();

        return BigDecimal.valueOf(monto);
    }
}
