package pe.gob.vuce.zee.api.tesoreria.service;

import pe.gob.vuce.zee.api.tesoreria.dto.UitDTO;

import java.math.BigDecimal;

public interface UitService {
    UitDTO guardar(UitDTO uitDTO);
    BigDecimal valorMonto(BigDecimal porcentajeUit);
}
