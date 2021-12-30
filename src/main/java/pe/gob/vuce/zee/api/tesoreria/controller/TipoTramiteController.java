package pe.gob.vuce.zee.api.tesoreria.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.gob.vuce.zee.api.tesoreria.dto.*;
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
public ResponseEntity<Response2DTO<TipoTramiteGuardarDTO>> guardar(@Valid @RequestBody List<TipoTramiteDTO> listaTipoTramites, BindingResult result) {

    if(result.hasErrors()){

        List<String> listaErrores = new ArrayList<>();
        result.getFieldErrors()

                .stream().collect(Collectors.toList()).forEach(x -> listaErrores.add(x.getDefaultMessage()));

        throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST,listaErrores,"Verificar los campos");
    }
    List<TipoTramiteDTO> nuevoListaTipoTramite = tipoTramiteService.guardarAll(listaTipoTramites);

    UUID idTramitePago = nuevoListaTipoTramite.get(0).getTramitePagoId();

    TipoTramiteGuardarDTO nuevoTipoTramiteGuardarDTO = new TipoTramiteGuardarDTO();

    nuevoTipoTramiteGuardarDTO.setIdTramite(idTramitePago);
    nuevoTipoTramiteGuardarDTO.setListaTipoTramite(nuevoListaTipoTramite);

    var responseBody = new Response2DTO<>(HttpStatus.CREATED.value(),"Guardado", nuevoTipoTramiteGuardarDTO);

    return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
}

    @PutMapping
    public ResponseEntity<Response2DTO<TipoTramiteGuardarDTO>> modificar(@Valid
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

        TipoTramiteGuardarDTO nuevoTipoTramiteGuardarDTO = new TipoTramiteGuardarDTO();

        nuevoTipoTramiteGuardarDTO.setIdTramite(idTramitePago);
        nuevoTipoTramiteGuardarDTO.setListaTipoTramite(modificarListaTipoTramite);

        var responseBody = new Response2DTO<>(HttpStatus.CREATED.value(),"Modificado", nuevoTipoTramiteGuardarDTO);

        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Response2DTO<TipoTramiteListarDTO>> buscarPorTramitePago ( @PathVariable("id") UUID id){
        TipoTramiteListarDTO tipoTramiteListarDTO=new TipoTramiteListarDTO();
        String messege = "";

        List<TipoTramiteDTO> listadoTipoTramite= tipoTramiteService.buscarTramitePago(id);

        if(listadoTipoTramite.isEmpty()){
                messege="No se encontraron registros por el tramite de pago buscado";
        }else{
                messege="Listado de tipos de tramite buscando por tramite de pago";

                tipoTramiteListarDTO.setTipoCalculoId(listadoTipoTramite.get(0).getTipoCalculoId());
                tipoTramiteListarDTO.setTipoCalculoDescripcion(listadoTipoTramite.get(0).getTipoCalculoDescripcion());
                tipoTramiteListarDTO.setCodigoModuloId(listadoTipoTramite.get(0).getCodigoModuloId());
                tipoTramiteListarDTO.setCodigoModuloDescripcion(listadoTipoTramite.get(0).getCodigoModuloDescripcion());
                tipoTramiteListarDTO.setCodigoProcesoId(listadoTipoTramite.get(0).getCodigoProcesoId());
                tipoTramiteListarDTO.setCodigoProcesoDescripcion(listadoTipoTramite.get(0).getCodigoProcesoDescripcion());
                tipoTramiteListarDTO.setCodigoFormularioId(listadoTipoTramite.get(0).getCodigoFormularioId());
                tipoTramiteListarDTO.setCodigoFormularioDescripcion(listadoTipoTramite.get(0).getCodigoFormularioDescripcion());
                tipoTramiteListarDTO.setCodigoAccionId(listadoTipoTramite.get(0).getCodigoAccionId());
                tipoTramiteListarDTO.setCodigoAccionDescripcion(listadoTipoTramite.get(0).getCodigoAccionDescripcion());
                tipoTramiteListarDTO.setPreguntaDatoInformado(listadoTipoTramite.get(0).getPreguntaDatoInformado());
                tipoTramiteListarDTO.setListadoTipoTramite(listadoTipoTramite);
        }


        var responseBody = new Response2DTO<>(HttpStatus.OK.value(), messege,tipoTramiteListarDTO);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

}
