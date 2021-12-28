package pe.gob.vuce.zee.api.tesoreria.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
public ResponseEntity<ResponseDTO> guardar(@Valid @RequestBody List<TipoTramiteDTO> listaTipoTramites, BindingResult result) {

    if(result.hasErrors()){

        List<String> listaErrores = new ArrayList<>();
        result.getFieldErrors()

                .stream().collect(Collectors.toList()).forEach(x -> listaErrores.add(x.getDefaultMessage()));

        throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST,listaErrores,"Verificar los campos");
    }
    List<TipoTramiteDTO> nuevoListaTipoTramite = tipoTramiteService.guardarAll(listaTipoTramites);

    UUID idTramitePago = nuevoListaTipoTramite.get(0).getTramitePagoId();

    ResponseDTO responseBody = new ResponseDTO("success",nuevoListaTipoTramite,"Lista de tipos de tramite guardada", idTramitePago);
    return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.CREATED);
}

    @PutMapping
    public ResponseEntity<ResponseDTO> modificar(@Valid
                                                 @RequestBody List<TipoTramiteDTO> listaTipoTramites,
                                                 BindingResult result){
        if(result.hasErrors()){

            List<String> listaErrores = new ArrayList<>();
            result.getFieldErrors()
                    .stream().collect(Collectors.toList()).forEach(x -> listaErrores.add(x.getDefaultMessage()));

            throw new BadRequestException("FAILED",HttpStatus.BAD_REQUEST,listaErrores,"Verificar los campos");
        }

        List<TipoTramiteDTO> modificarListaTipoTramite = tipoTramiteService.modificarAll(listaTipoTramites);

        UUID idTramitePago = modificarListaTipoTramite.get(0).getTramitePagoId();

        ResponseDTO responseBody = new ResponseDTO("success",modificarListaTipoTramite,"Lista de tipos de tramite modififcada",idTramitePago);
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> buscarPorTramitePago ( @PathVariable("id") UUID id){
            String messege = "";
            UUID tipoCalculoId;

            List<TipoTramiteDTO> listadoTipoTramite= tipoTramiteService.buscarTramitePago(id);

            if(listadoTipoTramite.isEmpty()){
                messege="No se encontraron registros por el tramite de pago buscado";
                tipoCalculoId=null;
            }else{
                messege="Listado de tipos de tramite buscando por tramite de pago";
                tipoCalculoId=listadoTipoTramite.get(0).getTipoCalculoId();
            }



        ResponseDTO responseBody = new ResponseDTO("success",listadoTipoTramite, messege,tipoCalculoId);
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.OK);
    }

}
