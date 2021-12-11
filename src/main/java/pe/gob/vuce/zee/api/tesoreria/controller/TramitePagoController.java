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
            @RequestParam(name = "id", required = false) UUID id,
            @RequestParam(name = "tipotramite", required = false) UUID tipoTramite,
            @RequestParam(name = "nombretramite", required = false) String nombreTramite,
            @RequestParam(name = "estado", required = false) UUID estado,
            @RequestParam(name = "fechainicio", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime fechaInicio,
            @RequestParam(name = "fechafin", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime fechaFin,
            Pageable paginador){

        String messege = "";
        if(nombreTramite == ""){
            nombreTramite=null;
        }
        if(nombreTramite != null){

            nombreTramite=nombreTramite.toUpperCase();
        }
        if((fechaInicio != null && fechaFin == null) || (fechaFin !=null && fechaInicio == null)){

            throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST,"Los campos de las fechas no pueden ser nulos");
        }
        if(fechaInicio != null && fechaFin != null){

            if(fechaFin.compareTo(fechaInicio) < 0){
                throw new BadRequestException("FAILED",HttpStatus.BAD_REQUEST,"La fecha final no puede ser menor a la fecha inicial");
            }
        }
        Page<TramitePagoDTO> listaDTOPaginada = this.tramitePagoService.busquedaPorFiltros(id,estado, 0, tipoTramite, nombreTramite, fechaInicio, fechaFin, paginador);

        if(listaDTOPaginada.isEmpty()){
            messege="No se encontraron registros";
        }else{
            messege="Listado de tramites de pago";
        }

        ResponseDTO rpta = new ResponseDTO("success", listaDTOPaginada,messege);

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
        ResponseDTO responseBody = new ResponseDTO(nuevoTramitePago,"Informacion principal guardada","success",nuevoTramitePago.getId());
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

        ResponseDTO responseBody = new ResponseDTO(modificarTramitePago,"Informacion principal del tramite modificada","success",modificarTramitePago.getId());
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.CREATED);
    }

    @GetMapping("/exportar")
    public void exportarTipoCambio(

            @RequestParam(name = "tipoTramite", required = false) UUID tipoTramite,
            @RequestParam(name = "nombreTramite", required = false) String nombreTramite,
            @RequestParam(name = "estado", required = false) UUID estado,
            @RequestParam(name = "fechaInicio", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime fechaInicio,
            @RequestParam(name = "fechaFin", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime fechaFin,
            @RequestParam(name = "extension", required = false, defaultValue = "xls") String formato,
            HttpServletResponse response){


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
                x.getConfiguradorOperacionTramiteDescripcion(),
                x.getCodigoSistema(),
                x.getCodigoPago(),
                x.getNombrePago(),
                x.getBaseLegal(),
                x.getEstadoDescripcion(),
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
    @PatchMapping("/{id}/{nuevoEstado}")
    public ResponseEntity<ResponseDTO> modificarEstado(@PathVariable("id") UUID id,@PathVariable("nuevoEstado") UUID nuevoEstado){

        TramitePagoDTO modificarTramiteEstado = tramitePagoService.modificarEstado(id, nuevoEstado);

        ResponseDTO responseBody = new ResponseDTO(modificarTramiteEstado,"Estado modificado","success",modificarTramiteEstado.getId());
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.OK);
    }

    @GetMapping("/codigoSistema")
     public ResponseEntity<ResponseDTO> codigoSistema(){
        String codigo = tramitePagoService.codigoSistema();
         ResponseDTO responseBody = new ResponseDTO("success",codigo,"Codigo del sistema");
         return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.OK);
     }
}


