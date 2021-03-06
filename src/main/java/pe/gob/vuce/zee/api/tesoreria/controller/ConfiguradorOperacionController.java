package pe.gob.vuce.zee.api.tesoreria.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pe.gob.vuce.zee.api.tesoreria.base.Constantes;
import pe.gob.vuce.zee.api.tesoreria.dto.ConfiguradorOperacionDTO;
import pe.gob.vuce.zee.api.tesoreria.dto.Response2DTO;
import pe.gob.vuce.zee.api.tesoreria.exceptions.BadRequestException;
import pe.gob.vuce.zee.api.tesoreria.service.ConfiguradorOperacionService;
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
@RequestMapping("v1/configurador")
@Slf4j
public class ConfiguradorOperacionController {

    @Autowired
    private ConfiguradorOperacionService configuradorOperacionService;

    @GetMapping
    public ResponseEntity<Response2DTO<Page<ConfiguradorOperacionDTO>>> busquedaPorFitros(
            @RequestParam(name = "tramite", required = false) UUID tramite,
            @RequestParam(name = "operacion", required = false) UUID operacion,
            Pageable paginador){

        String messege;

        Page<ConfiguradorOperacionDTO> listaDTOPaginada = this.configuradorOperacionService.busquedaPorFiltros(null,null, 0,tramite,operacion,paginador);

        if(listaDTOPaginada.isEmpty()){
            messege="No se encontraron registros";
        }else{
            messege="success";

        }
        var rpta = new Response2DTO<>(HttpStatus.OK.value(),messege,listaDTOPaginada);

        return new ResponseEntity<>(rpta,HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Response2DTO<ConfiguradorOperacionDTO>> guardar(@Valid @RequestBody ConfiguradorOperacionDTO configuradorOperacionDTO,BindingResult result) {
        if(result.hasErrors()){

            List<String> listaErrores = new ArrayList<>();
            result.getFieldErrors()
                    .stream().collect(Collectors.toList()).forEach(x -> listaErrores.add(x.getDefaultMessage()));

            throw new BadRequestException("FAILED",HttpStatus.BAD_REQUEST,listaErrores,"Verificar los campos");
        }
        ConfiguradorOperacionDTO nuevaConfiguracion = configuradorOperacionService.guardar(configuradorOperacionDTO);

        var responseBody = new Response2DTO<>(HttpStatus.CREATED.value(),"Guardado",nuevaConfiguracion);

        return new ResponseEntity<>(responseBody,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response2DTO<ConfiguradorOperacionDTO>> modificar(@PathVariable("id") UUID id,@RequestBody ConfiguradorOperacionDTO configuradorOperacionDTO){

        if( configuradorOperacionDTO.getOperacionId() == null){
            throw new BadRequestException("FAILED",HttpStatus.BAD_REQUEST,"El campo de operacionid no puede ser nulo");
        }
        if( configuradorOperacionDTO.getTramiteId() == null){
            throw new BadRequestException("FAILED",HttpStatus.BAD_REQUEST,"El campo tramiteid no puede ser nulo");
        }

        ConfiguradorOperacionDTO modificarConfiguracion = configuradorOperacionService.modificar(id,configuradorOperacionDTO);

        var responseBody = new Response2DTO<>(HttpStatus.CREATED.value(),"Modificado",modificarConfiguracion);

        return new ResponseEntity<>(responseBody,HttpStatus.CREATED);
    }


    @GetMapping("/exportar")
    public void busquedaPorFitros(
            @RequestParam(name = "tramite", required = false) UUID tramite,
            @RequestParam(name = "operacion", required = false) UUID operacion,
            @RequestParam(name = "extension", required = false, defaultValue = "xls") String formato,
            HttpServletResponse response) {

        var listado = configuradorOperacionService.busquedaPorFiltros(null,null, Constantes.HABILITADO, tramite,operacion);

        String[] columnas = new String[]{"FECHA CREACION", "TRAMITE", "OPERACION", "ESTADO"};

        var rowDataList = listado.stream().map(x -> new String[]{
                x.getFechaCreacion().toString(),
                x.getTramiteDescripcion(),
                x.getOperacionDescripcion(),
                x.getEstadoDescripcion(),
        }).collect(Collectors.toList());
        var contentDispositionTmpl = "attachment; filename=%s";
        if (formato.equalsIgnoreCase("xls") || formato.equalsIgnoreCase("xlsx")) {
            try {
                response.setContentType(Constantes.CONTENT_TYPE_XLSX);
                var contentDisposition = String.format(contentDispositionTmpl, "listado_configuraciones.xlsx");
                response.setHeader("Content-Disposition", contentDisposition);
                OutputStream os = response.getOutputStream();
                String titulo = "LISTADO DE CONFIGURACIONES";
                String hoja = "LISTADO DE CONFIGURACIONES";

                ExportarUtil.crearExcel(rowDataList, titulo, hoja, columnas, os);
            } catch (IOException e) {
                log.error("Error de entrada/salida", e);
            }
        } else if (formato.equalsIgnoreCase("csv")) {
            response.setContentType(Constantes.CONTENT_TYPE_CSV);
            var contentDisposition = String.format(contentDispositionTmpl, "listado_configuraciones.csv");
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
