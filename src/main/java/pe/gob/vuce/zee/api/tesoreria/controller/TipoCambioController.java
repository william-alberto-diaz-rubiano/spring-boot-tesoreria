package pe.gob.vuce.zee.api.tesoreria.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.gob.vuce.zee.api.tesoreria.base.Constantes;
import pe.gob.vuce.zee.api.tesoreria.exceptions.BadRequestException;
import pe.gob.vuce.zee.api.tesoreria.utils.FechasUtil;
import pe.gob.vuce.zee.api.tesoreria.dto.ResponseDTO;
import pe.gob.vuce.zee.api.tesoreria.dto.TipoCambioDTO;
import pe.gob.vuce.zee.api.tesoreria.service.TipoCambioService;
import pe.gob.vuce.zee.api.tesoreria.utils.ExportarUtil;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/tipoCambio")
@Slf4j
public class TipoCambioController {

    private static final String formatoFecha="yyyy-MM-dd";
    @Autowired
    private TipoCambioService tipoCambioService;


    @GetMapping
    public ResponseEntity<ResponseDTO> busquedaPorFitros(
            @RequestParam(name = "estado", required = false) Integer estado,
            @RequestParam(name = "cambiocompra", required = false) BigDecimal cambioCompra,
            @RequestParam(name = "cambioventa", required = false) BigDecimal cambioVenta,
            @RequestParam(name = "fechainicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam(name = "fechafin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin,
            Pageable paginador){

        System.out.println(fechaInicio);
        System.out.println(fechaFin);

        if((fechaInicio != null && fechaFin == null) || (fechaFin !=null && fechaInicio == null)){

           throw new BadRequestException("FAILED",HttpStatus.BAD_REQUEST,"Los campos de las fechas no pueden ser nulos");
        }
        if(fechaFin.compareTo(fechaInicio) < 0){
            throw new BadRequestException("FAILED",HttpStatus.BAD_REQUEST,"La fecha final no puede ser menor a la fecha inicial");
        }
        
        Page<TipoCambioDTO> listaDTOPaginada = this.tipoCambioService.busquedaPorFiltros(estado, 0, cambioCompra, cambioVenta, fechaInicio, fechaFin, paginador);
        ResponseDTO rpta = new ResponseDTO("success", listaDTOPaginada, "Listado de tipos de cambio");
        return new ResponseEntity<ResponseDTO>(rpta, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<ResponseDTO> guardar(@Valid @RequestBody TipoCambioDTO tipoCambioDTO) {

        TipoCambioDTO nuevoTipoCambio = tipoCambioService.guardar(tipoCambioDTO);
        ResponseDTO responseBody = new ResponseDTO(nuevoTipoCambio,"success","Tipo de cambio creado",nuevoTipoCambio.getId());
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.CREATED);
    }

    @GetMapping("/exportar")
    public ResponseEntity<ResponseDTO> exportarTipoCambio(@RequestParam(name = "estado", required = false) Integer estado,
                                   @RequestParam(name = "cambiocompra", required = false) BigDecimal cambioCompra,
                                   @RequestParam(name = "cambioventa", required = false) BigDecimal cambioVenta,
                                   @RequestParam(name = "fechainicio", required = false) String fechaInicio,
                                   @RequestParam(name = "fechafin", required = false) String fechaFin,
                                   @RequestParam(name = "extension", required = false, defaultValue = "xls") String formato,
                                   HttpServletResponse response) {

        if (!(fechaInicio.isEmpty() || fechaFin.isEmpty())) {
            if (FechasUtil.compareDateInitialFinal(fechaInicio, fechaFin, formatoFecha)) {
                ResponseDTO rpta = new ResponseDTO("error", "Fecha inicial no debe ser mayor a final");
                return new ResponseEntity<ResponseDTO>(rpta, HttpStatus.BAD_REQUEST);
            }
        }
        LocalDateTime fechaInicioL = null;
        LocalDateTime fechaFinL = null;

        if (!(fechaInicio.isEmpty() || fechaFin.isEmpty())) {
            fechaInicioL = FechasUtil.getStringStartDate(fechaInicio, formato);
            fechaFinL = FechasUtil.getStringEndDate(fechaFin, formato);
        }

        var listado = tipoCambioService.busquedaPorFiltros(estado, Constantes.HABILITADO, cambioCompra, cambioVenta, fechaInicioL, fechaFinL);

        String[] columnas = new String[]{"FECHA CREACION", "CAMBIO COMPRA", "CAMBIO VENTA", "ESTADO"};

        var rowDataList = listado.stream().map(x -> new String[]{
                x.getFechaCreacion().toString(),
                x.getCambioCompra().toString(),
                x.getCambioVenta().toString(),
                Constantes.ESTADOS_TIPO_CAMBIO.get(x.getEstado()),
        }).collect(Collectors.toList());
        var contentDispositionTmpl = "attachment; filename=%s";
        if (formato.equalsIgnoreCase("xls") || formato.equalsIgnoreCase("xlsx")) {
            try {
                response.setContentType(Constantes.CONTENT_TYPE_XLSX);
                var contentDisposition = String.format(contentDispositionTmpl, "listado_tipos_cambio.xlsx");
                response.setHeader("Content-Disposition", contentDisposition);
                OutputStream os = response.getOutputStream();
                String titulo = "LISTADO DE TIPOS DE CAMBIO";
                String hoja = "LISTADO DE TIPOS DE CAMBIO";

                ExportarUtil.crearExcel(rowDataList, titulo, hoja, columnas, os);
            } catch (IOException e) {
                log.error("Error de entrada/salida", e);
            }
        } else if (formato.equalsIgnoreCase("csv")) {
            response.setContentType(Constantes.CONTENT_TYPE_CSV);
            var contentDisposition = String.format(contentDispositionTmpl, "listado_tipos_cambio.csv");
            response.setHeader("Content-Disposition", contentDisposition);
            PrintWriter writer = null;
            try {
                writer = response.getWriter();
                ExportarUtil.crearCSV(rowDataList, columnas, writer);
            } catch (IOException e) {
                log.error("Error de entrada/salida", e);
            }

        }
        ResponseDTO responseBody = new ResponseDTO("ERROR","Archivo No generado");
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.BAD_REQUEST);
    }
}
