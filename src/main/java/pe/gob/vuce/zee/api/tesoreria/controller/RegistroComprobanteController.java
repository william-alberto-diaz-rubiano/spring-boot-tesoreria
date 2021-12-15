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
import pe.gob.vuce.zee.api.tesoreria.dto.ConceptoPagoDTO;
import pe.gob.vuce.zee.api.tesoreria.dto.RegistroComprobanteDTO;
import pe.gob.vuce.zee.api.tesoreria.dto.ResponseDTO;
import pe.gob.vuce.zee.api.tesoreria.exceptions.BadRequestException;
import pe.gob.vuce.zee.api.tesoreria.service.RegistroComprobanteService;
import pe.gob.vuce.zee.api.tesoreria.utils.ExportarUtil;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/registroComprobante")
@Slf4j
public class RegistroComprobanteController {

    @Autowired
    public RegistroComprobanteService registroComprobanteService;

    @GetMapping
    public ResponseEntity<ResponseDTO> busquedaPorFitros(
            @RequestParam(name = "comprobante", required = false) UUID comprobante,
            @RequestParam(name = "estado", required = false) UUID estado,
            Pageable paginador) {

        String messege = "";

        Page<RegistroComprobanteDTO> listaDTOPaginada = this.registroComprobanteService.busquedaPorFiltros(null,estado,null,null,comprobante,paginador);

        if (listaDTOPaginada.isEmpty()) {
            messege = "No se encontraron registros";
        } else {
            messege = "Listado de registros de comprobantes";
        }

        ResponseDTO rpta = new ResponseDTO("success", listaDTOPaginada, messege);

        return new ResponseEntity<ResponseDTO>(rpta, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> guardar(@Valid @RequestBody RegistroComprobanteDTO registroComprobanteDTO, BindingResult result) {

        if(result.hasErrors()){

            List<String> listaErrores = new ArrayList<>();
            result.getFieldErrors()
                    .stream().collect(Collectors.toList()).forEach(x -> listaErrores.add(x.getDefaultMessage()));

            throw new BadRequestException("FAILED", HttpStatus.BAD_REQUEST,listaErrores,"Verificar los campos");
        }

        String serieMayuscula = registroComprobanteDTO.getCodigoSerie().toUpperCase();
        registroComprobanteDTO.setCodigoSerie(serieMayuscula);

        RegistroComprobanteDTO nuevoRegistroComprobante = registroComprobanteService.guardar(registroComprobanteDTO);
        ResponseDTO responseBody = new ResponseDTO(nuevoRegistroComprobante,"Registro de comprobante guardado","success",nuevoRegistroComprobante.getId());
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> modificar(@Valid
                                                 @PathVariable("id") UUID id,
                                                 @RequestBody RegistroComprobanteDTO registroComprobanteDTO, BindingResult result){
        if(result.hasErrors()){

            List<String> listaErrores = new ArrayList<>();
            result.getFieldErrors()
                    .stream().collect(Collectors.toList()).forEach(x -> listaErrores.add(x.getDefaultMessage()));

            throw new BadRequestException("FAILED",HttpStatus.BAD_REQUEST,listaErrores,"Verificar los campos");
        }

        String serieMayuscula = registroComprobanteDTO.getCodigoSerie().toUpperCase();
        registroComprobanteDTO.setCodigoSerie(serieMayuscula);

        RegistroComprobanteDTO modificarRegistroComprobante= registroComprobanteService.modificar(id, registroComprobanteDTO);

        ResponseDTO responseBody = new ResponseDTO(modificarRegistroComprobante,"Registro de comprobante modificado","success",modificarRegistroComprobante.getId());
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.CREATED);
    }

    @GetMapping("/exportar")
    public void exportar(
            @RequestParam(name = "comprobante", required = false) UUID comprobante,
            @RequestParam(name = "estado", required = false) UUID estado,
            @RequestParam(name = "extension", required = false, defaultValue = "xls") String formato,
            HttpServletResponse response){

        var listado = registroComprobanteService.busquedaPorFiltros(null,estado, Constantes.HABILITADO,null,comprobante);

        String[] columnas = new String[]{"ID", "COMPROBANTE", "SERIE","CORRELATIVO INICIAL","CORRELATIVO ACTUAL","ESTADO"};

        var rowDataList = listado.stream().map(x -> new String[]{
                x.getId().toString(),
                x.getCodigoComprobanteDescripcion(),
                x.getCodigoSerie(),
                x.getCorrelativoInicial(),
                x.getCorrelativoAnual(),
                x.getEstadoDescripcion(),
        }).collect(Collectors.toList());
        var contentDispositionTmpl = "attachment; filename=%s";
        if (formato.equalsIgnoreCase("xls") || formato.equalsIgnoreCase("xlsx")) {
            try {
                response.setContentType(Constantes.CONTENT_TYPE_XLSX);
                var contentDisposition = String.format(contentDispositionTmpl, "listado_registro_comprobantes.xlsx");
                response.setHeader("Content-Disposition", contentDisposition);
                OutputStream os = response.getOutputStream();
                String titulo = "LISTADO DE REGISTRO DE COMPROBANTES";
                String hoja = "LISTADO DE REGISTRO DE COMPROBANTES";

                ExportarUtil.crearExcel(rowDataList, titulo, hoja, columnas, os);
            } catch (IOException e) {
                log.error("Error de entrada/salida", e);
            }
        } else if (formato.equalsIgnoreCase("csv")) {
            response.setContentType(Constantes.CONTENT_TYPE_CSV);
            var contentDisposition = String.format(contentDispositionTmpl, "listado_registro_comprobantes");
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

        RegistroComprobanteDTO modificarRegistroEstado = registroComprobanteService.modificarEstado(id, nuevoEstado);

        ResponseDTO responseBody = new ResponseDTO(modificarRegistroEstado,"Estado modificado","success",modificarRegistroEstado.getId());
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.OK);
    }

}
