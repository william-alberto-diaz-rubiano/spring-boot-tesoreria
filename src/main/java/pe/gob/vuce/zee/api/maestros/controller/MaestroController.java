package pe.gob.vuce.zee.api.maestros.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.gob.vuce.zee.api.maestros.base.Constantes;
import pe.gob.vuce.zee.api.maestros.dto.ResponseDTO;
import pe.gob.vuce.zee.api.maestros.service.MaestroService;

@RestController
@RequestMapping("v1/maestros")
public class MaestroController {
    private final MaestroService maestroService;

    public MaestroController(MaestroService maestroService) {
        this.maestroService = maestroService;
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<?>> getMaestros(
            @RequestParam(name = "prefijo") Integer prefijo,
            @RequestParam(name = "correlativo", required = false) Integer correlativo
    ) {
        ResponseDTO<?> response;
        if (correlativo == null) {
            var resultado = maestroService.buscarPorPrefijo(prefijo);
            response = new ResponseDTO<>(Constantes.NO_ERROR, resultado);
        } else {
            var resultado = maestroService.buscarPorPrefijoYCorrelativo(prefijo, correlativo);
            response = new ResponseDTO<>(Constantes.NO_ERROR, resultado);
        }
        return ResponseEntity.ok(response);
    }
}
