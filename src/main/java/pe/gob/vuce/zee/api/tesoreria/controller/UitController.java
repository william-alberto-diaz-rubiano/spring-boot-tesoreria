package pe.gob.vuce.zee.api.tesoreria.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.gob.vuce.zee.api.tesoreria.dto.ResponseDTO;
import pe.gob.vuce.zee.api.tesoreria.dto.UitDTO;
import pe.gob.vuce.zee.api.tesoreria.exceptions.BadRequestException;
import pe.gob.vuce.zee.api.tesoreria.service.UitService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/UIT")
@Slf4j
public class UitController {

    @Autowired
    private UitService uitService;

    @GetMapping
    public ResponseEntity<ResponseDTO> busquedaPorFitros(
            @RequestParam(name = "porcentaje", required = true) BigDecimal porcentaje){

        var monto = uitService.valorMonto(porcentaje);
        ResponseDTO rpta = new ResponseDTO("success", monto, "monto segun porcentaje de UIT");
        return new ResponseEntity<ResponseDTO>(rpta, HttpStatus.OK);
    }

    @PostMapping
    public  ResponseEntity<ResponseDTO> guardar(@Valid @RequestBody UitDTO uitDTO, BindingResult result){
        if(result.hasErrors()){

            List<String> listaErrores = new ArrayList<>();
            result.getFieldErrors()
                    .stream().collect(Collectors.toList()).forEach(x -> listaErrores.add(x.getDefaultMessage()));

            throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST,listaErrores,"Verificar los campos");
        }
        UitDTO nuevoUIT= uitService.guardar(uitDTO);

        ResponseDTO responseBody = new ResponseDTO(nuevoUIT,"Tipo de tramite creado","success",nuevoUIT.getId());
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.CREATED);
    }
}
