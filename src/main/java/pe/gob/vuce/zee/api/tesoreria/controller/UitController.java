package pe.gob.vuce.zee.api.tesoreria.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.gob.vuce.zee.api.tesoreria.dto.ResponseDTO;
import pe.gob.vuce.zee.api.tesoreria.service.UitService;

import java.math.BigDecimal;

@RestController
@RequestMapping("v1/UIT")
@Slf4j
public class UitController {

    private UitService uitService;

    @GetMapping
    public ResponseEntity<ResponseDTO> busquedaPorFitros(
            @RequestParam(name = "anio", required = true) String anio,
            @RequestParam(name = "anio", required = true) BigDecimal porcentaje){

        var monto = uitService.valorMonto(anio,porcentaje);
        ResponseDTO rpta = new ResponseDTO("success", monto, "monto segun porcentaje de UIT");
        return new ResponseEntity<ResponseDTO>(rpta, HttpStatus.OK);
    }
}
