package pe.gob.vuce.zee.api.tesoreria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.gob.vuce.zee.api.tesoreria.base.FechasUtil;
import pe.gob.vuce.zee.api.tesoreria.dto.ResponseDTO;
import pe.gob.vuce.zee.api.tesoreria.dto.TipoCambioDTO;
import pe.gob.vuce.zee.api.tesoreria.service.TipoCambioService;

import java.text.ParseException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("v1/tipoCambio")
public class TipoCambioController {


    @Autowired
    private TipoCambioService tipoCambioService;


    @GetMapping
    @ResponseBody
    public ResponseEntity<ResponseDTO> busquedaPorFitros(
            @RequestParam(name = "estado", required = false) Integer estado,
            @RequestParam(name = "fechaInicio", required = false) String fechaInicio,
            @RequestParam(name = "fechaFin", required = false) String fechaFin,
            Pageable paginador) throws ParseException {

        //compara si la fecha inicial es mayor a la final
        if (!(fechaInicio.isEmpty() || fechaFin.isEmpty())) {
            if (FechasUtil.compareDateInitialFinal(fechaInicio, fechaFin, "yyyy-MM-dd")) {
                ResponseDTO rpta = new ResponseDTO("error", "Fecha inicial no debe ser mayor a final");
                return new ResponseEntity<ResponseDTO>(rpta, HttpStatus.BAD_REQUEST);
            }
        }
        LocalDateTime fechaInicioL = null;
        LocalDateTime fechaFinL = null;

        // Dar formato fecha si valores son != a vacío
        if (!(fechaInicio.isEmpty() || fechaFin.isEmpty())) {
            fechaInicioL = FechasUtil.getStringStartDate(fechaInicio, "yyyy-MM-dd");
            fechaFinL = FechasUtil.getStringEndDate(fechaFin, "yyyy-MM-dd");
        }
            Page<TipoCambioDTO> listaDTOPaginada = this.tipoCambioService.busquedaPorFiltros(estado,0, fechaInicioL, fechaFinL, paginador);

            ResponseDTO rpta = new ResponseDTO("success", listaDTOPaginada, "Listado de tipos de cambio");
            return new ResponseEntity<ResponseDTO>(rpta, HttpStatus.OK);
        }


    @PostMapping
    public ResponseEntity<ResponseDTO> guardar(@RequestBody TipoCambioDTO tipoCambioDTO) {

        TipoCambioDTO nuevoTipoCambio = tipoCambioService.guardar(tipoCambioDTO);
        ResponseDTO responseBody = new ResponseDTO(nuevoTipoCambio,"success","usuario creado",nuevoTipoCambio.getId());
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.CREATED);
    }

}
