package pe.gob.vuce.zee.api.maestros.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pe.gob.vuce.zee.api.maestros.base.Constantes;
import pe.gob.vuce.zee.api.maestros.dto.MaestroDTO;
import pe.gob.vuce.zee.api.maestros.exceptions.NotEntityFound;
import pe.gob.vuce.zee.api.maestros.repository.MaestroRepository;
import pe.gob.vuce.zee.api.maestros.service.MaestroService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MaestroServiceImpl implements MaestroService {
    private final ModelMapper modelMapper;
    private final MaestroRepository maestroRepository;

    public MaestroServiceImpl(ModelMapper modelMapper, MaestroRepository maestroRepository) {
        this.modelMapper = modelMapper;
        this.maestroRepository = maestroRepository;
    }

    @Override
    public Set<MaestroDTO> buscarPorPrefijo(Integer prefijo) {
        var maestroEntities = maestroRepository.findByPrefijoAndEstado(prefijo, Constantes.HABILITADO);
        if (maestroEntities.isEmpty()) {
            throw new NotEntityFound(String.format("No existen registros con el prefijo: %d", prefijo));
        }
        return maestroEntities
                .stream()
                .filter(entity -> entity.getPrefijo() != 0)
                .map(entity -> modelMapper.map(entity, MaestroDTO.class))
                .collect(Collectors.toSet());
    }

    @Override
    public MaestroDTO buscarPorPrefijoYCorrelativo(Integer prefijo, Integer correlativo) {
        var maestroOptional = maestroRepository.findByPrefijoAndCorrelativoAndEstado(prefijo, correlativo, Constantes.HABILITADO);
        return maestroOptional
                .map(entity -> modelMapper.map(entity, MaestroDTO.class))
                .orElseThrow(() -> new NotEntityFound(String.format("No existe registro con el prefijo %d y correlativo %d", prefijo, correlativo)));
    }
}
