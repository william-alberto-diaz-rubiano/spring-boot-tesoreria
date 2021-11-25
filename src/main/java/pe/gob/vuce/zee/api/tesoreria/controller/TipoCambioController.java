package pe.gob.vuce.zee.api.tesoreria.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.gob.vuce.zee.api.tesoreria.base.Constantes;
import pe.gob.vuce.zee.api.tesoreria.base.FechasUtil;
import pe.gob.vuce.zee.api.tesoreria.dto.ResponseDTO;
import pe.gob.vuce.zee.api.tesoreria.dto.TipoCambioDTO;
import pe.gob.vuce.zee.api.tesoreria.service.TipoCambioService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/tipoCambio")
@Slf4j
public class TipoCambioController {

    private static final String formato="yyyy-MM-dd";
    @Autowired
    private TipoCambioService tipoCambioService;



    @GetMapping
    @ResponseBody
    public ResponseEntity<ResponseDTO> busquedaPorFitros(
            @RequestParam(name = "estado", required = false) Integer estado,
            @RequestParam(name = "cambiocompra", required = false) BigDecimal cambioCompra,
            @RequestParam(name = "cambioventa", required = false) BigDecimal cambioVenta,
            @RequestParam(name = "fechainicio", required = false) String fechaInicio,
            @RequestParam(name = "fechafin", required = false) String fechaFin,
            Pageable paginador) throws ParseException {

        //compara si la fecha inicial es mayor a la final
        if (!(fechaInicio.isEmpty() || fechaFin.isEmpty())) {
            if (FechasUtil.compareDateInitialFinal(fechaInicio, fechaFin, formato)) {
                ResponseDTO rpta = new ResponseDTO("error", "Fecha inicial no debe ser mayor a final");
                return new ResponseEntity<ResponseDTO>(rpta, HttpStatus.BAD_REQUEST);
            }
        }
        LocalDateTime fechaInicioL = null;
        LocalDateTime fechaFinL = null;

        // Dar formato fecha si valores son != a vacío
        if (!(fechaInicio.isEmpty() || fechaFin.isEmpty())) {
            fechaInicioL = FechasUtil.getStringStartDate(fechaInicio,formato);
            fechaFinL = FechasUtil.getStringEndDate(fechaFin, formato);
        }
        Page<TipoCambioDTO> listaDTOPaginada = this.tipoCambioService.busquedaPorFiltros(estado,0,cambioCompra,cambioVenta, fechaInicioL, fechaFinL, paginador);

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
