package pe.gob.vuce.zee.api.tesoreria.service.impl;

import pe.gob.vuce.zee.api.tesoreria.models.UitEntity;
import pe.gob.vuce.zee.api.tesoreria.repository.UitRepository;
import pe.gob.vuce.zee.api.tesoreria.service.UitService;

import java.math.BigDecimal;

public class UitServiceImpl implements UitService {

    private UitRepository uitRepository;

    @Override
    public BigDecimal valorMonto(String anio,BigDecimal porcentajeUit) {

        UitEntity uit=uitRepository.findByAnioUit(anio);
        var monto= uit.getValorSolesUit() / 100 * porcentajeUit.doubleValue();

        return BigDecimal.valueOf(monto);
    }
}
