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
import pe.gob.vuce.zee.api.tesoreria.dto.ConfiguradorOperacionDTO;
import pe.gob.vuce.zee.api.tesoreria.dto.ResponseDTO;
import pe.gob.vuce.zee.api.tesoreria.exceptions.BadRequestException;
import pe.gob.vuce.zee.api.tesoreria.service.ConfiguradorOperacionService;
import pe.gob.vuce.zee.api.tesoreria.utils.ExportarUtil;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/configurador")
@Slf4j
public class ConfiguradorOperacionController {

    @Autowired
    private ConfiguradorOperacionService configuradorOperacionService;

    @GetMapping
    public ResponseEntity<ResponseDTO> busquedaPorFitros(
            @RequestParam(name = "estado", required = false) Integer estado,
            @RequestParam(name = "tramite", required = false) Integer tramite,
            @RequestParam(name = "operacion", required = false) Integer operacion,
            @RequestParam(name = "fechacreacion", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime fechaCreacion,
            Pageable paginador){

        Page<ConfiguradorOperacionDTO> listaDTOPaginada = this.configuradorOperacionService.busquedaPorFiltros(estado, 0,tramite,operacion,fechaCreacion, paginador);
        ResponseDTO rpta = new ResponseDTO("success", listaDTOPaginada, "Listado del configurador de operaciones");
        return new ResponseEntity<ResponseDTO>(rpta, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<ResponseDTO> guardar(@Valid @RequestBody ConfiguradorOperacionDTO configuradorOperacionDTO,BindingResult result) {
        if(result.hasErrors()){

            List<String> listaErrores = new ArrayList<>();
            result.getFieldErrors()
                    .stream().collect(Collectors.toList()).forEach(x -> listaErrores.add(x.getDefaultMessage()));

            throw new BadRequestException("FAILED",HttpStatus.BAD_REQUEST,listaErrores,"Verificar los campos");
        }
        ConfiguradorOperacionDTO nuevaConfiguracion = configuradorOperacionService.guardar(configuradorOperacionDTO);

        ResponseDTO responseBody = new ResponseDTO(nuevaConfiguracion,"success","Configurador creado",nuevaConfiguracion.getId());
        return new ResponseEntity<ResponseDTO>(responseBody, HttpStatus.CREATED);
    }

    @GetMapping("/exportar")
    public ResponseEntity<ResponseDTO> busquedaPorFitros(
            @RequestParam(name = "estado", required = false) Integer estado,
            @RequestParam(name = "tramite", required = false) Integer tramite,
            @RequestParam(name = "operacion", required = false) Integer operacion,
            @RequestParam(name = "fechacreacion", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime fechaCreacion,
            @RequestParam(name = "extension", required = false, defaultValue = "xls") String formato,
            HttpServletResponse response) {

        var listado = configuradorOperacionService.busquedaPorFiltros(estado, Constantes.HABILITADO, tramite,operacion,fechaCreacion);

        String[] columnas = new String[]{"FECHA CREACION", "TRAMITE", "OPERACION", "ESTADO"};

        var rowDataList = listado.stream().map(x -> new String[]{
                x.getFechaCreacion().toString(),
                Constantes.TIPO_TRAMITES.get(x.getTramite()),
                Constantes.TIPO_TRAMITES.get(x.getTramite()),
                x.getOperacion().toString(),
                Constantes.ESTADOS_TIPO_CAMBIO.get(x.getEstado()),
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
