package pe.gob.vuce.zee.api.maestros.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pe.gob.vuce.zee.api.maestros.base.Constantes;
import pe.gob.vuce.zee.api.maestros.consumer.ReniecAPI;
import pe.gob.vuce.zee.api.maestros.consumer.SunatAPI;
import pe.gob.vuce.zee.api.maestros.dto.IdentidadDTO;
import pe.gob.vuce.zee.api.maestros.dto.ReniecContentDTO;
import pe.gob.vuce.zee.api.maestros.dto.SunatResponseDTO;
import pe.gob.vuce.zee.api.maestros.exceptions.IdentityException;
import pe.gob.vuce.zee.api.maestros.service.IdentidadService;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
public class IdentidadServiceImpl implements IdentidadService {
    private final ReniecAPI reniecApiConsumer;
    private final SunatAPI sunatApiConsumer;
    private final ModelMapper modelMapper;


    public IdentidadServiceImpl(@Qualifier("reniecApiConsumer") ReniecAPI reniecApiConsumer,
                                @Qualifier("sunatApiConsumer") SunatAPI sunatApiConsumer,
                                ModelMapper modelMapper) {
        this.reniecApiConsumer = reniecApiConsumer;
        this.sunatApiConsumer = sunatApiConsumer;
        this.modelMapper = modelMapper;
    }

    @Override
    public IdentidadDTO consultarDni(String numeroDocumento) throws IOException {
        var callResponse = reniecApiConsumer.consultaDni(numeroDocumento, Constantes.RENIEC_APLICACION);
        var response = callResponse.execute();

        if (response.code() == 200) {
            var body = response.body();
            if (body != null) {
                if (body.getCoResultado().equals("0000")) {
                    var result = Optional.ofNullable(body.getDatosPersona())
                            .map(datos -> modelMapper.typeMap(ReniecContentDTO.class, IdentidadDTO.class)
                                    .addMappings(mapper -> {
                                        mapper.map(ReniecContentDTO::getApPrimer, IdentidadDTO::setApellidoPaterno);
                                        mapper.map(ReniecContentDTO::getApSegundo, IdentidadDTO::setApellidoMaterno);
                                        mapper.map(ReniecContentDTO::getPrenombres, IdentidadDTO::setNombre);
                                        mapper.map(ReniecContentDTO::getDireccion, IdentidadDTO::setDireccion);
                                        mapper.map(ReniecContentDTO::getEstadoCivil, IdentidadDTO::setEstadoCivil);
                                        mapper.map(ReniecContentDTO::getUbigeo, IdentidadDTO::setUbigeo);
                                        mapper.map(ReniecContentDTO::getFoto, IdentidadDTO::setFotoBase64);
                                    })
                                    .map(datos))
                            .orElseThrow(() -> new IdentityException(String.format("No se obtuvo resultado al consultar %s", numeroDocumento)));
                    result.setTipoDocumento("DNI");
                    result.setNumeroDocumento(numeroDocumento);
                    return result;
                } else {
                    throw new IdentityException(body.getCoResultado(), body.getDeResultado());
                }
            } else {
                throw new IdentityException(String.format("No se obtuvo resultado al consultar %s", numeroDocumento));
            }
        } else {
            throw new IdentityException(String.format("El servicio retorno el codigo %d al consultar el DNI %s", response.code(), numeroDocumento));
        }
    }

    @Override
    public IdentidadDTO consultarRuc(String numeroDocumento) throws IOException {
        var callResponse = sunatApiConsumer.consultaRuc(numeroDocumento);
        var response = callResponse.execute();

        if (response.code() == 200) {
            var body = response.body();
            var result = Optional.ofNullable(response.body())
                    .map(sunatResponse -> modelMapper
                            .typeMap(SunatResponseDTO.class, IdentidadDTO.class)
                            .addMappings(mapper -> {
                                mapper.map(SunatResponseDTO::getDomicioLegal, IdentidadDTO::setDireccion);
                                mapper.map(SunatResponseDTO::getEsActivo, IdentidadDTO::setActivo);
                                mapper.map(SunatResponseDTO::getEsHabido, IdentidadDTO::setHabido);
                            })
                            .map(sunatResponse)
                    ).orElseThrow(() -> new IdentityException(String.format("No se pudo obtener resultado al consultar el RUC %s", numeroDocumento)));
            result.setTipoDocumento("RUC");
            result.setNumeroDocumento(numeroDocumento);
            return result;
        } else {
            throw new IdentityException(String.format("El servicio retorno el codigo %d al consultar el RUC %s", response.code(), numeroDocumento));
        }
    }

    @Override
    public IdentidadDTO consultarCe(String numeroDocumento) throws IOException {
        return null;
    }
}
