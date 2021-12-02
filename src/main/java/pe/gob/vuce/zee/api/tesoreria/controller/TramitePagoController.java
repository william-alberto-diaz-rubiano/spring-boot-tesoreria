package pe.gob.vuce.zee.api.tesoreria.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.gob.vuce.zee.api.tesoreria.base.Constantes;
import pe.gob.vuce.zee.api.tesoreria.dto.ResponseDTO;
import pe.gob.vuce.zee.api.tesoreria.dto.TramitePagoDTO;
import pe.gob.vuce.zee.api.tesoreria.exceptions.BadRequestException;
import pe.gob.vuce.zee.api.tesoreria.service.TramitePagoService;
import pe.gob.vuce.zee.api.tesoreria.utils.ExportarUtil;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/tramitePago")
@Slf4j
public class TramitePagoController {

    @Autowired
    private TramitePagoService tramitePagoService;


    @GetMapping
    public ResponseEntity<ResponseDTO> busquedaPorFitros(
            @RequestParam(name = "tipoTramite", required = false) Integer tipoTramite,
            @RequestParam(name = "nombreTramite", required = false) String nombreTramite,
            @RequestParam(name = "estado", required = false) Integer estado,
            @RequestParam(name = "fechaInicio", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime fechaInicio,
            @RequestParam(name = "fechaFin", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime fechaFin,
            Pageable paginador){

        if(estado == 0){
            estado=null;
        }
        if((fechaInicio != null && fechaFin == null) || (fechaFin !=null && fechaInicio == null)){

            throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST,"Los campos de las fechas no pueden ser nulos");
        }
        if(fechaInicio != null && fechaFin != null){

            if(fechaFin.compareTo(fechaInicio) < 0){
                throw new BadRequestException("FAILED",HttpStatus.BAD_REQUEST,"La fecha final no puede ser menor a la fecha inicial");
            }
        }
        Page<TramitePagoDTO> listaDTOPaginada = this.tramitePagoService.busquedaPorFiltros(null,estado, 0, tipoTramite, nombreTramite, fechaInicio, fechaFin, paginador);

        ResponseDTO rpta = new ResponseDTO("success", listaDTOPaginada, "Listado de tramites de pago");

        return new ResponseEntity<ResponseDTO>(rpta, HttpStatus.OK);
        }

    @PostMapping
    public ResponseEntity<ResponseDTO> guardar(@Valid @RequestBody TramitePagoDTO tramitePagoDTO, BindingResult result) {

        if(result.hasErrors()){

            List<String> listaErrores = new ArrayList<>();
            result.getFieldErrors()
                    .stream().collect(Collectors.toList()).forEach(x -> listaErrores.add(x.getDefaultMessage()));

            throw new BadRequestException("FAILED",HttpStatus.BAD_REQUEST,listaErrores,"Verificar los campos");
        }
        TramitePagoDTO nuevoTramitePago = tramitePagoService.guardar(tramitePagoDTO);
        ResponseDTO responseBody = new ResponseDTO(nuevoTramitePago,"success","Informacion principal guardada",nuevoTramitePago.getId());
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> modificar(@Valid
                                                 @PathVariable("id") UUID id,
                                                 @RequestBody TramitePagoDTO tramitePagoDTO, BindingResult result){
        if(result.hasErrors()){

            List<String> listaErrores = new ArrayList<>();
            result.getFieldErrors()
                    .stream().collect(Collectors.toList()).forEach(x -> listaErrores.add(x.getDefaultMessage()));

            throw new BadRequestException("FAILED",HttpStatus.BAD_REQUEST,listaErrores,"Verificar los campos");
        }

        TramitePagoDTO modificarTramitePago = tramitePagoService.modificar(id, tramitePagoDTO);

        ResponseDTO responseBody = new ResponseDTO(modificarTramitePago,"success","Informacion principal del tramite modificada",modificarTramitePago.getId());
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.CREATED);
    }

    @GetMapping("/exportar")
    public void exportarTipoCambio(

            @RequestParam(name = "tipoTramite", required = false) Integer tipoTramite,
            @RequestParam(name = "nombreTramite", required = false) String nombreTramite,
            @RequestParam(name = "estado", required = false) Integer estado,
            @RequestParam(name = "fechaInicio", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime fechaInicio,
            @RequestParam(name = "fechaFin", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime fechaFin,
            @RequestParam(name = "extension", required = false, defaultValue = "xls") String formato,
            HttpServletResponse response){

        if(estado == 0){
            estado=null;
        }

        if((fechaInicio != null && fechaFin == null) || (fechaFin !=null && fechaInicio == null)){

            throw new BadRequestException("FAILED",HttpStatus.BAD_REQUEST,"Los campos de las fechas no pueden ser nulos");
        }
        if(fechaInicio != null && fechaFin != null){

            if(fechaFin.compareTo(fechaInicio) < 0){
                throw new BadRequestException("FAILED",HttpStatus.BAD_REQUEST,"La fecha final no puede ser menor a la fecha inicial");
            }
        }

        var listado = tramitePagoService.busquedaPorFiltros(null,estado, Constantes.HABILITADO, tipoTramite, nombreTramite, fechaInicio, fechaFin);

        String[] columnas = new String[]{"FECHA REGISTRO", "TIPO TRAMITE/CONCEPTOS", "CODIGO DEL SISTEMA","CODIGO","TRAMITE","BASE LEGAL","ESTADO"};

        var rowDataList = listado.stream().map(x -> new String[]{
                x.getFechaCreacion().toString(),
                x.getConfiguradorOperacion().toString(),
                x.getCodigoSistema(),
                x.getCodigoPago(),
                x.getNombrePago(),
                x.getBaseLegal(),
                Constantes.ESTADOS_TIPO_CAMBIO.get(x.getEstado()),
        }).collect(Collectors.toList());
        var contentDispositionTmpl = "attachment; filename=%s";
        if (formato.equalsIgnoreCase("xls") || formato.equalsIgnoreCase("xlsx")) {
            try {
                response.setContentType(Constantes.CONTENT_TYPE_XLSX);
                var contentDisposition = String.format(contentDispositionTmpl, "listado_tramites_pago.xlsx");
                response.setHeader("Content-Disposition", contentDisposition);
                OutputStream os = response.getOutputStream();
                String titulo = "LISTADO DE TRAMITES DE PAGO";
                String hoja = "LISTADO DE TRAMITES DE PAGO";

                ExportarUtil.crearExcel(rowDataList, titulo, hoja, columnas, os);
            } catch (IOException e) {
                log.error("Error de entrada/salida", e);
            }
        } else if (formato.equalsIgnoreCase("csv")) {
            response.setContentType(Constantes.CONTENT_TYPE_CSV);
            var contentDisposition = String.format(contentDispositionTmpl, "listado_tramites_pago.csv");
            response.setHeader("Content-Disposition", contentDisposition);
            PrintWriter writer = null;
            try {
                writer = response.getWriter();
                ExportarUtil.crearCSV(rowDataList, columnas, writer);
            } catch (IOException e) {
                log.error("Error de entrada/salida", e);
            }

        }
        throw new BadRequestException("FAILED",HttpStatus.BAD_REQUEST,"El tipo de extension ingresado no es correcto");

    }
}


