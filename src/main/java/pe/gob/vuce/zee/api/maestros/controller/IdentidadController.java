package pe.gob.vuce.zee.api.maestros.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.gob.vuce.zee.api.maestros.base.Constantes;
import pe.gob.vuce.zee.api.maestros.dto.IdentidadDTO;
import pe.gob.vuce.zee.api.maestros.dto.ResponseDTO;
import pe.gob.vuce.zee.api.maestros.service.IdentidadService;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("v1/identidad")
public class IdentidadController {
    private final IdentidadService identidadService;

    public IdentidadController(IdentidadService identidadService) {
        this.identidadService = identidadService;
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<?>> getIdentidad(@RequestParam(name = "tipo") String tipoDocumento,
                                                       @RequestParam(name = "numero") String numeroDocumento) throws IOException {
        IdentidadDTO response;
        switch(tipoDocumento.toLowerCase()){
            case "dni":
                response = identidadService.consultarDni(numeroDocumento);
                break;
            case "ruc":
                response =identidadService.consultarRuc(numeroDocumento);
                break;
            default:
                response =identidadService.consultarCe(numeroDocumento);
                break;
        }

        return ResponseEntity.ok(new ResponseDTO<>(Constantes.NO_ERROR, response));
    }
}
