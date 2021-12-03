package pe.gob.vuce.zee.api.tesoreria.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.gob.vuce.zee.api.tesoreria.dto.ConfiguradorOperacionDTO;
import pe.gob.vuce.zee.api.tesoreria.dto.ResponseDTO;
import pe.gob.vuce.zee.api.tesoreria.dto.TipoTramiteDTO;
import pe.gob.vuce.zee.api.tesoreria.exceptions.BadRequestException;
import pe.gob.vuce.zee.api.tesoreria.service.TipoTramiteService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/tipoTramite")
@Slf4j
public class TipoTramiteController {

    @Autowired
    public TipoTramiteService tipoTramiteService;

    @PostMapping
    public ResponseEntity<ResponseDTO> guardar(@Valid @RequestBody TipoTramiteDTO tipoTramiteDTO, BindingResult result) {
        if(result.hasErrors()){

            List<String> listaErrores = new ArrayList<>();
            result.getFieldErrors()
                    .stream().collect(Collectors.toList()).forEach(x -> listaErrores.add(x.getDefaultMessage()));

            throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST,listaErrores,"Verificar los campos");
        }
        TipoTramiteDTO nuevoTipoTramite = tipoTramiteService.guardar(tipoTramiteDTO);

        ResponseDTO responseBody = new ResponseDTO(nuevoTipoTramite,"success","Tipo tramite creado",nuevoTipoTramite.getId());
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> modificar(@Valid
                                                 @PathVariable("id") UUID id,
                                                 @RequestBody TipoTramiteDTO tipoTramiteDTO,BindingResult result){
        if(result.hasErrors()){

            List<String> listaErrores = new ArrayList<>();
            result.getFieldErrors()
                    .stream().collect(Collectors.toList()).forEach(x -> listaErrores.add(x.getDefaultMessage()));

            throw new BadRequestException("FAILED",HttpStatus.BAD_REQUEST,listaErrores,"Verificar los campos");
        }

        TipoTramiteDTO modificarTipoTramite= tipoTramiteService.modificar(id,tipoTramiteDTO);

        ResponseDTO responseBody = new ResponseDTO(modificarTipoTramite,"success","Tipo tramite modificado",modificarTipoTramite.getId());
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.CREATED);
    }

}