package pe.gob.vuce.zee.api.tesoreria.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.gob.vuce.zee.api.tesoreria.dto.AccionPagoDTO;
import pe.gob.vuce.zee.api.tesoreria.dto.ResponseDTO;
import pe.gob.vuce.zee.api.tesoreria.exceptions.BadRequestException;
import pe.gob.vuce.zee.api.tesoreria.service.AccionPagoService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/accionPago")
@Slf4j
public class AccionPagoController {

    @Autowired
    public AccionPagoService accionPagoService;

    @PostMapping
    public ResponseEntity<ResponseDTO> guardar(@Valid @RequestBody AccionPagoDTO accionPagoDTO, BindingResult result) {

        if (result.hasErrors()) {

            List<String> listaErrores = new ArrayList<>();
            result.getFieldErrors()
                    .stream().collect(Collectors.toList()).forEach(x -> listaErrores.add(x.getDefaultMessage()));

            throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST, listaErrores, "Verificar los campos");
        }
        AccionPagoDTO nuevaAccionPago = accionPagoService.guardar(accionPagoDTO);

        ResponseDTO responseBody = new ResponseDTO(nuevaAccionPago, "Acción de tramite creada", "success", nuevaAccionPago.getId());
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> modificar(@Valid
                                                 @PathVariable("id") UUID id,
                                                 AccionPagoDTO accionPagoDTO,
                                                 BindingResult result) {
        if (result.hasErrors()) {

            List<String> listaErrores = new ArrayList<>();
            result.getFieldErrors()
                    .stream().collect(Collectors.toList()).forEach(x -> listaErrores.add(x.getDefaultMessage()));

            throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST, listaErrores, "Verificar los campos");
        }
        AccionPagoDTO modificarAccionPago = accionPagoService.modificar(id, accionPagoDTO);

        ResponseDTO responseBody = new ResponseDTO(modificarAccionPago, "Tipo tramite modificado", "success", modificarAccionPago.getId());
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> buscarPorTramitePago(@PathVariable("id") UUID id) {
        String messege = "";

        List<AccionPagoDTO> listadoAccionPago = accionPagoService.buscarPorTramitePago(id);

        if (listadoAccionPago.isEmpty()) {
            messege = "No se encontraron registros por el tramite de pago buscado";
        } else {
            messege = "Listado de acciones de tramite buscandos por tramite de pago";
        }

        ResponseDTO responseBody = new ResponseDTO("success", listadoAccionPago, messege);
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.OK);
    }
}