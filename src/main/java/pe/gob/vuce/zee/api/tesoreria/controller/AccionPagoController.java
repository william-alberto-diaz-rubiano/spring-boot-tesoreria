package pe.gob.vuce.zee.api.tesoreria.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.gob.vuce.zee.api.tesoreria.dto.*;
import pe.gob.vuce.zee.api.tesoreria.exceptions.BadRequestException;
import pe.gob.vuce.zee.api.tesoreria.repository.MaestroRepository;
import pe.gob.vuce.zee.api.tesoreria.service.AccionPagoService;
import pe.gob.vuce.zee.api.tesoreria.service.TramitePagoService;

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

    @Autowired
    public TramitePagoService tramitePagoService;

    @Autowired
    private MaestroRepository maestroRepository;

    @PostMapping
    public ResponseEntity<Response2DTO<AccionPagoGuardarDTO>> guardar(@Valid @RequestBody List<AccionPagoDTO> listaAccionPagos, BindingResult result) {

        UUID estadoActivo = maestroRepository.findByPrefijoAndCorrelativo(34, 1).getId();

        if (result.hasErrors()) {

            List<String> listaErrores = new ArrayList<>();
            result.getFieldErrors()
                    .stream().collect(Collectors.toList()).forEach(x -> listaErrores.add(x.getDefaultMessage()));

            throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST, listaErrores, "Verificar los campos");
        }
        List<AccionPagoDTO> nuevaListaAccionPago = accionPagoService.guardarAll(listaAccionPagos);

        UUID idTramitePago = nuevaListaAccionPago.get(0).getTramitePagoId();

        AccionPagoGuardarDTO accionPagoGuardarDTO = new AccionPagoGuardarDTO();

        accionPagoGuardarDTO.setIdTramite(idTramitePago);
        accionPagoGuardarDTO.setListaAccionPago(nuevaListaAccionPago);

        tramitePagoService.modificarEstado(idTramitePago, estadoActivo);

        var responseBody = new Response2DTO<>(HttpStatus.CREATED.value(), "Guardado", accionPagoGuardarDTO);

        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Response2DTO<AccionPagoGuardarDTO>> modificar(@Valid
                                                                            @RequestBody List<AccionPagoDTO> listaAccionPagos,
                                                                        BindingResult result) {
        if (result.hasErrors()) {

            List<String> listaErrores = new ArrayList<>();
            result.getFieldErrors()
                    .stream().collect(Collectors.toList()).forEach(x -> listaErrores.add(x.getDefaultMessage()));

            throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST, listaErrores, "Verificar los campos");
        }
        List<AccionPagoDTO> modificarListaAccionPago = accionPagoService.modificarAll(listaAccionPagos);

        UUID idTramitePago = modificarListaAccionPago.get(0).getTramitePagoId();

        AccionPagoGuardarDTO accionPagoGuardarDTO = new AccionPagoGuardarDTO();

        accionPagoGuardarDTO.setIdTramite(idTramitePago);
        accionPagoGuardarDTO.setListaAccionPago(modificarListaAccionPago);

        var responseBody = new Response2DTO<>(HttpStatus.CREATED.value(), "Modificado", accionPagoGuardarDTO);

        return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response2DTO<List<AccionPagoDTO>>> buscarPorTramitePago(@PathVariable("id") UUID id) {
        String messege = "";

        List<AccionPagoDTO> listadoAccionPago = accionPagoService.buscarPorTramitePago(id);

        if (listadoAccionPago.isEmpty()) {
            messege = "No se encontraron registros por el tramite de pago buscado";
        } else {
            messege = "Listado de acciones de tramite buscandos por tramite de pago";
        }

        var responseBody = new Response2DTO<>(HttpStatus.OK.value(), messege, listadoAccionPago);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}